package com.example.test_task.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity-класс, необходимый для представления адресов электронной почты пользователей в БД
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_to_user")
public class Email {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "email_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne
    private User user;
}
