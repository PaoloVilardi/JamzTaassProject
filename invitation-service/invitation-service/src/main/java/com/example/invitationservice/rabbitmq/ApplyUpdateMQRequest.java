package com.example.invitationservice.rabbitmq;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApplyUpdateMQRequest implements Serializable {

    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonProperty("creator")
    private String creator;
    @JsonProperty("candidate")
    private String candidate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ApplyUpdateMQRequest(String type, String id, String creator, String candidate) {
        this.type = type;
        this.id = id;
        this.creator = creator;
        this.candidate = candidate;
    }

    public ApplyUpdateMQRequest() {
        super();
    }
}
