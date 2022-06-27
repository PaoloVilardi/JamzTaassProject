package com.example.userservice.services;

import com.example.userservice.models.InvitationId;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    MongoTemplate mongoTemplate;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        logger.info("Saving user {} in the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // in a real world app you should return pages with Users loaded, not List<User>
    @Override
    public User getUser(String username) {
        logger.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean updateInvitationList(InvitationId invitationId) {
        User creator = this.getUser(invitationId.getCreator());
        Query query = Query.query(Criteria.where("username").is(creator.getUsername()));
        Update update = new Update();
        List<InvitationId> invitationList = creator.getInvitationList();
        if(invitationList == null || invitationList.isEmpty()){
            invitationList = new ArrayList<InvitationId>();
        }
        invitationList.add(invitationId);
        update.set("invitationList", invitationList);
        if(mongoTemplate.findAndModify(query, update, User.class) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteInvitationFromInvitationList(InvitationId invitationId) {
        User creator = this.getUser(invitationId.getCreator());
        Query query = Query.query(Criteria.where("username").is(creator.getUsername()));
        Update update = new Update();
        List<InvitationId> invitationList = creator.getInvitationList();
        if(invitationList == null || invitationList.isEmpty()){
            invitationList = new ArrayList<InvitationId>();
        }
        invitationList = removeInvitationById(invitationList, invitationId.getId());
        update.set("invitationList", invitationList);
        if(mongoTemplate.findAndModify(query, update, User.class) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteApplyFromApplyList(String applyId, String candidate) {
        User candidateUser = this.getUser(candidate);
        Query query = Query.query(Criteria.where("username").is(candidateUser.getUsername()));
        Update update = new Update();
        List<InvitationId> applyList = candidateUser.getApplyList();
        if(applyList == null || applyList.isEmpty()){
            return false;
        }
        applyList = removeInvitationById(applyList, applyId);
        update.set("applyList", applyList);
        if(mongoTemplate.findAndModify(query, update, User.class) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addApplyToApplyList(String applyId, String creator,String candidate) {
        User candidateUser = this.getUser(candidate);
        Query query = Query.query(Criteria.where("username").is(candidateUser.getUsername()));
        Update update = new Update();
        List<InvitationId> applyList = candidateUser.getApplyList();
        if(applyList == null || applyList.isEmpty()){
            applyList = new ArrayList<InvitationId>();
        }
        applyList.add(new InvitationId(applyId, creator));
        update.set("applyList", applyList);
        if(mongoTemplate.findAndModify(query, update, User.class) != null){
            return true;
        } else {
            return false;
        }
    }

    public static List<InvitationId> removeInvitationById(List<InvitationId> invitationList, String id){
        invitationList.removeIf(invitation -> invitation.getId().equals(id));
        return invitationList;
    }
}
