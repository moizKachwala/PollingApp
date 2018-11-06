package com.example.polls.payload;

import com.example.polls.model.User;

/**
 * Created by rajeevkumarsingh on 19/08/17.
 */
public class AuthenticationResponse {
    private String token;

    private UserBasic user;

    public AuthenticationResponse(UserBasic user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBasic getUser() {
        return user;
    }

    public void setUser(UserBasic user) {
        this.user = user;
    }
}
