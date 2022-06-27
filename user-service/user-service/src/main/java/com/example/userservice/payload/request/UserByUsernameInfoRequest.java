package com.example.userservice.payload.request;

public class UserByUsernameInfoRequest {
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserByUsernameInfoRequest(String username) {
        this.username = username;
    }

    public UserByUsernameInfoRequest() {
        super();
    }
}
