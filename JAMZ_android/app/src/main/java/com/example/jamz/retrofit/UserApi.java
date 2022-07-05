package com.example.jamz.retrofit;

import com.example.jamz.model.User;
import com.example.jamz.payload.payload.request.UserByUsernameInfoRequest;
import com.example.jamz.payload.payload.request.UserUpdateRequest;
import com.example.jamz.payload.payload.response.MessageResponse;
import com.example.jamz.payload.payload.response.UserProfileInfoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {
    static final String BASE_URL = "/api/v1/user/";

    @GET(BASE_URL + "list")
    Call<List<User>> getUsers();

    @GET(BASE_URL + "test")
    Call<String> test();

    @POST(BASE_URL + "update")
    Call<Response<MessageResponse>> updateInfo(@Header("Authorization") String authorization, @Body UserUpdateRequest userUpdateRequest);

    @POST(BASE_URL + "get_profile")
    Call<UserProfileInfoResponse> userInfo(@Body UserByUsernameInfoRequest userByUsernameInfoRequest);

    @GET(BASE_URL + "my_profile")
    Call<UserProfileInfoResponse> myProfileInfo(@Header("Authorization") String authorization);


}
