package com.example.authservice.payload.response;

import java.util.List;

public class UserInfoResponse {
    private Long id;
    private String username;
    private String access_token;
    private String refresh_token;

    public UserInfoResponse(Long id, String username, String access_token, String refresh_token) {
        this.id = id;
        this.username = username;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
