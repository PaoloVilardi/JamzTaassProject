package com.example.jamz;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.jamz.payload.payload.request.SignupRequest;
import com.example.jamz.payload.payload.response.MessageResponse;
import com.example.jamz.retrofit.AuthApi;
import com.example.jamz.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupTabFragment extends Fragment {

    MaterialButton signupbtn;
    TextView usernameSignup, passwordSignup, email, name, surname, instrument;
    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        context = getActivity();
        signupbtn = root.findViewById(R.id.signupbtn);
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameSignup =(TextView) root.findViewById(R.id.username_signup);
                passwordSignup =(TextView) root.findViewById(R.id.password_signup);
                email =(TextView) root.findViewById(R.id.email);
                name =(TextView) root.findViewById(R.id.name);
                surname =(TextView) root.findViewById(R.id.surname);
                instrument =(TextView) root.findViewById(R.id.instrument);

                String usernameValue = usernameSignup.getText().toString();
                String passwordValue = passwordSignup.getText().toString();
                String emailValue = email.getText().toString();
                String nameValue = name.getText().toString();
                String surnameValue = surname.getText().toString();
                String instrumentValue = instrument.getText().toString();
                if(!usernameValue.isEmpty() && !passwordValue.isEmpty() && !emailValue.isEmpty()  &&
                        !nameValue.isEmpty()  && !surnameValue.isEmpty()  && !instrumentValue.isEmpty()){
                    RetrofitService retrofitService = new RetrofitService();
                    AuthApi authApi = retrofitService.getRetrofit().create(AuthApi.class);
                    SignupRequest signupRequest = new SignupRequest(usernameValue, emailValue, passwordValue, nameValue, surnameValue, instrumentValue);
                    authApi.signUp(signupRequest).enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            if (response.body() != null) {
                                Toast.makeText(context, response.body().getMessage() + " You can proceed to login now!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error during registration...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(context, "Error during registration...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(context, "Fields are empty or not valid...", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return root;
    }
}
