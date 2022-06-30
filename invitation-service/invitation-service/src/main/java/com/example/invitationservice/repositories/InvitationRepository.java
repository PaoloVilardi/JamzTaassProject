package com.example.invitationservice.repositories;

import com.example.invitationservice.models.Invitation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends MongoRepository<Invitation, String> {
    Optional<Invitation> findById(String id);
    List<Invitation> findByGenre(String genre);
    List<Invitation> findByInvitationType(String invitationType);
    List<Invitation> findByInvitationTypeAndGenreAndInstrument(String invitationType, String genre, String instrument);
    List<Invitation> findByInvitationTypeAndGenre(String invitationType, String genre);
}
