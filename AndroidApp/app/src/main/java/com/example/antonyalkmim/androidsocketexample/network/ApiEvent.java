package com.example.antonyalkmim.androidsocketexample.network;

public enum ApiEvent {

    newUser("new user request"),
    newUserAdded("new user response");


    private String eventId;
    ApiEvent(String id) {
        this.eventId = id;
    }

    public String getEventId() {
        return eventId;
    }
}
