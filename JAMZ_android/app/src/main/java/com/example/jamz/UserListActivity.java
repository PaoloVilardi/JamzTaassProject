package com.example.jamz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jamz.adapter.UserAdapter;
import com.example.jamz.model.User;
import com.example.jamz.retrofit.RetrofitService;
import com.example.jamz.retrofit.UserApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String username  = sharedPreferences.getString("username", "");

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Profiles selected
        bottomNavigationView.setSelectedItemId(R.id.menu_navigation_profiles);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.menu_navigation_profiles:
                        return true;
                    case R.id.menu_navigation_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
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

        recyclerView = findViewById(R.id.userList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(new ArrayList<User>(), this));
        loadUsers();

    }

    private void loadUsers(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        System.out.println(userApi.test());
        userApi.getUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(UserListActivity.this, "Failed to load users " +  call.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<User> userList) {
        UserAdapter userAdapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(userAdapter);
    }

    private void startProfileDetailsActivity(String username){
        Intent intent = new Intent(this, ProfileDetailsActivity.class);
        intent.putExtra("data", "MyProfileDetails");
        intent.putExtra("user", username);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }
}