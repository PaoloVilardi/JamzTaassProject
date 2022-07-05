package com.example.jamz;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.adapter.PostAdapter;
import com.example.jamz.model.Invitation;
import com.example.jamz.model.User;
import com.example.jamz.payload.payload.request.InvitationFilterRequest;
import com.example.jamz.retrofit.InvitationApi;
import com.example.jamz.retrofit.RetrofitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout advSearchLayout;
    private Button advSearchFilteredSearchButton;
    private ImageButton advSearchButton, advSearchAddTagButton;
    private Spinner advSearchSpinnerType, advSearchSpinnerStatus;
    private EditText advSearchGenre, advSearchInstrument, advSearchTag;
    private ImageView closeAdvancedSearch;
    private ChipGroup advSearchTagChipGroup;

    RetrofitService retrofitService = new RetrofitService();
    InvitationApi invitationApi = retrofitService.getRetrofit().create(InvitationApi.class);

    //TODO TERMINARE IL DESIGN DELLA RICERCA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String username  = sharedPreferences.getString("username", "");

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Posts selected
        bottomNavigationView.setSelectedItemId(R.id.menu_navigation_posts);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.menu_navigation_posts:
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
                        startProfileDetailsActivity(username);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.postList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PostAdapter(new ArrayList<Invitation>(), this));

        advSearchLayout = findViewById(R.id.postListAdvancedSearchFilters);

        closeAdvancedSearch = findViewById(R.id.postListCloseAdvancedSearch);
        closeAdvancedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advSearchLayout.setVisibility(View.GONE);

                closeAdvancedSearch.setVisibility(View.GONE);
                closeAdvancedSearch.setClickable(false);
                closeAdvancedSearch.setFocusable(false);
            }
        });

        advSearchTag = findViewById(R.id.postListAdvancedSearchEditTextTag);
        advSearchTagChipGroup = findViewById(R.id.postListAdvancedSearchTagChipGroup);
        advSearchAddTagButton = findViewById(R.id.postListAdvancedSearchAddTagButton);
        advSearchAddTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag = advSearchTag.getText().toString();
                if(!tag.isEmpty()){
                    addChip(tag, advSearchTagChipGroup);
                }
            }
        });

        advSearchSpinnerType = findViewById(R.id.postListAdvancedSearchType);
        String[] types = new String[]{"gig", "jam", "band member"};
        ArrayAdapter<String> spinnerTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, types);
        advSearchSpinnerType.setAdapter(spinnerTypeAdapter);

        advSearchSpinnerStatus = findViewById(R.id.postListAdvancedSearchStatus);
        String[] statusArray = new String[]{"open", "closed"};
        ArrayAdapter<String> spinnerStatusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, statusArray);
        advSearchSpinnerStatus.setAdapter(spinnerStatusAdapter);

        advSearchGenre = findViewById(R.id.postListAdvancedSearchGenre);

        advSearchInstrument = findViewById(R.id.postListAdvancedSearchInstrument);

        advSearchButton = findViewById(R.id.postListAdvancedSearchButton);
        advSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advSearchLayout.setVisibility(View.VISIBLE);

                closeAdvancedSearch.setVisibility(View.VISIBLE);
                closeAdvancedSearch.setClickable(true);
                closeAdvancedSearch.setFocusable(true);
            }
        });

        advSearchFilteredSearchButton = findViewById(R.id.postListAdvancedSearchFilteredSearchButton);
        advSearchFilteredSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String genre = advSearchGenre.getText().toString();
                String instrument = advSearchInstrument.getText().toString();
                String type = advSearchSpinnerType.getSelectedItem().toString();
                boolean status = (advSearchSpinnerStatus.getSelectedItem().toString().equals("open"));
                List<String> tag_List = new ArrayList<>();
                for (int i=0; i<advSearchTagChipGroup.getChildCount();i++){
                    tag_List.add(((Chip)advSearchTagChipGroup.getChildAt(i)).getText().toString());
                }
                System.out.println("GENRE = " + genre + " INSTRUMENT = " + instrument + " TYPE = " + type + " STATUS = " + status + " TAGLIST = " + tag_List);
                InvitationFilterRequest invitationFilterRequest = new InvitationFilterRequest(type, genre, instrument, tag_List, status);
                invitationApi.getFilteredInvitations(invitationFilterRequest).enqueue(new Callback<List<Invitation>>() {
                    @Override
                    public void onResponse(Call<List<Invitation>> call, Response<List<Invitation>> response) {
                        System.out.println("BODY = " + response.body());
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Invitation>> call, Throwable t) {
                        Toast.makeText(PostListActivity.this, "Failed to do filtered search", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        loadPosts(retrofitService, invitationApi);

    }

    private void loadPosts(RetrofitService retrofitService, InvitationApi invitationApi){
        invitationApi.getInvitations()
                .enqueue(new Callback<List<Invitation>>() {
                    @Override
                    public void onResponse(Call<List<Invitation>> call, Response<List<Invitation>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Invitation>> call, Throwable t) {
                        Toast.makeText(PostListActivity.this, "Failed to load posts " +  call.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Invitation> invitationList) {
        PostAdapter postAdapter = new PostAdapter(invitationList, this);
        recyclerView.setAdapter(postAdapter);
    }

    private void startProfileDetailsActivity(String username){
        Intent intent = new Intent(this, ProfileDetailsActivity.class);
        intent.putExtra("data", "MyProfileDetails");
        intent.putExtra("user", username);
        startActivity(intent);
        overridePendingTransition(0,0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadPosts(retrofitService, invitationApi);
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