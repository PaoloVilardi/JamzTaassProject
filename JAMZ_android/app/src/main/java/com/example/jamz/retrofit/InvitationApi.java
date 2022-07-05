package com.example.jamz.retrofit;

import com.example.jamz.model.Invitation;
import com.example.jamz.payload.payload.request.InvitationApplyRequest;
import com.example.jamz.payload.payload.request.InvitationCreationRequest;
import com.example.jamz.payload.payload.request.InvitationEliminationRequest;
import com.example.jamz.payload.payload.request.InvitationFilterRequest;
import com.example.jamz.payload.payload.request.InvitationFromIdsRequest;
import com.example.jamz.payload.payload.request.InvitationModifyAcceptanceRequest;
import com.example.jamz.payload.payload.request.RemoveApplyRequest;
import com.example.jamz.payload.payload.response.MessageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InvitationApi {
    static final String BASE_URL = "/api/v1/invitation/";


    @GET(BASE_URL + "list")
    Call<List<Invitation>> getInvitations();

    @GET(BASE_URL + "test")
    Call<String> test();

    @POST(BASE_URL + "create")
    Call<MessageResponse> createInvitation(@Header("Authorization") String authorization, @Body InvitationCreationRequest invitationCreationRequest);

    @POST(BASE_URL + "modify_acceptance")
    Call<MessageResponse> modifyAcceptance(@Header("Authorization") String authorization, @Body InvitationModifyAcceptanceRequest invitationModifyAcceptanceRequest);

    @POST(BASE_URL + "delete")
    Call<MessageResponse> deleteInvitation(@Header("Authorization") String authorization, @Body InvitationEliminationRequest invitationEliminationRequest);

    @POST(BASE_URL + "apply")
    Call<MessageResponse> applyForInvitation(@Header("Authorization") String authorization, @Body InvitationApplyRequest invitationApplyRequest);

    @POST(BASE_URL + "close")
    Call<MessageResponse> closeInvitation(@Header("Authorization") String authorization, @Body InvitationEliminationRequest invitationEliminationRequest);

    @POST(BASE_URL + "remove_apply")
    Call<MessageResponse> removeApply(@Header("Authorization") String authorization, @Body RemoveApplyRequest removeApplyRequest);

    @POST(BASE_URL + "is_applied")
    Call<Boolean> isApplied(@Header("Authorization") String authorization, @Body InvitationApplyRequest invitationApplyRequest);

    @POST(BASE_URL + "filtered_search")
    Call<List<Invitation>> getFilteredInvitations(@Body InvitationFilterRequest invitationFilterRequest);

    @POST(BASE_URL + "list_by_ids")
    Call<List<Invitation>> getInvitationFromId(@Header("Authorization") String authorization, @Body InvitationFromIdsRequest invitationFromIdsRequest);

}
