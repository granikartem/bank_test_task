package com.example.test_task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * <p>
 * DTO для передачи данных для доабвления/удаления адреса электронной почты.
 * </p>
 */
@Data
@Schema(description = "Запрос на добавление адреса электронной почты")
public class AddOrDeleteEmailRequest {
    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;
}

