package com.example.jamz.model;

import java.util.ArrayList;
import java.util.List;

public class User {


    private String id;



    private String username;


    private String email;

    private String password;

    private String name;
    private String surname;

    private List<String> instrumentList;
    private List<String> tagList;

    private String bio;

    private String location;

    private boolean available;



    private List<InvitationId> invitationList;

    private List<InvitationId> applyList;


    private List<String> roles = new ArrayList<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<String> getInstrumentList() {
        return instrumentList;
    }

    public void setInstrumentList(List<String> instrumentList) {
        this.instrumentList = instrumentList;
    }

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
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

    public void setAppliesList(List<InvitationId> applyList) {
        this.applyList = applyList;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public User(String username,  String email,  String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }


    public User( String username) {
        this.username = username;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }

    public User(String id,  String username,  String email, String password, String name, String surname, List<String> instrumentList, String bio, String location, boolean available, List<String> tagList, List<InvitationId> applyList) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.instrumentList = instrumentList;
        this.bio = bio;
        this.location = location;
        this.available = available;
        this.tagList = tagList;
        this.applyList = applyList;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }

    public User( String username,  String email,  String password, String name, String surname, List<String> instrumentList, String bio, String location, boolean available, List<String> tagList) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.instrumentList = instrumentList;
        this.bio = bio;
        this.location = location;
        this.available = available;
        this.tagList = tagList;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }

    public User(String id,  String username,  String email,  String password, String name, String surname, List<String> instrumentList, List<String> tagList, String bio, String location, boolean available, List<InvitationId> invitationList) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.instrumentList = instrumentList;
        this.tagList = tagList;
        this.bio = bio;
        this.location = location;
        this.available = available;
        this.invitationList = invitationList;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }

    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", instrumentList=" + instrumentList +
                ", tagList=" + tagList +
                ", bio='" + bio + '\'' +
                ", location='" + location + '\'' +
                ", available=" + available +
                ", invitationList=" + invitationList +
                ", applyList=" + applyList +
                ", roles=" + roles +
                '}';
    }
}
