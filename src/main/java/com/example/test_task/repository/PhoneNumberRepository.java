package com.example.test_task.repository;

import com.example.test_task.entity.Email;
import com.example.test_task.entity.PhoneNumber;
import com.example.test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA-репозиторий для работы с номерами телефонов
 */
@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<PhoneNumber> getByPhoneNumberAndUser(String phoneNumber, User currentUser);
}
