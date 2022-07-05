package com.example.jamz.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jamz.ProfileDetailsActivity;
import com.example.jamz.R;
import com.example.jamz.model.User;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    private List<User> userList;
    Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userList.get(position);

        ImageView proPic = holder.profilePic;
        proPic.setFocusable(true);
        proPic.setClickable(true);
        proPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileDetailsActivity.class);
                intent.putExtra("data", "ProfileInfo");
                intent.putExtra("user", user.getUsername());
                context.startActivity(intent);
            }
        });

        holder.username.setText(user.getUsername());
        holder.location.setText(user.getLocation());
        boolean available = user.isAvailable();
        TextView availability = holder.availability;

        ChipGroup instrumentList = holder.instrumentList;
        ChipGroup tagList = holder.tagList;

        if(available){
            availability.setText("AVAILABLE");
            availability.setBackgroundResource(R.drawable.border_green);
            availability.setTextColor(context.getResources().getColor(R.color.dark_green));
        } else {
            availability.setText("NOT AVAILABLE");
            availability.setBackgroundResource(R.drawable.border_red);
            availability.setTextColor(Color.parseColor("#F44336"));
        }

        holder.bio.setText(user.getBio());

        if(user.getInstrumentList() != null && !user.getInstrumentList().isEmpty()) {
            holder.instrumentListNone.setVisibility(View.GONE);
            for (String instrument : user.getInstrumentList()) {
                addChip(instrument, instrumentList);
            }
        } else {
            instrumentList.setVisibility(View.GONE);
            holder.instrumentListNone.setVisibility(View.VISIBLE);
        }

        if(user.getTagList() != null && !user.getTagList().isEmpty()) {
            holder.tagListNone.setVisibility(View.GONE);
            for (String tag : user.getTagList()) {
                addChip(tag, tagList);
            }
        } else {
            tagList.setVisibility(View.GONE);
            holder.tagListNone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void addChip(String pItem, ChipGroup pChipGroup) {
        Chip lChip = new Chip(context);
        lChip.setText(pItem);
        pChipGroup.addView(lChip, pChipGroup.getChildCount() - 1);
    }
}
