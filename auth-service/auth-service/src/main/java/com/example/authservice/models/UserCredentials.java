package com.example.authservice.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotBlank
    @Size(min=3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private List<String> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserCredentials(String id, @NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(min = 6, max = 40) String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }

    public UserCredentials(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(min = 6, max = 40) String password) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }

    public UserCredentials() {
        super();
    }
}
