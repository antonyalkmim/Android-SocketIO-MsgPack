package com.example.antonyalkmim.androidsocketexample.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("username") public String username;
    @JsonProperty("email") public String email;

    public User() { }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
