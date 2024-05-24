package com.example.test_task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.sql.Date;
import lombok.Data;


/**
 * <p>
 * DTO для передачи данных при создании пользователя.
 * </p>
 */
@Data
@Schema(description = "Запрос на регистрацию")
public class CreateUserRequest {

    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;

    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Номер телефона", example = "+79117777777")
    @NotBlank(message = "Нужно указать номер телефона")
    private String phoneNumber;

    @Schema(description = "ФИО", example = "Иванов Иван Иванович")
    @Size(min = 5, max = 255, message = "ФИО должно содержать от 5 до 255 символов")
    @NotBlank(message = "Должно быть указано ФИО")
    private String nameFIO;

    @Schema(description = "Дата рождения", example = "Иванов Иван Иванович")
    @NotBlank(message = "Должна быть указана дата рождения")
    private Date dateOfBirth;

    @Schema(description = "Начальный баланс", example = "100.1")
    @NotBlank(message = "На счете должны быть средства")
    @Positive
    private double initialAccount;
}
