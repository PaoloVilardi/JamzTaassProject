package com.example.invitationservice.controller;

import com.example.invitationservice.config.MQConfig;
import com.example.invitationservice.models.Invitation;
import com.example.invitationservice.models.InvitationId;
import com.example.invitationservice.payload.request.*;
import com.example.invitationservice.payload.response.MessageResponse;
import com.example.invitationservice.rabbitmq.ApplyUpdateMQRequest;
import com.example.invitationservice.rabbitmq.InvitationUpdateMQRequest;
import com.example.invitationservice.repositories.InvitationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/invitation")
public class InvitationController {

    Logger logger = LoggerFactory.getLogger(InvitationController.class);
    private final InvitationRepository invitationRepository;

    public InvitationController(InvitationRepository invitationRepository/*, UserRepository userRepository*/) {
        this.invitationRepository = invitationRepository;
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @GetMapping(value = "/test", produces = APPLICATION_JSON_VALUE)
    public String test(){
        logger.info("TEST ESEGUITO");
        return "This is a test!";
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createInvitation(@RequestBody InvitationCreationRequest invitationCreationRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getPrincipal();
        invitationCreationRequest.setCreator(username);
        Invitation invitation = new Invitation(invitationCreationRequest.getTitle(),
                invitationCreationRequest.getDescription(),
                invitationCreationRequest.getGenre(),
                invitationCreationRequest.getInstrument(),
                invitationCreationRequest.getTagList(),
                invitationCreationRequest.getInvitationType(),
                invitationCreationRequest.getCreator());
        invitationRepository.save(invitation);
        rabbitTemplate.convertAndSend(MQConfig.INV_LIST_UPDATE_EXCHANGE, MQConfig.INV_LIST_UPDATE_ROUTING_KEY, new InvitationUpdateMQRequest("add",invitation.getId(), username));
        return ResponseEntity.ok(new MessageResponse("Invitation for User: "+ username +" created successfully"));
    }

    @PostMapping(value = "/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteInvitation(@RequestBody InvitationEliminationRequest invitationEliminationRequest) throws IllegalAccessException {
        String id = invitationEliminationRequest.getId();
        Optional<Invitation> invitationOptional = invitationRepository.findById(id);
        if(invitationOptional.isPresent()) {
            Invitation invitation = invitationOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String)authentication.getPrincipal();
            if(invitation.getCreator().toLowerCase().equals(username.toLowerCase())) {
                invitationRepository.deleteById(invitation.getId());
                String creator = invitation.getCreator();
                List<String> candidateList = invitation.getCandidateList();
                String invitationId = invitation.getId();
                //update creator data
                rabbitTemplate.convertAndSend(MQConfig.INV_LIST_UPDATE_EXCHANGE, MQConfig.INV_LIST_UPDATE_ROUTING_KEY,
                        new InvitationUpdateMQRequest("delete", invitation.getId(), creator));
                //update candidateList data
                for (String candidate : candidateList) {
                    rabbitTemplate.convertAndSend(MQConfig.APPLY_LIST_UPDATE_EXCHANGE, MQConfig.APPLY_LIST_UPDATE_ROUTING_KEY,
                            new ApplyUpdateMQRequest("delete", invitation.getId(), creator, candidate));
                }
            } else {
                throw new IllegalAccessException("Trying to delete Invitation: " + id + " by a User who is not owner of the object!");
            }

        } else {
            throw new DataRetrievalFailureException("Invitation not found in the database. Id error!!!");
        }
        return ResponseEntity.ok(new MessageResponse("Invitation deleted successfully"));
    }

    @PostMapping(value = "/apply")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> applyForInvitation(@RequestBody InvitationApplyRequest invitationApplyRequest){
        String id = invitationApplyRequest.getId();
        Optional<Invitation> invitationOptional = invitationRepository.findById(id);
        if(invitationOptional.isPresent()){
            Invitation invitation = invitationOptional.get();
            if(invitation.isOpen()) { //controllo se valida(open) e non scaduta
                Query query = Query.query(Criteria.where("_id").is(id));
                Update update = new Update();
                List<String> candidateList = invitation.getCandidateList();
                if (candidateList == null) {
                    candidateList = new ArrayList<String>();
                }
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String candidate = (String) authentication.getPrincipal();
                if(!(candidateList.contains(candidate))) {
                    logger.info("candidate = " + candidate);
                    candidateList.add(candidate);
                    logger.info("candidate list = " + candidateList);
                    update.set("candidateList", candidateList);
                    mongoTemplate.findAndModify(query, update, Invitation.class);
                    rabbitTemplate.convertAndSend(MQConfig.APPLY_LIST_UPDATE_EXCHANGE, MQConfig.APPLY_LIST_UPDATE_ROUTING_KEY,
                            new ApplyUpdateMQRequest("add", invitation.getId(), invitation.getCreator(), candidate));
                } else {
                    logger.error("The user " + candidate + " is already applied to this invitation");
                    throw new DataRetrievalFailureException("User already applied to this invitation!!!");
                }
            } else{
                logger.error("The invitation you applied for is not open");
                throw new DataRetrievalFailureException("The invitation you applied for is not open!!!");
                //case invitation not open
            }
        } else {
            throw new DataRetrievalFailureException("Invitation not found in the database. Id error!!!");
        }
        return ResponseEntity.ok(new MessageResponse("User applied successfully"));
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getInvitations(){
        return invitationRepository.findAll();
    }


    @PostMapping(value = "/modify_acceptance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> modifyAcceptance(@RequestBody InvitationModifyAcceptanceRequest invitationModifyAcceptanceRequest) throws IllegalAccessException{
        String id = invitationModifyAcceptanceRequest.getId();
        String candidate = invitationModifyAcceptanceRequest.getCandidate();
        Optional<Invitation> invitationOptional = invitationRepository.findById(id);
        if(invitationOptional.isPresent()) {
            Invitation invitation = invitationOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String)authentication.getPrincipal();
            if(invitation.getCreator().toLowerCase().equals(username.toLowerCase())) {
                if (invitation.isOpen()) {
                    if(invitation.getAcceptanceStatus() == null || invitation.getAcceptanceStatus().isEmpty()) {
                        //modify acceptance status
                        Query query_acceptance = Query.query(Criteria.where("id").is(id));
                        Update update_acceptance = new Update();
                        update_acceptance.set("acceptanceStatus", candidate);
                        update_acceptance.set("open", false);
                        mongoTemplate.findAndModify(query_acceptance, update_acceptance, Invitation.class);
                    } else {
                        throw new DataRetrievalFailureException("Invitation already already accepted. Useless operation!");
                    }
                } else {
                    throw new DataRetrievalFailureException("Invitation already closed. Useless operation!");
                }
            } else {
                throw new IllegalAccessException("Trying to modify Invitation: " + id + " by a User who is not owner of the object!");
            }

        } else {
            throw new DataRetrievalFailureException("Invitation not found in the database. Id error!!!");
        }
        return ResponseEntity.ok(new MessageResponse("Invitation modified successfully"));
    }

    @PostMapping(value = "/close")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> closeInvitation(@RequestBody InvitationEliminationRequest invitationEliminationRequest) throws IllegalAccessException {
        String id = invitationEliminationRequest.getId();
        Optional<Invitation> invitationOptional = invitationRepository.findById(id);
        if(invitationOptional.isPresent()) {
            Invitation invitation = invitationOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String)authentication.getPrincipal();
            if(invitation.getCreator().toLowerCase().equals(username.toLowerCase())) {
                if (invitation.isOpen()) {
                    Query query_invitation = Query.query(Criteria.where("id").is(id));
                    Update update_invitation = new Update();
                    update_invitation.set("open", false);
                    mongoTemplate.findAndModify(query_invitation, update_invitation, Invitation.class);
                } else {
                    throw new DataRetrievalFailureException("Invitation already closed. Useless operation!");
                }
            } else {
                throw new IllegalAccessException("Trying to close Invitation: " + id + " by a User who is not owner of the object!");
            }
        } else {
            throw new DataRetrievalFailureException("Invitation not found in the database. Id error!!!");
        }
        return ResponseEntity.ok(new MessageResponse("Invitation closed successfully"));
    }

    @PostMapping("/remove_apply")
    public ResponseEntity<MessageResponse> removeApply(@RequestBody RemoveApplyRequest removeApplyRequest) throws IllegalAccessException {
        String id = removeApplyRequest.getId();
        Optional<Invitation> invitationOptional = invitationRepository.findById(id);
        if(invitationOptional.isPresent()) {
            Invitation invitation = invitationOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String)authentication.getPrincipal();
            List<String> candidateList = invitation.getCandidateList();
            if(candidateList.contains(username)) {
                //removing candidate from candidateList of just removed apply
                Query query_apply = Query.query(Criteria.where("id").is(id));
                Update update_apply = new Update();

                candidateList.remove(username);
                update_apply.set("candidateList", candidateList);
                mongoTemplate.findAndModify(query_apply, update_apply, Invitation.class);

                //removing apply from applyList of current user
                rabbitTemplate.convertAndSend(MQConfig.APPLY_LIST_UPDATE_EXCHANGE, MQConfig.APPLY_LIST_UPDATE_ROUTING_KEY,
                        new ApplyUpdateMQRequest("delete", invitation.getId(), invitation.getCreator(), username));
            } else {
                throw new IllegalAccessException("Trying to remove apply for Invitation: " + id + " by a User who is not applied!");
            }

        } else {
            throw new DataRetrievalFailureException("Invitation not found in the database. Id error!!!");
        }
        return ResponseEntity.ok(new MessageResponse("Apply removed successfully"));
    }


    @PostMapping(value = "/is_applied", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isApplied(@RequestBody InvitationApplyRequest invitationApplyRequest){
        Optional<Invitation> invitationOptional = invitationRepository.findById(invitationApplyRequest.getId());
        if(invitationOptional.isPresent()) {
            Invitation invitation = invitationOptional.get();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = (String) authentication.getPrincipal();
            if(invitation.getCandidateList() != null && !invitation.getCandidateList().isEmpty())
                return invitation.getCandidateList().contains(username);
            else {
                return false;
            }
        } else {
            throw new DataRetrievalFailureException("Invitation not found in the database. Id error!!!");
        }
    }


    @PostMapping(value = "/filtered_search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getFilteredInvitations(@RequestBody InvitationFilterRequest invitationFilterRequest){
        String invitationType=invitationFilterRequest.getInvitationType();
        String genre = invitationFilterRequest.getGenre();
        String instrument = invitationFilterRequest.getInstrument();
        List<String> tagList = invitationFilterRequest.getTag_list();
        boolean open = invitationFilterRequest.isOpen();
        Criteria search_criteria = new Criteria();
        if(invitationType != null && !invitationType.isEmpty())
            search_criteria = search_criteria.and("invitationType").is(invitationType);
        if(genre != null && !genre.isEmpty())
            search_criteria = search_criteria.and("genre").is(genre);
        if(instrument != null && !instrument.isEmpty())
            search_criteria = search_criteria.and("instrument").is(instrument);
        if(tagList != null && !tagList.isEmpty())
            search_criteria = search_criteria.and("tagList").is(tagList);
        search_criteria = search_criteria.and("open").is(open);
        Query query = new Query(search_criteria);
        return mongoTemplate.find(query, Invitation.class);
    }

    @PostMapping(value = "/list_by_ids", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getInvitationFromId(@RequestBody InvitationFromIdsRequest invitationFromIdsRequest){
        List<InvitationId> invIdList = invitationFromIdsRequest.getInvIdList();
            List<Invitation> invList = new ArrayList<>();
        for(InvitationId invId: invIdList){
            Optional<Invitation> optInv = invitationRepository.findById(invId.getId());
            if(optInv.isPresent()){
                invList.add(optInv.get());
                logger.info("Invitation = " + optInv.get());
            }
        }
        return invList;
    }
}
