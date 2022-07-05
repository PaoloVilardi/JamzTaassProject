package com.example.jamz.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jamz.R;

public class MyInvitationsCandidateHolder extends RecyclerView.ViewHolder{

    TextView candidateText;
    ImageButton candidateAcceptanceButton;

    public MyInvitationsCandidateHolder(@NonNull View itemView) {
        super(itemView);
        candidateText = itemView.findViewById(R.id.myInvitationsListItemCandidateText);
        candidateAcceptanceButton = itemView.findViewById(R.id.myInvitationsListItemCandidateAcceptanceButton);
    }
}
