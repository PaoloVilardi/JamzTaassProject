package com.example.invitationservice.payload.request;

public class InvitationModifyAcceptanceRequest {
    private String id;
    private String candidate;

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvitationModifyAcceptanceRequest(String id, String candidate) {
        this.id = id;
        this.candidate = candidate;
    }

    public InvitationModifyAcceptanceRequest() {
        super();
    }
}
