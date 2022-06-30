package com.example.invitationservice.payload.request;

import java.util.List;

public class InvitationFilterRequest {

    public String getInvitationType() {
        return invitationType;
    }

    public void setInvitationType(String invitationType) {
        this.invitationType = invitationType;
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

    public List<String> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<String> tag_list) {
        this.tag_list = tag_list;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }


    private String invitationType;
    private String genre;
    private String instrument;
    private List<String> tag_list;
    private boolean open;
    //TODO inserire filtro per keyword(?) e per location


    public InvitationFilterRequest(String invitationType, String genre, String instrument, List<String> tag_list, boolean open) {
        this.invitationType = invitationType;
        this.genre = genre;
        this.instrument = instrument;
        this.tag_list = tag_list;
        this.open = open;
    }

    public InvitationFilterRequest() {
        super();
    }

    public InvitationFilterRequest(String invitationType, String genre, String instrument, List<String> tag_list) {
        this.invitationType = invitationType;
        this.genre = genre;
        this.instrument = instrument;
        this.tag_list = tag_list;
        this.open = true;
    }
}

