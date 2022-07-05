package com.example.jamz.payload.payload.request;

import com.example.jamz.model.InvitationId;

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
