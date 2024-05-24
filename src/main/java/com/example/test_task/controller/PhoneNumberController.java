package com.example.test_task.controller;

import com.example.test_task.dto.UpdatePhoneNumberRequest;
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

import com.example.test_task.dto.AddOrDeletePhoneNumberRequest;

/**
 * <p>
 * REST-контроллер, отвечающий за манипуляции с номерами телефона пользователя.
 * </p>
 * <p>
 * Позволяет добавлять, удалять и изменять существующие номера телефона аутентифицированного пользователя.
 * </p>
 */
@RestController
@RequestMapping("/phone-number")
@RequiredArgsConstructor
@Tag(name = "Изменение/добавление номера телефона")
public class PhoneNumberController {

    private final UserService userService;

    @Operation(summary = "Добавление номера телефона")
    @PostMapping("/add")
    public ResponseEntity<String> addPhoneNumber(@RequestBody @Valid AddOrDeletePhoneNumberRequest request) {
        try {
            userService.addPhoneNumber(request.getPhoneNumber());
            return new ResponseEntity<>("Номер телефона добавлен", HttpStatus.OK);
        } catch (DataAccessException p) {
            return new ResponseEntity<>("Номер телефона уже занят", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Удаление номера телефона")
    @PostMapping("/delete")
    public ResponseEntity<String> deletePhoneNumber(@RequestBody @Valid AddOrDeletePhoneNumberRequest request) {
        try {
            userService.deletePhoneNumber(request.getPhoneNumber());
            return new ResponseEntity<>("Номер телефона удален", HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Обновление номера телефона")
    @PostMapping("/update")
    public ResponseEntity<String> updatePhoneNumber(@RequestBody @Valid UpdatePhoneNumberRequest request) {
        try {
            userService.updatePhoneNumber(request.getOldPhoneNumber(), request.getNewPhoneNumber());
            return new ResponseEntity<>("Phone number updated", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DataAccessException p) {
            return new ResponseEntity<>("Номер телефона уже занят", HttpStatus.BAD_REQUEST);
        }
    }
}
