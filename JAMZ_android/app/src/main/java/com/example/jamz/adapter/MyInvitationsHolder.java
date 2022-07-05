package com.example.jamz.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.R;
import com.google.android.material.chip.ChipGroup;

public class MyInvitationsHolder extends RecyclerView.ViewHolder {

    TextView title, genre, instrument, description, dateTime, type, creator, tagsNone, candidateListText, status;
    ChipGroup tagList;
    Button closeInvitation, removeInvitation;
    RecyclerView candidateView;

    public MyInvitationsHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.myInvitationsListItemTitle);
        status = itemView.findViewById(R.id.myInvitationsListItemStatus);
        genre = itemView.findViewById(R.id.myInvitationsListItemGenre);
        instrument = itemView.findViewById(R.id.myInvitationsListItemInstrument);
        description = itemView.findViewById(R.id.myInvitationsListItemDescription);
        dateTime = itemView.findViewById(R.id.myInvitationsListItemDate);
        type = itemView.findViewById(R.id.myInvitationsListItemType);
        creator = itemView.findViewById(R.id.myInvitationsListItemCreator);
        //candidateListText = itemView.findViewById(R.id.myInvitationsListItemCandidateList);
        tagList = itemView.findViewById(R.id.myInvitationsListItemTagListChipGroup);
        tagsNone = itemView.findViewById(R.id.myInvitationsListItemTagsNoneText);
        closeInvitation = itemView.findViewById(R.id.myInvitationsListItemCloseButton);
        removeInvitation = itemView.findViewById(R.id.myInvitationsListItemDeleteButton);
        candidateView = itemView.findViewById(R.id.myInvitationsListItemCandidateView);
    }
}
