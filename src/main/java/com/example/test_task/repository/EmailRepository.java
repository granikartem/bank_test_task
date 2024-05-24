package com.example.test_task.repository;

import com.example.test_task.entity.Email;
import com.example.test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA-репозиторий для работы с адресами электронной почты
 */
@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    boolean existsByEmail(String email);

    Optional<Email> getByEmailAndUser(String email, User user);

}
