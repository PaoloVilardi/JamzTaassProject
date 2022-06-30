package com.example.invitationservice.payload.request;

import com.example.invitationservice.models.UserId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class InvitationCreationRequest {

    @NotBlank
    @Size(min = 5, max = 60)
    private String title;
    @NotBlank
    @Size(min = 10, max = 300)
    private String description;
    @NotBlank
    private String genre;
    @NotBlank
    private String instrument;
    @NotEmpty
    private List<String> tagList;
    @NotBlank
    private String invitationType;
    private String creator;

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

    public InvitationCreationRequest(@NotBlank @Size(min = 5, max = 60) String title, @NotBlank @Size(min = 10, max = 300) String description, @NotBlank String genre, @NotBlank String instrument, @NotEmpty List<String> tagList, @NotBlank String invitationType) {
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

}
