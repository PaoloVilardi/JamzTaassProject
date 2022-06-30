package com.example.invitationservice.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Document(collection = "invitation")
public class Invitation {
    //TODO validare i campi??

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotBlank
    @Size(min = 5, max = 60)
    private String title;
    @NotBlank
    @Size(min = 10, max=300)
    private String description;

    private String genre;
    private String instrument;
    private List<String> tagList;
    private String invitationType;
    private String localDateTime;

    private boolean open;
    private String acceptanceStatus;

    public String getAcceptanceStatus() {
        return acceptanceStatus;
    }

    public void setAcceptanceStatus(String acceptanceStatus) {
        this.acceptanceStatus = acceptanceStatus;
    }

    private String creator;

    private List<String> candidateList;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getInvitationType() {
        return invitationType;
    }

    public void setInvitationType(String invitationType) {
        this.invitationType = invitationType;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<String> candidateList) {
        this.candidateList = candidateList;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }


    public Invitation(@NotBlank @Size(min = 5, max = 60) String title, @NotBlank @Size(min = 10, max = 300) String description) {
        this.title = title;
        this.description = description;
    }

    public Invitation(@NotBlank @Size(min = 5, max = 60) String title, @NotBlank @Size(min = 10, max = 300) String description, String genre, String instrument, List<String> tagList, String invitationType, String creator) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.instrument = instrument;
        this.tagList = tagList;
        this.invitationType = invitationType;
        this.creator = creator;
        this.open = true;
        this.acceptanceStatus = "";
        this.localDateTime = LocalDateTime.now().toString();
        this.candidateList = null;
    }

    public Invitation(@NotBlank @Size(min = 5, max = 60) String title, @NotBlank @Size(min = 10, max = 300) String description, String genre, String instrument, List<String> tagList, String invitationType, String localDateTime, boolean open, String creator, List<String> candidateList) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.instrument = instrument;
        this.tagList = tagList;
        this.invitationType = invitationType;
        this.localDateTime = localDateTime;
        this.open = open;
        this.acceptanceStatus = "";
        this.creator = creator;
        this.candidateList = candidateList;
    }

    public Invitation(){
        super();
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", instrument='" + instrument + '\'' +
                ", tagList=" + tagList +
                ", invitationType='" + invitationType + '\'' +
                ", localDateTime=" + localDateTime +
                ", open=" + open +
                ", creator=" + creator +
                ", candidateList=" + candidateList +
                '}';
    }
}
