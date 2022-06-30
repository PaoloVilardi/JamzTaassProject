package com.example.invitationservice.models;


public class InvitationId {

    private static final String className = "invitation";
    private String id;
    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvitationId(String id, String creator) {
        this.id = id;
        this.creator = creator;
    }
    public InvitationId(){
        super();
    }
}
