package com.example.test_task.controller;


import com.example.test_task.dto.CreateUserRequest;
import com.example.test_task.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * REST-контроллер, отвечающий за создание пользователей.
 * </p>
 */
@RestController
@RequestMapping("/createuser")
@RequiredArgsConstructor
@Tag(name = "Создание пользователей")
public class CreateUserController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Создание пользователя")
    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody @Valid CreateUserRequest request) {
        try {
            authenticationService.createUser(request);
        }catch (IllegalArgumentException e){
            String message = e.getMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User created", HttpStatus.OK);
    }
}
