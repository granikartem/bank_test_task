package com.example.test_task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * <p>
 * DTO для передачи данных для проведения денежного перевода.
 * </p>
 */
@Data
@Schema(description = "Запрос на проведение транзакции")
public class TransactionRequest {
    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Сумма для перевода", example = "100.1")
    @NotBlank(message = "Нужно переводить сумму больше нуля")
    @Positive
    private double sum;
}
