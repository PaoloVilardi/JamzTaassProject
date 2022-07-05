package com.example.jamz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.jamz.payload.payload.request.LoginRequest;
import com.example.jamz.payload.payload.response.UserInfoResponse;
import com.example.jamz.retrofit.AuthApi;
import com.example.jamz.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {
    MaterialButton loginbtn;
    TextView username, password;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        context = getActivity();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        loginbtn = root.findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username =(TextView) root.findViewById(R.id.username);
                password =(TextView) root.findViewById(R.id.password);
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                if(!usernameValue.isEmpty() && !passwordValue.isEmpty()){
                    RetrofitService retrofitService = new RetrofitService();
                    AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);
                    LoginRequest loginRequest = new LoginRequest(usernameValue, passwordValue);
                    authApi.login(loginRequest).enqueue(new Callback<UserInfoResponse>() {
                        @Override
                        public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                            if (response.body() != null) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", response.body().getAccess_token());
                                editor.putString("username", response.body().getUsername());
                                editor.apply();
                                changeActivity();

                            } else {
                                Toast.makeText(context, "Error while trying to login...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                            Toast.makeText(context, "Error while trying to login...", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //authApi.signUp(new SignupRequest(username, email, password, name, surname, instrument));
                } else {
                    Toast.makeText(context, "Fields are empty or not valid...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return root;
    }

    private void changeActivity(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
