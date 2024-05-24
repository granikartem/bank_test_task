package com.example.test_task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * <p>
 * DTO для передачи данных для изменения номера телефона.
 * </p>
 */
@Data
@Schema(description = "Запрос на изменение телефона")
public class UpdatePhoneNumberRequest {
    @Schema(description = "Номер телефона", example = "+79117777777")
    @NotBlank(message = "Номер телефонаы не может быть пустым")
    private String oldPhoneNumber;

    @Schema(description = "Номер телефона", example = "+79117777777")
    @NotBlank(message = "Номер телефонаы не может быть пустым")
    private String newPhoneNumber;
}
