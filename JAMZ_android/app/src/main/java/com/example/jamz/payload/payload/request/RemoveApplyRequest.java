package com.example.jamz.payload.payload.request;

public class RemoveApplyRequest {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RemoveApplyRequest(String id) {
        this.id = id;
    }

    public RemoveApplyRequest() {
        super();
    }
}

