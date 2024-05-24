package com.example.test_task.repository;

import com.example.test_task.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * JPA-репозиторий для работы с пользователями
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByBirthDateAfter(Date birthDate);

    @Query("select p.user from PhoneNumber p where p.phoneNumber = ?1")
    User findByPhoneNumber(String phoneNumber);

    @Query("select e.user from Email e where e.email = ?1")
    User findByEmail(String email);

    List<User> findAllByNameFIOLike(String nameFIO);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.account = case when u.account * 1.05 < u.account_limit then u.account * 1.05 " +
            "when u.account > u.account_limit then u.account else u.account_limit end")
    void increaseAccounts();
}
