package com.example.jamz;

import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.adapter.MyAppliesAdapter;
import com.example.jamz.adapter.MyInvitationsAdapter;
import com.example.jamz.adapter.UserAdapter;
import com.example.jamz.model.Invitation;
import com.example.jamz.model.InvitationId;
import com.example.jamz.payload.payload.request.InvitationCreationRequest;
import com.example.jamz.payload.payload.request.InvitationFromIdsRequest;
import com.example.jamz.payload.payload.request.UserByUsernameInfoRequest;
import com.example.jamz.payload.payload.request.UserUpdateRequest;
import com.example.jamz.payload.payload.response.MessageResponse;
import com.example.jamz.payload.payload.response.UserProfileInfoResponse;
import com.example.jamz.retrofit.InvitationApi;
import com.example.jamz.retrofit.RetrofitService;
import com.example.jamz.retrofit.UserApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileDetailsActivity extends AppCompatActivity {
    //TODO RISOLVERE DESIGN IN GENERALE(DISTANZA TRA GLI ELEMENTI ETC ETC)
    // finire di cambiare i colori in dark
    // FARE IL LOGIN/SIGNUP CON FACEBOOK E GOOGLE(?)

    TextView fullName;
    TextView username;
    TextView location;
    TextView availability;
    SwitchCompat availabilitySwitch;
    ChipGroup instrumentList;
    Button addInstrument;
    ChipGroup tagList;
    Button addTag, addInvitation, createInvitation, signoutButton;
    ImageButton addTagForm;
    TextView bio;
    EditText locationEdit;
    EditText instrumentListEdit;
    EditText tagListEdit;
    EditText bioEdit;
    EditText formTitle, formDescription, formGenre, formInstrument, formTag;
    Spinner invTypeForm;
    ChipGroup tagListForm;
    ImageView closeEdit, closeAddInvitationForm;
    LinearLayout myInvitationsLayout, myAppliesLayout, profileDetailsLayout, detailsLayout,
            topMenu, myInvitationsListLayout, myAppliesListLayout, profileDetailsEditLayout, addInvitationFormLayout;
    RecyclerView myInvitationsView, myAppliesView;

    RetrofitService retrofitService = new RetrofitService();
    UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
    InvitationApi invitationApi = retrofitService.getRetrofit().create(InvitationApi.class);

    String token = "";

    boolean editMode = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        Intent thisIntent = getIntent();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "");
        String user = thisIntent.getStringExtra("username");

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.menu_navigation_posts:
                        startActivity(new Intent(getApplicationContext(), PostListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_navigation_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_navigation_profiles:
                        startActivity(new Intent(getApplicationContext(), UserListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_navigation_myprofile:
                        return true;
                }
                return false;
            }
        });

        //setup elements
        fullName = findViewById(R.id.profileDetailsFullName);
        username = findViewById(R.id.profileDetailsUsername);
        location = findViewById(R.id.profileDetailsLocation);
        availability = findViewById(R.id.profileDetailsAvailability);
        availabilitySwitch = findViewById(R.id.profileDetailsAvailabilitySwitch);
        instrumentList = findViewById(R.id.profileDetailsInstrumentsChipGroup);
        tagList = findViewById(R.id.profileDetailsTagsChipGroup);
        bio = findViewById(R.id.profileDetailsBio);
        locationEdit = findViewById(R.id.profileDetailsLocationEdit);
        instrumentListEdit = findViewById(R.id.profileDetailsInstrumentsEdit);
        addInstrument = findViewById(R.id.profileDetailsAddInstrumentButton);
        tagListEdit = findViewById(R.id.profileDetailsTagsEdit);
        addTag = findViewById(R.id.profileDetailsAddTagButton);
        bioEdit = findViewById(R.id.profileDetailsBioEdit);
        closeEdit = findViewById(R.id.closeEdit);
        closeAddInvitationForm = findViewById(R.id.closeAddInvitationForm);
        addInvitationFormLayout = findViewById(R.id.profileDetailsAddInvitationFormLayout);
        signoutButton = findViewById(R.id.profileDetailsSignout);

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });


        //setup adapter my invitations list
        myInvitationsView = findViewById(R.id.profileDetailsMyInvitationsList);
        myInvitationsView.setLayoutManager(new LinearLayoutManager(this));
        myInvitationsView.setAdapter(new MyInvitationsAdapter(new ArrayList<Invitation>(),
                this, retrofitService, invitationApi, token));

        //setup adapter my applies list
        myAppliesView = findViewById(R.id.profileDetailsMyAppliesList);
        myAppliesView.setLayoutManager(new LinearLayoutManager(this));
        myAppliesView.setAdapter(new MyAppliesAdapter(new ArrayList<Invitation>(), this, retrofitService, invitationApi, token));

        //setup add invitation button
        addInvitation = findViewById(R.id.myInvitationsListAddInvitationButton);
        addInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topMenu.setVisibility(View.GONE);
                myInvitationsListLayout.setVisibility(View.GONE);
                myAppliesListLayout.setVisibility(View.GONE);
                profileDetailsEditLayout.setVisibility(View.GONE);
                detailsLayout.setVisibility(View.GONE);

                addInvitationFormLayout.setVisibility(View.VISIBLE);
                addInvitation.setVisibility(View.GONE);
                closeAddInvitationForm.setClickable(true);
                closeAddInvitationForm.setFocusable(true);

            }
        });

        closeAddInvitationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topMenu.setVisibility(View.VISIBLE);
                myInvitationsListLayout.setVisibility(View.VISIBLE);

                addInvitationFormLayout.setVisibility(View.GONE);
                addInvitation.setVisibility(View.VISIBLE);
                closeAddInvitationForm.setClickable(false);
                closeAddInvitationForm.setFocusable(false);
            }
        });

        //setup form
        formDescription = findViewById(R.id.profileDetailsAddInvitationFormDescription);
        formTitle = findViewById(R.id.profileDetailsAddInvitationFormTitle);
        formGenre = findViewById(R.id.profileDetailsAddInvitationFormGenre);
        formInstrument = findViewById(R.id.profileDetailsAddInvitationFormInstrument);
        formTag = findViewById(R.id.profileDetailsAddInvitationFormEditTag);
        addTagForm = findViewById(R.id.profileDetailsAddInvitationFormAddTagButton);
        tagListForm = findViewById(R.id.profileDetailsAddInvitationFormTagChipGroup);
        addTagForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagToBeAdded = formTag.getText().toString();
                if(!tagToBeAdded.isEmpty()){
                    addChip(tagToBeAdded, tagListForm);
                }
            }
        });


        //setup spinner for the add invitation form
        invTypeForm = findViewById(R.id.profileDetailsAddInvitationFormType);
        String[] types = new String[]{"gig", "jam", "band member"};
        ArrayAdapter<String> spinnerTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        invTypeForm.setAdapter(spinnerTypeAdapter);
        //setup create button
        createInvitation = findViewById(R.id.profileDetailsAddInvitationFormCreateButton);
        createInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = formDescription.getText().toString();
                String title = formTitle.getText().toString();
                String genre = formGenre.getText().toString();
                String instrument = formInstrument.getText().toString();
                String type = invTypeForm.getSelectedItem().toString();
                List<String> tagListFormUpdate = new ArrayList<>();
                for (int i=0; i<tagListForm.getChildCount();i++){
                    tagListFormUpdate.add(((Chip)tagListForm.getChildAt(i)).getText().toString());
                }
                invitationApi.createInvitation("Bearer " + token,
                        new InvitationCreationRequest(title, description, genre, instrument, tagListFormUpdate, type, user)).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        Toast.makeText(ProfileDetailsActivity.this, "Invitation created successfully" +  call.toString(), Toast.LENGTH_SHORT).show();
                        thisIntent.putExtra("data", "MyProfileDetails");
                        thisIntent.putExtra("username", user);
                        reloadActivity();

                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(ProfileDetailsActivity.this, "Error while creating invitation", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





        detailsLayout = findViewById(R.id.detailsLayout);
        topMenu = findViewById(R.id.profileDetailsTopMenu);
        profileDetailsEditLayout = findViewById(R.id.profileDetailsEditLayout);

        myInvitationsLayout = findViewById(R.id.profileDetailsMyInvitationsLayout);
        myAppliesLayout = findViewById(R.id.profileDetailsMyAppliesLayout);
        profileDetailsLayout = findViewById(R.id.profileDetailsProfileDetails);

        //setup myInvitations e myApplies layout
        myInvitationsListLayout = findViewById(R.id.myInvitationsListLayout);
        myAppliesListLayout = findViewById(R.id.myAppliesListLayout);


        //profile info(if user != myprofile) or my_profile_info if user is logged
        String profileMode = thisIntent.getStringExtra("data").toLowerCase();
        if(profileMode.equals("profileinfo")){
            //invisible layout except for profile details
            signoutButton.setVisibility(View.GONE);
            signoutButton.setClickable(false);
            myInvitationsLayout.setVisibility(View.GONE);
            myAppliesLayout.setVisibility(View.GONE);
            profileDetailsLayout.setVisibility(View.VISIBLE);
            profileDetailsEditLayout.setVisibility(View.GONE);

            //loading profile informations
            loadProfileInfo(thisIntent);
        } else if(profileMode.equals("myprofiledetails")) {
            // Set My Profile selected
            bottomNavigationView.setSelectedItemId(R.id.menu_navigation_myprofile);
            signoutButton.setVisibility(View.VISIBLE);
            signoutButton.setClickable(true);
            //all the layouts are visible
            profileDetailsLayout.setVisibility(View.VISIBLE);
            myInvitationsLayout.setVisibility(View.VISIBLE);
            myAppliesLayout.setVisibility(View.VISIBLE);
            profileDetailsEditLayout.setVisibility(View.VISIBLE);

            //loading my profile details
            loadMyProfileDetails();

            //listener for profile details icon
            profileDetailsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myInvitationsListLayout.setVisibility(View.GONE);
                    myAppliesListLayout.setVisibility(View.GONE);
                    profileDetailsEditLayout.setVisibility(View.VISIBLE);
                    detailsLayout.setVisibility(View.VISIBLE);
                    addInvitation.setVisibility(View.GONE);
                }
            });
            //listener for my invitations icon
            myInvitationsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsLayout.setVisibility(View.GONE);
                    profileDetailsEditLayout.setVisibility(View.GONE);
                    myAppliesListLayout.setVisibility(View.GONE);

                    topMenu.setVisibility(View.VISIBLE);
                    myInvitationsListLayout.setVisibility(View.VISIBLE);
                    addInvitation.setVisibility(View.VISIBLE);

                }
            });
            //listener for my applies icon
            myAppliesLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsLayout.setVisibility(View.GONE);
                    profileDetailsEditLayout.setVisibility(View.GONE);
                    myInvitationsListLayout.setVisibility(View.GONE);
                    addInvitation.setVisibility(View.GONE);

                    topMenu.setVisibility(View.VISIBLE);
                    myAppliesListLayout.setVisibility(View.VISIBLE);
                }
            });

        }
        //edit button setup
        Button editButton = findViewById(R.id.profileDetailsEditButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location.setVisibility(View.GONE);
                bio.setVisibility(View.GONE);

                locationEdit.setVisibility(View.VISIBLE);
                instrumentListEdit.setVisibility(View.VISIBLE);
                addInstrument.setVisibility(View.VISIBLE);
                tagListEdit.setVisibility(View.VISIBLE);
                addTag.setVisibility(View.VISIBLE);
                bioEdit.setVisibility(View.VISIBLE);
                availabilitySwitch.setVisibility(View.VISIBLE);

                closeEdit.setVisibility(View.VISIBLE);
                closeEdit.setClickable(true);
                closeEdit.setFocusable(true);

                setEditText();

                editMode = true;
            }
        });

        //close edit cross icon setup
        closeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMode = false;
                location.setVisibility(View.VISIBLE);
                instrumentList.setVisibility(View.VISIBLE);
                tagList.setVisibility(View.VISIBLE);
                bio.setVisibility(View.VISIBLE);

                locationEdit.setVisibility(View.GONE);
                instrumentListEdit.setVisibility(View.GONE);
                addInstrument.setVisibility(View.GONE);
                tagListEdit.setVisibility(View.GONE);
                addTag.setVisibility(View.GONE);
                bioEdit.setVisibility(View.GONE);
                availabilitySwitch.setVisibility(View.GONE);
                closeEdit.setVisibility(View.GONE);
                closeEdit.setClickable(false);
                closeEdit.setFocusable(false);
            }
        });

        //save button setup
        Button saveButton = findViewById(R.id.profileDetailsSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editMode){
                    saveUpdate();
                }
            }
        });

        //add instrument button setup(for edit mode)
        addInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toBeAddedInstrument = instrumentListEdit.getText().toString();
                if(!toBeAddedInstrument.isEmpty()){
                    addChip(toBeAddedInstrument, instrumentList);
                }
            }
        });

        //add tag button setup(for edit mode)
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toBeAddedTag = tagListEdit.getText().toString();
                if(!toBeAddedTag.isEmpty()){
                    addChip(toBeAddedTag, tagList);
                }
            }
        });
    }

    private void loadProfileInfo(Intent thisIntent){
        UserByUsernameInfoRequest userByUsernameInfoRequest = new UserByUsernameInfoRequest(thisIntent.getStringExtra("user"));
        userApi.userInfo(userByUsernameInfoRequest).enqueue(new Callback<UserProfileInfoResponse>() {
            @Override
            public void onResponse(Call<UserProfileInfoResponse> call, Response<UserProfileInfoResponse> response) {
                if(response.body() != null){
                    profileDetailsSetup(response.body());
                } else {
                    Toast.makeText(ProfileDetailsActivity.this, "Error while loading user " +  call.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileInfoResponse> call, Throwable t) {
                Toast.makeText(ProfileDetailsActivity.this, "Error while loading user " +  call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMyProfileDetails(){
        userApi.myProfileInfo("Bearer " + token).enqueue(new Callback<UserProfileInfoResponse>() {
            @Override
            public void onResponse(Call<UserProfileInfoResponse> call, Response<UserProfileInfoResponse> response) {
                if(response.body() != null){
                    profileDetailsSetup(response.body());
                    List<InvitationId> invIdList = response.body().getInvitationList();
                    if(invIdList != null && !invIdList.isEmpty())
                        populateMyInvitationsList(invIdList);
                    List<InvitationId> applyIdList = response.body().getApplyList();
                    if(applyIdList != null && !applyIdList.isEmpty()){
                        populateMyAppliesList(applyIdList);
                    }
                } else {
                    Toast.makeText(ProfileDetailsActivity.this, "Error while loading user " +  call.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileInfoResponse> call, Throwable t) {
                Toast.makeText(ProfileDetailsActivity.this, "Error while loading user " +  call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateMyInvitationsList(List<InvitationId> invitationIdList){
        Context context = this;
        invitationApi.getInvitationFromId("Bearer " + token, new InvitationFromIdsRequest(invitationIdList)).enqueue(new Callback<List<Invitation>>() {
            @Override
            public void onResponse(Call<List<Invitation>> call, Response<List<Invitation>> response) {
                List<Invitation> invitationList = response.body();
                MyInvitationsAdapter myInvitationsAdapter = new MyInvitationsAdapter(invitationList, context,
                        retrofitService, invitationApi, token);
                myInvitationsView.setAdapter(myInvitationsAdapter);
            }

            @Override
            public void onFailure(Call<List<Invitation>> call, Throwable t) {
                Toast.makeText(ProfileDetailsActivity.this, "Error while loading MyInvitations!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateMyAppliesList(List<InvitationId> invitationIdList){
        Context context = this;
        invitationApi.getInvitationFromId("Bearer " + token, new InvitationFromIdsRequest(invitationIdList)).enqueue(new Callback<List<Invitation>>() {
            @Override
            public void onResponse(Call<List<Invitation>> call, Response<List<Invitation>> response) {
                List<Invitation> appliesList = response.body();
                MyAppliesAdapter myAppliesAdapter = new MyAppliesAdapter(appliesList, context, retrofitService, invitationApi, token);
                myAppliesView.setAdapter(myAppliesAdapter);
            }

            @Override
            public void onFailure(Call<List<Invitation>> call, Throwable t) {
                Toast.makeText(ProfileDetailsActivity.this, "Error while loading MyInvitations!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void profileDetailsSetup(UserProfileInfoResponse userProfileInfoResponse){

        String completeName = userProfileInfoResponse.getName() + " " + userProfileInfoResponse.getSurname();
        fullName.setText(completeName);
        username.setText(userProfileInfoResponse.getUsername());

        boolean available = userProfileInfoResponse.getAvailable();
        if(available){
            availability.setText("AVAILABLE");
            availability.setBackgroundResource(R.drawable.border_green);
            availability.setTextColor(getResources().getColor(R.color.dark_green));
            availabilitySwitch.setChecked(true);
        } else {
            availability.setText("NOT AVAILABLE");
            availability.setBackgroundResource(R.drawable.border_red);
            availability.setTextColor(Color.parseColor("#F44336"));
            availabilitySwitch.setChecked(false);
        }

        location.setText(userProfileInfoResponse.getLocation());

        for(String instrument: userProfileInfoResponse.getInstrumentList()){
            addChip(instrument, instrumentList);
        }

        for(String tag: userProfileInfoResponse.getTagList()){
            addChip(tag, tagList);
        }

        bio.setText(userProfileInfoResponse.getBio());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,120);
        bio.setLayoutParams(params);

    }

    private void setEditText(){
        locationEdit.setText(location.getText());
        bioEdit.setText(bio.getText());

    }

    private void saveUpdate(){
        String locUpdate = locationEdit.getText().toString();

        boolean availableUpdate = availabilitySwitch.isChecked();

        List<String> instrumentsUpdate = new ArrayList<>();
        for (int i=0; i<instrumentList.getChildCount();i++){
            instrumentsUpdate.add(((Chip)instrumentList.getChildAt(i)).getText().toString());
        }
        List<String> tagsUpdate = new ArrayList<>();
        for (int i=0; i<tagList.getChildCount();i++){
            tagsUpdate.add(((Chip)tagList.getChildAt(i)).getText().toString());
        }

        String bioUpdate = bioEdit.getText().toString();
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(bioUpdate, locUpdate, instrumentsUpdate, tagsUpdate, availableUpdate);
        userApi.updateInfo("Bearer " + token,userUpdateRequest).enqueue(new Callback<Response<MessageResponse>>() {
            @Override
            public void onResponse(Call<Response<MessageResponse>> call, Response<Response<MessageResponse>> response) {
                Toast.makeText(ProfileDetailsActivity.this, "User info updated " +  call.toString(), Toast.LENGTH_SHORT).show();
                saveSuccess();
            }

            @Override
            public void onFailure(Call<Response<MessageResponse>> call, Throwable t) {
                Toast.makeText(ProfileDetailsActivity.this, "Error while updating user info " +  call.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSuccess(){
        editMode = false;
        reloadActivity();
    }

    private void reloadActivity(){
        this.recreate();
    }

    private void signout(){
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean result = sharedPreferences.edit().clear().commit();
        if(result) {
            Intent intent = new Intent(this, SignActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(ProfileDetailsActivity.this, "Error while trying to signout! Please, contact the support", Toast.LENGTH_SHORT).show();
        }
    }

    private void addChip(String pItem, ChipGroup pChipGroup) {
        Chip lChip = new Chip(this);
        lChip.setText(pItem);
        lChip.setChipIconResource(R.drawable.x_icon);
        lChip.setChipIconSize(30);
        lChip.setIconStartPadding(10);
        lChip.setIconEndPadding(10);
        lChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pChipGroup.removeView(lChip);
            }
        });
        pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
    }
}
