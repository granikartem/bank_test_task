package com.example.test_task.controller;

import com.example.test_task.dto.SignInRequest;
import com.example.test_task.dto.TransactionRequest;
import com.example.test_task.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * REST-контроллер, отвечающий за проведение переводов со счета аутентифицированного пользователя.
 * </p>
 */
@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
@Tag(name = "Перевод денег")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Денежный перевод")
    @PostMapping
    public ResponseEntity<String> transferMoney(@RequestBody @Valid TransactionRequest request) {
        try{
            transactionService.transferMoney(request.getUsername(), request.getSum());
            return new ResponseEntity<>("Деньги успешно переведены",HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(UsernameNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
