package com.example.jamz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.ProfileDetailsActivity;
import com.example.jamz.R;
import com.example.jamz.model.Invitation;
import com.example.jamz.payload.payload.request.InvitationEliminationRequest;
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

public class MyInvitationsAdapter extends RecyclerView.Adapter<MyInvitationsHolder> {

    List<Invitation> myInvitationsList;
    Context context;
    RetrofitService retrofitService;
    InvitationApi invitationApi;
    String token;



    public MyInvitationsAdapter(List<Invitation> myInvitationsList, Context context, RetrofitService retrofitService, InvitationApi invitationApi, String token) {
        this.myInvitationsList = myInvitationsList;
        this.context = context;
        this.retrofitService = retrofitService;
        this.invitationApi = invitationApi;
        this.token = token;
    }

    @NonNull
    @Override
    public MyInvitationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_invitations_item, parent, false);
        return new MyInvitationsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyInvitationsHolder holder, int position) {
        Invitation invitation = myInvitationsList.get(position);
        holder.creator.setText(invitation.getCreator());
        holder.type.setText(invitation.getInvitationType());
        holder.description.setText(invitation.getDescription());
        holder.title.setText(invitation.getTitle());
        holder.genre.setText(invitation.getGenre());
        holder.instrument.setText(invitation.getInstrument());
        String localDateTimeText = "Date: " + invitation.getLocalDateTime();
        holder.dateTime.setText(localDateTimeText);

        holder.candidateView.setLayoutManager(new LinearLayoutManager(context));
        List<String> candidateList = invitation.getCandidateList() != null ? invitation.getCandidateList() : new ArrayList<String>();
        holder.candidateView.setAdapter(new MyInvitationsCandidateAdapter(candidateList,
                context, invitation.getAcceptanceStatus(), invitation.isOpen(), invitationApi, token, invitation.getId()));

        /*TextView candidateList = holder.candidateListText;
        String candidateListText = "";
        if(invitation.getCandidateList() != null && !invitation.getCandidateList().isEmpty()){
            for(String candidate : invitation.getCandidateList()){
                String candidateText = candidate;
                if(invitation.getAcceptanceStatus() != null){
                    System.out.println("ACCEPTANCE NOT NULL");
                    if(invitation.getAcceptanceStatus().toLowerCase().equals(candidate.toLowerCase())){
                        System.out.println("ACCEPTANCE UGUALE CANDIDATE");
                        candidateText += "[ACCEPTED]";
                    }
                }
                candidateListText += candidate + ", ";
            }
        }
        candidateList.setText(candidateListText);*/

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

        if(invitation.isOpen()){
            holder.status.setText("[OPEN]");
            holder.closeInvitation.setVisibility(View.VISIBLE);
            holder.removeInvitation.setVisibility(View.GONE);
            holder.closeInvitation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invitationApi.closeInvitation("Bearer " + token, new InvitationEliminationRequest(invitation.getId())).enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            Toast.makeText(context, "Invitation closed succesfully!", Toast.LENGTH_SHORT).show();
                            ((ProfileDetailsActivity) context).getIntent().putExtra("data", "MyProfileDetails");
                            ((ProfileDetailsActivity) context).getIntent().putExtra("username", "MyProfileDetails");
                            ((ProfileDetailsActivity)context).recreate();

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(context, "Error while trying to close invitation", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            holder.status.setText("[CLOSED]");
            holder.closeInvitation.setVisibility(View.GONE);
            holder.removeInvitation.setVisibility(View.VISIBLE);
            holder.removeInvitation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invitationApi.deleteInvitation("Bearer " + token, new InvitationEliminationRequest(invitation.getId())).enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            Toast.makeText(context, "Invitation deleted succesfully!", Toast.LENGTH_SHORT).show();
                            ((ProfileDetailsActivity) context).getIntent().putExtra("data", "MyProfileDetails");
                            ((ProfileDetailsActivity) context).getIntent().putExtra("username", "MyProfileDetails");
                            ((ProfileDetailsActivity)context).recreate();
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(context, "Error while trying to delete invitation", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return myInvitationsList.size();
    }

    private void addChip(String pItem, ChipGroup pChipGroup) {
        Chip lChip = new Chip(context);
        lChip.setText(pItem);
        pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
    }
}
