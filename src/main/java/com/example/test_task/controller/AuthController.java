package com.example.test_task.controller;

import com.example.test_task.dto.JwtAuthenticationResponse;
import com.example.test_task.dto.SignInRequest;
import com.example.test_task.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * REST-контроллер, отвечающий за аутентификацию пользователя.
 * </p>
 * <p>
 * Де-факто необходим для передачи JWT-токена пользователю для дальнейшего доступа к url-адресам, требующим аутентификации.
 * </p>
 */
@RestController
@RequestMapping("/sign-in")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Авторизация пользователя")
    @PostMapping
    public ResponseEntity<String> signIn(@RequestBody @Valid SignInRequest request) {

        try{
            String token = authenticationService.signIn(request).getToken();
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (AuthenticationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
