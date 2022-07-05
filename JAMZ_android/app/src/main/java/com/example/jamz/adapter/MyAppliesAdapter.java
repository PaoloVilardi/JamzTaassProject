package com.example.jamz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.ProfileDetailsActivity;
import com.example.jamz.R;
import com.example.jamz.model.Invitation;
import com.example.jamz.payload.payload.request.RemoveApplyRequest;
import com.example.jamz.payload.payload.response.MessageResponse;
import com.example.jamz.retrofit.InvitationApi;
import com.example.jamz.retrofit.RetrofitService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAppliesAdapter extends RecyclerView.Adapter<MyAppliesHolder> {


    List<Invitation> myAppliesList;
    Context context;
    RetrofitService retrofitService;
    InvitationApi invitationApi;
    String token;

    public MyAppliesAdapter(List<Invitation> myAppliesList, Context context, RetrofitService retrofitService, InvitationApi invitationApi, String token) {
        if(myAppliesList != null){
            this.myAppliesList = myAppliesList;
        } else {
            this.myAppliesList = new ArrayList<Invitation>();
        }
        this.context = context;
        this.retrofitService = retrofitService;
        this.invitationApi = invitationApi;
        this.token = token;
    }

    @NonNull
    @Override
    public MyAppliesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_applies_item, parent, false);
        return new MyAppliesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAppliesHolder holder, int position) {
        Invitation invitation = myAppliesList.get(position);
        holder.creator.setText(invitation.getCreator());
        holder.type.setText(invitation.getInvitationType());
        holder.description.setText(invitation.getDescription());
        holder.title.setText(invitation.getTitle());
        holder.genre.setText(invitation.getGenre());
        holder.instrument.setText(invitation.getInstrument());
        String localDateTimeText = "Date: " + invitation.getLocalDateTime();
        holder.dateTime.setText(localDateTimeText);

        TextView candidateList = holder.candidateListText;
        String candidateListText = "";
        if(invitation.getCandidateList() != null && !invitation.getCandidateList().isEmpty()){
            for(String candidate : invitation.getCandidateList()){
                String candidateText = candidate;
                if(invitation.getAcceptanceStatus() != null){
                    if(invitation.getAcceptanceStatus().toLowerCase().equals(candidate.toLowerCase())){
                        candidateText += "[ACCEPTED]";
                    }
                }
                candidateListText += candidateText + ", ";
            }
        }
        candidateList.setText(candidateListText);

        ChipGroup tagListGroup = holder.tagList;
        if(invitation.getTagList() != null && !invitation.getTagList().isEmpty()){
            holder.tagsNone.setVisibility(View.GONE);
            for(String tag: invitation.getTagList()){
                addChip(tag, tagListGroup);
            }
        } else {
            tagListGroup.setVisibility(View.GONE);
            holder.tagsNone.setVisibility(View.VISIBLE);
        }

        Button removeApply = holder.removeApply;
        removeApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invitationApi.removeApply("Bearer " + token, new RemoveApplyRequest(invitation.getId())).enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        Toast.makeText(context, "Apply removed successfully!", Toast.LENGTH_SHORT).show();
                        ((ProfileDetailsActivity) context).getIntent().putExtra("data", "MyProfileDetails");
                        ((ProfileDetailsActivity) context).getIntent().putExtra("username", "MyProfileDetails");
                        ((ProfileDetailsActivity)context).recreate();
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        Toast.makeText(context, "Error while trying to remove the apply", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return myAppliesList.size();
    }

    private void addChip(String pItem, ChipGroup pChipGroup) {
        Chip lChip = new Chip(context);
        lChip.setText(pItem);
        pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
    }
}
