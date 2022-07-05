package com.example.jamz.payload.payload.request;

public class InvitationEliminationRequest {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvitationEliminationRequest(String id) {
        this.id = id;
    }

    public InvitationEliminationRequest() {
        super();
    }
}
