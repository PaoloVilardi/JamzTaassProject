package com.example.jamz.payload.payload.request;

public class InvitationApplyRequest {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvitationApplyRequest(String id) {
        this.id = id;
    }

    public InvitationApplyRequest() {
        super();
    }
}
