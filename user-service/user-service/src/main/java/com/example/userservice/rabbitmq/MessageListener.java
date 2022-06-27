package com.example.userservice.rabbitmq;


import com.example.userservice.config.MQConfig;
import com.example.userservice.models.InvitationId;
import com.example.userservice.models.User;
import com.example.userservice.payload.request.SignupRequest;
import com.example.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Service
public class MessageListener {

    Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private final UserService userService;
    public MessageListener(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = MQConfig.AUTH_QUEUE)
    public void receiveMessage(@RequestBody SignupRequest signupRequest){
        if(signupRequest != null) {
            if (userService.existsByUsername(signupRequest.getUsername())) {
                //TODO RISOLVERE IL RITORNO NEL CASO IN CUI L'USER ESISTA GIA'
                throw new DataRetrievalFailureException("Error! Username already exists in the DB");
            }
            if (userService.existsByEmail(signupRequest.getEmail())) {
                //TODO RISOLVERE QUI, VA IN LOOP
                throw new DataRetrievalFailureException("Error! Email already exists in the DB");
            }
            // Create new user's account
            List<String> instrumentList = new ArrayList<>();
            instrumentList.add(signupRequest.getInstrument());
            User user = new User(
                    signupRequest.getUsername(),
                    signupRequest.getEmail(),
                    signupRequest.getPassword(),
                    signupRequest.getName(),
                    signupRequest.getSurname(),
                    instrumentList,
                    null,
                    null, //inserire anche location, eventualmente cambiare la signup request
                    true,
                    null
            );
            userService.saveUser(user);
            logger.info("User: " + user + " registered successfully!");
        }
    }

    @RabbitListener(queues = MQConfig.INV_LIST_UPDATE_QUEUE)
    public void receiveMessage(@RequestBody InvitationUpdateMQRequest invitationUpdateMQRequest){
        if(invitationUpdateMQRequest != null) {
            boolean result;
            if(invitationUpdateMQRequest.getType().toLowerCase().equals("add")) {
                InvitationId invitationId = new InvitationId(invitationUpdateMQRequest.getId(), invitationUpdateMQRequest.getCreator());
                result = userService.updateInvitationList(invitationId);
                if(result) {
                    logger.info("Invitation: " + invitationId.getId() + " added successfully to the invitation list of User: " + invitationId.getCreator());
                } else {
                    logger.error("Something went wrong while trying to add invitation: " + invitationId.getId() + " to the invitation list of User: " + invitationId.getCreator());
                }
            } else if(invitationUpdateMQRequest.getType().toLowerCase().equals("delete")){
                InvitationId invitationId = new InvitationId(invitationUpdateMQRequest.getId(), invitationUpdateMQRequest.getCreator());
                result = userService.deleteInvitationFromInvitationList(invitationId);
                if(result){
                    logger.info("Invitation: " + invitationId.getId() + " deleted successfully from the invitation list of User: " + invitationId.getCreator());
                } else {
                    logger.error("Something went wrong while trying to delete invitation: " + invitationId.getId() + " from the invitation list of User: " + invitationId.getCreator());
                }
            }
        }
    }

    @RabbitListener(queues = MQConfig.APPLY_LIST_UPDATE_QUEUE)
    public void receiveMessage(@RequestBody ApplyUpdateMQRequest applyUpdateMQRequest){
        if(applyUpdateMQRequest != null){
            boolean result;
            if(applyUpdateMQRequest.getType().toLowerCase().equals("add")){
                result = userService.addApplyToApplyList(applyUpdateMQRequest.getId(), applyUpdateMQRequest.getCreator(),applyUpdateMQRequest.getCandidate());
                if(result){
                    logger.info("Apply: " + applyUpdateMQRequest.getId() + " added successfully to the apply list of User: " + applyUpdateMQRequest.getCandidate());
                } else {
                    logger.error("Something went wrong while trying to add apply: " + applyUpdateMQRequest.getId() + " to the apply list of User: " + applyUpdateMQRequest.getCandidate());
                }
            } else if(applyUpdateMQRequest.getType().toLowerCase().equals("delete")) {
                result = userService.deleteApplyFromApplyList(applyUpdateMQRequest.getId(), applyUpdateMQRequest.getCandidate());
                if(result) {
                    logger.info("Apply: " + applyUpdateMQRequest.getId() + " deleted successfully from the apply list of User: " + applyUpdateMQRequest.getCandidate());
                } else {
                    logger.error("Something went wrong while trying to delete apply: " + applyUpdateMQRequest.getId() + " from the apply list of User: " + applyUpdateMQRequest.getCandidate());
                }
            }
        }
    }
}
