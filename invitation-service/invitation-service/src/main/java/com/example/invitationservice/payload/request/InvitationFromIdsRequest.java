package com.example.invitationservice.payload.request;

import com.example.invitationservice.models.InvitationId;

import java.util.List;

public class InvitationFromIdsRequest {
    private List<InvitationId> invIdList;

    public List<InvitationId> getInvIdList() {
        return invIdList;
    }

    public void setInvIdList(List<InvitationId> invIdList) {
        this.invIdList = invIdList;
    }

    public InvitationFromIdsRequest(List<InvitationId> invIdList) {
        this.invIdList = invIdList;
    }

    public InvitationFromIdsRequest() {
        super();
    }
}
