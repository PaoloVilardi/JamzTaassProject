package com.example.userservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice.models.InvitationId;
import com.example.userservice.models.User;
import com.example.userservice.payload.request.UserByUsernameInfoRequest;
import com.example.userservice.payload.request.UserUpdateRequest;
import com.example.userservice.payload.response.MessageResponse;
import com.example.userservice.payload.response.UserProfileInfoResponse;
import com.example.userservice.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(){
        return "This is a test!";
    }


    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(){
        logger.info(userService.getUsers().toString());
        return userService.getUsers();
    }


    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }



    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //same as sign on the authentication
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles())//.stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String > tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception){
                logger.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String > error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> updateInfo(@RequestBody UserUpdateRequest userUpdateRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getPrincipal();
        String bio = userUpdateRequest.getBio();
        String location = userUpdateRequest.getLocation();
        List<String> instrumentList = userUpdateRequest.getInstrumentList();
        List<String> tagList = userUpdateRequest.getTagList();
        Boolean available = userUpdateRequest.getAvailable();
        Query query = Query.query(Criteria.where("username").is(username));
        Update update = new Update();
        if(bio != null && bio.length() > 0){
            update.set("bio", bio);
        }
        if(location != null && location.length() > 0){
            update.set("location", location);
        }
        if(instrumentList != null && !(instrumentList.isEmpty())){
            update.set("instrumentList", instrumentList);
        } else {
            update.set("instrumentList", new ArrayList<String>());
        }
        if(tagList != null && !(tagList.isEmpty())){
            update.set("tagList", tagList);
        } else {
            update.set("tagList", new ArrayList<String>());
        }
        if(available != null){
            update.set("available", available);
        }
        User user = mongoTemplate.findAndModify(query, update, User.class);
        return ResponseEntity.ok(new MessageResponse("Info for user "+username+" updated correctly"));
    }

    @PostMapping(value = "/get_profile", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileInfoResponse> userInfo(@RequestBody UserByUsernameInfoRequest userByUsernameInfoRequest){
        logger.info(userByUsernameInfoRequest.getUsername());
        User user = userService.getUser(userByUsernameInfoRequest.getUsername());
        logger.info(user.toString());
        String id = user.getId();
        String username = user.getUsername();
        String name = user.getName();
        String surname = user.getSurname();
        String location = user.getLocation();
        List<String> instrumentList = user.getInstrumentList();
        List<String> tagList = user.getTagList();
        String bio = user.getBio();
        Boolean available = user.isAvailable();


        return ResponseEntity.ok().body(new UserProfileInfoResponse
                (id, username, name, surname, location, instrumentList, tagList, bio, available,
                        null, null));
    }

    @GetMapping(value = "/my_profile", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfileInfoResponse> userInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser((String)authentication.getPrincipal());
        String id = user.getId();
        String username = user.getUsername();
        String name = user.getName();
        String surname = user.getSurname();
        String location = user.getLocation();
        List<String> instrumentList = user.getInstrumentList();
        List<String> tagList = user.getTagList();
        String bio = user.getBio();
        Boolean available = user.isAvailable();

        List<InvitationId> invitationIdList = user.getInvitationList();
        List<InvitationId> applyIdList = user.getApplyList();


        return ResponseEntity.ok().body(new UserProfileInfoResponse
                (id, username, name, surname, location, instrumentList, tagList, bio, available,
                        invitationIdList, applyIdList));
    }

}
