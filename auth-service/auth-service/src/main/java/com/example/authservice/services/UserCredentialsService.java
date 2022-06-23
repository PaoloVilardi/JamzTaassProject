package com.example.authservice.services;

import com.example.authservice.models.UserCredentials;

import java.util.List;

public interface UserCredentialsService {
    UserCredentials saveUserCredentials(UserCredentials userCredentials);
    UserCredentials getUserCredentials(String username);
    List<UserCredentials> getUsersCredentials();
    Boolean existsByUsername(String username);
}
