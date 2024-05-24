package com.example.test_task.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity-класс, необходимый для представления номеров телефонов пользователей в БД
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "phonenumber_to_user")
public class PhoneNumber {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "phonenumber_id", nullable = false)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @ManyToOne
    private User user;
}
