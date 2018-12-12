package com.example.polls.service.impl;

import com.example.polls.mapper.UserMapper;
import com.example.polls.model.User;
import com.example.polls.payload.AuthenticationResponse;
import com.example.polls.payload.user.UserBasicDto;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.JwtTokenProvider;
import com.example.polls.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    public AuthenticationResponse authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        Optional<User> user = userRepository.findByUsername(username);

        UserBasicDto userBasicDto = UserMapper.MAPPER.UserToUserBasicDto(user.get());

        return new AuthenticationResponse(userBasicDto, jwt);
    }
}
