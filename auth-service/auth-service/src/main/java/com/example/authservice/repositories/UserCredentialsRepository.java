package com.example.authservice.repositories;

import com.example.authservice.models.UserCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserCredentialsRepository extends MongoRepository<UserCredentials, String> {
    UserCredentials findByUsername(String username);
    Boolean existsByUsername(String username);
    @Override
    List<UserCredentials> findAll();
}
