package com.example.authservice.controller;

import com.example.authservice.config.MQConfig;
import com.example.authservice.models.UserCredentials;
import com.example.authservice.payload.request.SignupRequest;
import com.example.authservice.payload.response.MessageResponse;
import com.example.authservice.services.UserCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserCredentialsService userCredentialsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public AuthController(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    @PostMapping("/login")
    public String login(){
        return "This is a login";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signupRequest) throws IOException {
        logger.info(signupRequest.toString());
        if (userCredentialsService.existsByUsername(signupRequest.getUsername())) {
            throw new DataRetrievalFailureException("Error! Username already exists in the DB");
        }
        // Create new user's account
        UserCredentials userCredentials = new UserCredentials(signupRequest.getUsername(), signupRequest.getPassword());
        UserCredentials newUserCredentials = userCredentialsService.saveUserCredentials(userCredentials);
        rabbitTemplate.convertAndSend(MQConfig.AUTH_EXCHANGE, MQConfig.AUTH_ROUTING_KEY, signupRequest);
        return ResponseEntity.ok(new MessageResponse("User: " + userCredentials + " registered successfully!"));
    }

}
