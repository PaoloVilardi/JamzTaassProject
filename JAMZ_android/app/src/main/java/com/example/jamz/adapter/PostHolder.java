package com.example.jamz.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.R;
import com.google.android.material.chip.ChipGroup;

public class PostHolder extends RecyclerView.ViewHolder {
    TextView creator, title, open, description, genre, type, instrument, tagsNone;
    ImageView profilePic;
    ChipGroup tagList;
    Button applyButton;

    public PostHolder(@NonNull View itemView) {
        super(itemView);
        creator = itemView.findViewById(R.id.postListItem_creator);
        profilePic = itemView.findViewById(R.id.postListItem_profilePic);
        title = itemView.findViewById(R.id.postListItem_title);
        open = itemView.findViewById(R.id.postListItem_open);
        description = itemView.findViewById(R.id.postListItemDescription);
        genre = itemView.findViewById(R.id.postListItem_genre);
        type = itemView.findViewById(R.id.postListItem_type);
        instrument = itemView.findViewById(R.id.postListItem_instrument);
        tagList = itemView.findViewById(R.id.postListItem_tagsChipGroup);
        tagsNone = itemView.findViewById(R.id.postListItemTagsNone);
        applyButton = itemView.findViewById(R.id.postListItemApplyButton);

    }
}
