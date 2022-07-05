package com.example.jamz.payload.payload.response;

import com.example.jamz.model.InvitationId;

import java.util.List;

public class UserProfileInfoResponse {
    private String id;
    private String username;
    private String name;
    private String surname;
    private String location;
    private List<String> instrumentList;
    private List<String> tagList;
    private String bio;
    private Boolean available;
    private List<InvitationId> invitationList;
    private List<InvitationId> applyList;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<InvitationId> getInvitationList() {
        return invitationList;
    }

    public void setInvitationList(List<InvitationId> invitationList) {
        this.invitationList = invitationList;
    }

    public List<InvitationId> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<InvitationId> applyList) {
        this.applyList = applyList;
    }

    public UserProfileInfoResponse(String id, String username, String name, String surname, String location, List<String> instrumentList, List<String> tagList, String bio, Boolean available, List<InvitationId> invitationList, List<InvitationId> applyList) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.instrumentList = instrumentList;
        this.tagList = tagList;
        this.bio = bio;
        this.available = available;
        this.invitationList = invitationList;
        this.applyList = applyList;
    }

    public UserProfileInfoResponse() {
        super();
    }


}
