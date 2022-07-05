package com.example.jamz.payload.payload.request;

import java.util.HashSet;
import java.util.Set;

public class SignupRequest {


    private String username;

    private String email;


    private String password;


    private String name;

    private String surname;


    private String instrument;

    private Set<String> roles;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRole(Set<String> roles) {
        this.roles = roles;
    }

    public SignupRequest(String username, String email, String password, String name,  String surname, String instrument, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.instrument = instrument;
        this.roles = roles;
    }

    public SignupRequest(String username,String email, String password, String name,  String surname, String instrument) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.instrument = instrument;
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        this.roles = roles;
    }

    public SignupRequest(){
        super();
    }


    @Override
    public String toString() {
        return "SignupRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", instrument=" + instrument +
                ", roles=" + roles +
                '}';
    }

    //TODO aggiungere altri parametri richiesti in registrazione qui(???)
}
