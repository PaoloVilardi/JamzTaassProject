package com.example.jamz.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.PostListActivity;
import com.example.jamz.ProfileDetailsActivity;
import com.example.jamz.R;
import com.example.jamz.model.Invitation;
import com.example.jamz.model.User;
import com.example.jamz.payload.payload.request.InvitationApplyRequest;
import com.example.jamz.payload.payload.request.UserByUsernameInfoRequest;
import com.example.jamz.payload.payload.response.MessageResponse;
import com.example.jamz.payload.payload.response.UserProfileInfoResponse;
import com.example.jamz.retrofit.InvitationApi;
import com.example.jamz.retrofit.RetrofitService;
import com.example.jamz.retrofit.UserApi;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostHolder> {

    private List<Invitation> invitationList;
    Context context;

    boolean userApplied = true;


    public PostAdapter(List<Invitation> invitationList, Context context) {
        this.invitationList = invitationList;
        this.context = context;

    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_post_item, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        Invitation invitation = invitationList.get(position);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String token = sharedPreferences.getString("token", "");
        String userLogged = sharedPreferences.getString("username", "");
        RetrofitService retrofitService = new RetrofitService();
        InvitationApi invitationApi = retrofitService.getRetrofit().create(InvitationApi.class);

        holder.creator.setText(invitation.getCreator());

        ImageView proPic = holder.profilePic;
        proPic.setFocusable(true);
        proPic.setClickable(true);
        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileDetailsActivity.class);
                intent.putExtra("data", "ProfileInfo");
                intent.putExtra("user", invitation.getCreator());
                context.startActivity(intent);
            }
        });

        holder.title.setText(invitation.getTitle());
        holder.instrument.setText(invitation.getInstrument());
        holder.genre.setText(invitation.getGenre());
        holder.type.setText(invitation.getInvitationType());

        ChipGroup tagList = holder.tagList;

        TextView open = holder.open;
        if(invitation.isOpen()){
            open.setText("Open");
            open.setBackgroundResource(R.drawable.border_green);
            open.setTextColor(context.getResources().getColor(R.color.dark_green));
        } else {
            open.setText("Closed");
            open.setBackgroundResource(R.drawable.border_red);
            open.setTextColor(Color.parseColor("#F44336"));
        }
        holder.description.setText(invitation.getDescription());

        if(invitation.getTagList() != null && !invitation.getTagList().isEmpty()){
            holder.tagsNone.setVisibility(View.GONE);
            tagList.setVisibility(View.VISIBLE);
            for(String tag: invitation.getTagList()){
                addChip(tag, tagList);
            }
        } else {
            holder.tagsNone.setVisibility(View.VISIBLE);
            tagList.setVisibility(View.GONE);
        }

        Button applyButton = holder.applyButton;
        if(invitation.isOpen() && !token.isEmpty() && !userLogged.isEmpty()){
            InvitationApplyRequest invitationApplyRequest = new InvitationApplyRequest(invitation.getId());

            invitationApi.isApplied("Bearer " + token, invitationApplyRequest).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    userApplied = Boolean.TRUE.equals(response.body());
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    userApplied = true;
                    Toast.makeText(context, "Error while trying to apply, cannot verify your apply status!", Toast.LENGTH_SHORT).show();
                }
            });
            if(!userApplied && !userLogged.toLowerCase().equals(invitation.getCreator().toLowerCase())) {
                applyButton.setVisibility(View.VISIBLE);
                applyButton.setClickable(true);
                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        invitationApi.applyForInvitation("Bearer " + token, invitationApplyRequest).enqueue(new Callback<MessageResponse>() {
                            @Override
                            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                                Toast.makeText(context, "Applied done successfully!", Toast.LENGTH_SHORT).show();
                                applyButton.setVisibility(View.GONE);
                                applyButton.setClickable(false);
                            }

                            @Override
                            public void onFailure(Call<MessageResponse> call, Throwable t) {
                                Toast.makeText(context, "Error while trying to apply!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        } else {
            applyButton.setClickable(false);
            applyButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return invitationList.size();
    }

    private void addChip(String pItem, ChipGroup pChipGroup) {
        Chip lChip = new Chip(context);
        lChip.setText(pItem);
        pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
    }
}
