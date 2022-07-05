package com.example.jamz.retrofit;

import com.example.jamz.payload.payload.request.LoginRequest;
import com.example.jamz.payload.payload.request.SignupRequest;
import com.example.jamz.payload.payload.response.MessageResponse;
import com.example.jamz.payload.payload.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    static final String BASE_URL = "/api/v1/auth/";

    @POST(BASE_URL + "signup")
    Call<MessageResponse> signUp(@Body SignupRequest signupRequest);

    @POST(BASE_URL + "login")
    Call<UserInfoResponse> login(@Body LoginRequest loginRequest);
}
