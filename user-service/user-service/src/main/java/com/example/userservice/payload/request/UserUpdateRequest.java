package com.example.userservice.payload.request;

import java.util.List;

public class UserUpdateRequest {

    private String bio;
    private String location;
    private List<String> instrumentList;
    private List<String> tagList;
    private Boolean available;


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<String> instrumentList) {
        this.instrumentList = instrumentList;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public UserUpdateRequest(String bio, String location, List<String> instrumentList, List<String> tagList, Boolean available) {
        this.bio = bio;
        this.location = location;
        this.instrumentList = instrumentList;
        this.tagList = tagList;
        this.available = available;
    }

    public UserUpdateRequest() {
        super();
    }
}
