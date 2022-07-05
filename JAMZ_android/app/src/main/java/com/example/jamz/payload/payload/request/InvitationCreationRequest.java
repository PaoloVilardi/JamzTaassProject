package com.example.jamz.payload.payload.request;

import java.util.List;

public class InvitationCreationRequest {
    //TODO validare i campi??


    private String title;

    private String description;

    private String genre;

    private String instrument;

    private List<String> tagList;

    private String invitationType;
    private String creator;

    //TODO inserire User

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getInvitationType() {
        return invitationType;
    }

    public void setInvitationType(String invitationType) {
        this.invitationType = invitationType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public InvitationCreationRequest(String title, String description, String genre, String instrument, List<String> tagList, String invitationType, String creator) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.instrument = instrument;
        this.tagList = tagList;
        this.invitationType = invitationType;
        this.creator = creator;

    }

    public InvitationCreationRequest( String title,  String description,  String genre,  String instrument,  List<String> tagList,  String invitationType) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.instrument = instrument;
        this.tagList = tagList;
        this.invitationType = invitationType;
    }

    public InvitationCreationRequest() {
        super();
    }

    //...
}
