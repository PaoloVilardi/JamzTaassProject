package com.example.jamz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String username  = sharedPreferences.getString("username", "");

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.menu_navigation_home);


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.menu_navigation_profiles:
                        startActivity(new Intent(getApplicationContext(), UserListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_navigation_home:
                        return true;
                    case R.id.menu_navigation_posts:
                        startActivity(new Intent(getApplicationContext(),PostListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menu_navigation_myprofile:
                        startProfileDetailsActivity(username);
                        return true;
                }
                return false;
            }
        });



    }

    private void startProfileDetailsActivity(String username){
        Intent intent = new Intent(this, ProfileDetailsActivity.class);
        intent.putExtra("data", "MyProfileDetails");
        intent.putExtra("user", username);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}