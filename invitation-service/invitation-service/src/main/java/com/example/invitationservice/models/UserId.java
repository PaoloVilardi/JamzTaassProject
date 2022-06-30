package com.example.invitationservice.models;

public class UserId {
    private static final String className = "user";
    private String id;
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserId(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
