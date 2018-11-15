package com.example.polls.payload;

import com.example.polls.payload.user.UserBasicDto;

public class AuthenticationResponse {
    private String token;

    private UserBasicDto user;

    public AuthenticationResponse(UserBasicDto user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBasicDto getUser() {
        return user;
    }

    public void setUser(UserBasicDto user) {
        this.user = user;
    }
}
