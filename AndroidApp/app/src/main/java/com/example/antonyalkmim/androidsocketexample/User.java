package com.example.antonyalkmim.androidsocketexample;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("username") public String username;
    @JsonProperty("email") public String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(){}

}
