package com.example.test_task.controller;

import com.example.test_task.dto.AddOrDeleteEmailRequest;
import com.example.test_task.dto.UpdateEmailRequest;
import com.example.test_task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * REST-контроллер, отвечающий за манипуляции с адресами электронной почты пользователя.
 * </p>
 * <p>
 * Позволяет добавлять, удалять и изменять существующие адреса электронной почты аутентифицированного пользователя.
 * </p>
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Tag(name = "Изменение/добавление электронной почты")
public class EmailController {

    private final UserService userService;

    @Operation(summary = "Добавление почты")
    @PostMapping("/add")
    public ResponseEntity<String> addEmail(@RequestBody @Valid AddOrDeleteEmailRequest request) {
        try {
            userService.addEmail(request.getEmail());
            return new ResponseEntity<>("Электронная почта добавлена", HttpStatus.OK);
        } catch (DataAccessException p) {
            return new ResponseEntity<>("Электронная почта уже занята", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Удаление почты")
    @PostMapping("/delete")
    public ResponseEntity<String> deleteEmail(@RequestBody @Valid AddOrDeleteEmailRequest request) {
        try {
            userService.deleteEmail(request.getEmail());
            return new ResponseEntity<>("Электронная почта удалена", HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Обновление адреса почты")
    @PostMapping("/update")
    public ResponseEntity<String> updateEmail(@RequestBody @Valid UpdateEmailRequest request) {
        try{
            userService.updateEmail(request.getOldEmail(), request.getNewEmail());
            return new ResponseEntity<>("Электронная почта обновлена", HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            return new ResponseEntity<>("Электронная почта уже занята", HttpStatus.BAD_REQUEST);
        }

    }
}
