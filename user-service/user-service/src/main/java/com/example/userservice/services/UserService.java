package com.example.userservice.services;

import com.example.userservice.models.InvitationId;
import com.example.userservice.models.User;
import com.example.userservice.rabbitmq.InvitationUpdateMQRequest;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    //TODO in Real World App it should return pages with users, not UserList
    User getUser(String username);
    List<User> getUsers();
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    boolean updateInvitationList(InvitationId invitationId);
    boolean deleteInvitationFromInvitationList(InvitationId invitationId);
    boolean deleteApplyFromApplyList(String applyId, String candidate);
    boolean addApplyToApplyList(String applyId, String creator, String candidate);
}
