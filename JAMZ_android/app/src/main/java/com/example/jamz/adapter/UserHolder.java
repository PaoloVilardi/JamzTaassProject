package com.example.jamz.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.R;
import com.google.android.material.chip.ChipGroup;

public class UserHolder extends RecyclerView.ViewHolder {
    TextView username, location, availability, bio, instrumentListNone, tagListNone;
    ImageView profilePic;
    ChipGroup instrumentList, tagList;

    public UserHolder(@NonNull View itemView) {
        super(itemView);
        profilePic = itemView.findViewById(R.id.userListItem_profilePic);
        username = itemView.findViewById(R.id.userListItem_username);
        location = itemView.findViewById(R.id.userListItem_location);
        availability = itemView.findViewById(R.id.userListItem_availability);
        bio = itemView.findViewById(R.id.userListItem_bio);
        instrumentList = itemView.findViewById(R.id.userListItemInstrumentListChipGroup);
        tagList = itemView.findViewById(R.id.userListItemTagListChipGroup);
        instrumentListNone = itemView.findViewById(R.id.userListItemInstrumentListNone);
        tagListNone = itemView.findViewById(R.id.userListItemTagListNone);

    }
}
