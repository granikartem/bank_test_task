package com.example.test_task.service;

import com.example.test_task.entity.Email;
import com.example.test_task.entity.PhoneNumber;
import com.example.test_task.entity.User;
import com.example.test_task.repository.EmailRepository;
import com.example.test_task.repository.PhoneNumberRepository;
import com.example.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с пользователями
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final EmailRepository emailRepo;
    private final PhoneNumberRepository phoneRepo;

    public User save(User user, PhoneNumber phoneNumber, Email email) {
        User result = repository.save(user);
        phoneRepo.save(phoneNumber);
        emailRepo.save(email);
        return result;
    }


    public User create(User user, PhoneNumber phoneNumber, Email email) throws IllegalArgumentException{
        if (repository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        } else if(phoneRepo.existsByPhoneNumber(phoneNumber.getPhoneNumber())){
            throw new IllegalArgumentException("Телефон занят");
        }else if(emailRepo.existsByEmail(email.getEmail())){
            throw new IllegalArgumentException("Почта занята");
        }
        return save(user, phoneNumber, email);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public Email updateEmail(String oldEmail, String newEmail) throws DataAccessException {
        Optional<Email> opt = emailRepo.getByEmailAndUser(oldEmail, getCurrentUser());
        if(opt.isPresent()) {
            Email email = opt.get();
            email.setEmail(newEmail);
            return emailRepo.save(email);
        } else{
            throw new IllegalArgumentException("Email not found");
        }
    }

    public Email addEmail(String email) throws DataAccessException{
        Email emailEntity = Email.builder()
                .email(email)
                .user(getCurrentUser())
                .build();
        return emailRepo.save(emailEntity);
    }

    public void deleteEmail(String email) {
        User user = getCurrentUser();
        Optional<Email> opt = emailRepo.getByEmailAndUser(email, user);
        if(opt.isPresent()){
            if(user.getEmails().size() > 1){
                emailRepo.delete(opt.get());
            }else{
                throw new IllegalStateException("Нельзя удалить последний адрес электронной почты");
            }
        } else{
            throw new IllegalArgumentException("У вас нет такого адреса электронной почты");
        }
    }

    public PhoneNumber updatePhoneNumber(String oldPhoneNumber, String newPhoneNumber) throws DataAccessException{
        Optional<PhoneNumber> opt = phoneRepo.getByPhoneNumberAndUser(oldPhoneNumber, getCurrentUser());
        if(opt.isPresent()) {
            PhoneNumber phoneNumber = opt.get();
            phoneNumber.setPhoneNumber(newPhoneNumber);
            return phoneRepo.save(phoneNumber);
        } else{
            throw new IllegalArgumentException("Email not found");
        }
    }

    public PhoneNumber addPhoneNumber(String phoneNumber) throws DataAccessException {
        PhoneNumber phoneNumberEntity = PhoneNumber.builder()
                .phoneNumber(phoneNumber)
                .user(getCurrentUser())
                .build();
        return phoneRepo.save(phoneNumberEntity);
    }

    public void deletePhoneNumber(String phoneNumber) {
        User user = getCurrentUser();
        Optional<PhoneNumber> opt = phoneRepo.getByPhoneNumberAndUser(phoneNumber, user);
        if(opt.isPresent()){
            if(user.getPhoneNumbers().size() > 1){
                phoneRepo.delete(opt.get());
            }else{
                throw new IllegalStateException("Нельзя удалить последний номер телефона");
            }
        } else{
            throw new IllegalArgumentException("У вас нет такого номера телефона");
        }
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public void deleteUser(String username) {
        User user = getByUsername(username);
        repository.delete(user);
    }
}
