package com.example.polls.service;

import com.example.polls.payload.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse authenticate(String username, String password);
}
