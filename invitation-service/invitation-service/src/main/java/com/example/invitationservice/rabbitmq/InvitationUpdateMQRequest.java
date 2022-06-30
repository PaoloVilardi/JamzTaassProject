package com.example.invitationservice.rabbitmq;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class InvitationUpdateMQRequest implements Serializable {

    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonProperty("creator")
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InvitationUpdateMQRequest(String type, String id, String creator) {
        this.type = type;
        this.id = id;
        this.creator = creator;
    }

    public InvitationUpdateMQRequest() {
        super();
    }
}