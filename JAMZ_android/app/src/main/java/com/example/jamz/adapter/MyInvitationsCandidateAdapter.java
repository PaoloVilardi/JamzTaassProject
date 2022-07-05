package com.example.jamz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.ProfileDetailsActivity;
import com.example.jamz.R;
import com.example.jamz.payload.payload.request.InvitationModifyAcceptanceRequest;
import com.example.jamz.payload.payload.response.MessageResponse;
import com.example.jamz.retrofit.InvitationApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInvitationsCandidateAdapter extends RecyclerView.Adapter<MyInvitationsCandidateHolder>{
    List<String> candidateList;
    String acceptanceStatus;
    boolean open;
    InvitationApi invitationApi;
    Context context;
    String token;
    String invitationId;

    public MyInvitationsCandidateAdapter(List<String> candidateList, Context context, String acceptanceStatus, boolean open, InvitationApi invitationApi, String token, String invitationId) {
        this.candidateList = candidateList;
        this.context = context;
        this.acceptanceStatus = acceptanceStatus;
        this.open = open;
        this.invitationApi = invitationApi;
        this.token = token;
        this.invitationId = invitationId;
    }

    @NonNull
    @Override
    public MyInvitationsCandidateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_invitations_candidate_item, parent, false);
        return new MyInvitationsCandidateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyInvitationsCandidateHolder holder, int position) {
        String candidate = candidateList.get(position);
        if(open){
            ImageButton acceptanceButton = holder.candidateAcceptanceButton;
            acceptanceButton.setVisibility(View.VISIBLE);
            //acceptanceButton.setClickable(true);
            //acceptanceButton.setText("ACCEPT");
            String finalCandidate = candidate;
            acceptanceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    invitationApi.modifyAcceptance("Bearer " + token, new InvitationModifyAcceptanceRequest(invitationId, finalCandidate)).enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            Toast.makeText(context, "User accepted successfully", Toast.LENGTH_SHORT).show();
                            ((ProfileDetailsActivity) context).getIntent().putExtra("data", "MyProfileDetails");
                            ((ProfileDetailsActivity) context).getIntent().putExtra("username", "MyProfileDetails");
                            ((ProfileDetailsActivity)context).recreate();
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(context, "Error!!! Cannot accept this user", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            holder.candidateAcceptanceButton.setVisibility(View.GONE);
            if(acceptanceStatus != null && acceptanceStatus.toLowerCase().equals(candidate.toLowerCase())){
                candidate += "[ACCEPTED]";
            }
        }
        holder.candidateText.setText(candidate);

    }

    @Override
    public int getItemCount() {
        return candidateList.size();
    }
}
