package com.example.authservice.services;

import com.example.authservice.models.UserCredentials;
import com.example.authservice.repositories.UserCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserCredentialsServiceImpl implements UserCredentialsService{

    Logger logger = LoggerFactory.getLogger(UserCredentialsServiceImpl.class);
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCredentialsServiceImpl(UserCredentialsRepository userCredentialsRepository, PasswordEncoder passwordEncoder) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserCredentials saveUserCredentials(UserCredentials userCredentials) {
        logger.info("Saving user {} in the database", userCredentials.getUsername());
        userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        return userCredentialsRepository.save(userCredentials);
    }

    @Override
    public UserCredentials getUserCredentials(String username) {
        logger.info("Fetching user {}", username);
        return userCredentialsRepository.findByUsername(username);
    }

    @Override
    public List<UserCredentials> getUsersCredentials() {
        logger.info("Fetching all users");
        return userCredentialsRepository.findAll();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userCredentialsRepository.existsByUsername(username);
    }
}
