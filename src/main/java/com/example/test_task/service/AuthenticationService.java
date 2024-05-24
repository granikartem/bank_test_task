package com.example.test_task.service;

import com.example.test_task.dto.JwtAuthenticationResponse;
import com.example.test_task.dto.SignInRequest;
import com.example.test_task.dto.CreateUserRequest;
import com.example.test_task.entity.Email;
import com.example.test_task.entity.PhoneNumber;
import com.example.test_task.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервис для аутентификации пользователей.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse createUser(CreateUserRequest request) throws IllegalArgumentException{
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nameFIO(request.getNameFIO())
                .birthDate(request.getDateOfBirth())
                .account(request.getInitialAccount())
                .account_limit(request.getInitialAccount() * 2.07)
                .build();

        PhoneNumber phoneNumber = PhoneNumber.builder()
                .phoneNumber(request.getPhoneNumber())
                .user(user)
                .build();
        Email email = Email.builder()
                .email(request.getEmail())
                .user(user)
                .build();

        userService.create(user, phoneNumber, email);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
