package com.example.jamz.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.R;
import com.google.android.material.chip.ChipGroup;

public class MyAppliesHolder extends RecyclerView.ViewHolder {

    TextView title, genre, instrument, description, dateTime, type, creator, tagsNone, candidateListText;
    Button removeApply;
    ChipGroup tagList;

    public MyAppliesHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.myAppliesListItemTitle);
        genre = itemView.findViewById(R.id.myAppliesListItemGenre);
        instrument = itemView.findViewById(R.id.myAppliesListItemInstrument);
        description = itemView.findViewById(R.id.myAppliesListItemDescription);
        dateTime = itemView.findViewById(R.id.myAppliesListItemDate);
        type = itemView.findViewById(R.id.myAppliesListItemType);
        creator = itemView.findViewById(R.id.myAppliesListItemCreator);
        candidateListText = itemView.findViewById(R.id.myAppliesListItemCandidateList);
        tagList = itemView.findViewById(R.id.myAppliesListItemTagListChipGroup);
        tagsNone = itemView.findViewById(R.id.myAppliesListItemTagsNoneText);
        removeApply = itemView.findViewById(R.id.myAppliesListItemRemoveApply);

    }
}
