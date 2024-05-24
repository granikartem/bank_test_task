package com.example.test_task.service;

import com.example.test_task.entity.User;
import com.example.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с денежными переводами.
 * <p>
 *     Потокобезопасноть переводов достигается здесь за счет использования наиболее высокого уровня транзакции при реализации днежных перводов.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final UserRepository repository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transferMoney(String userName, double sum){
        User source = getCurrentUser();
        transferMoney(source, userName, sum);
    }

    public void transferMoney(User source, String userName, double sum){
        if(Double.compare(source.getAccount(), sum) == 1){
            User target = getByUsername(userName);
            double oldAccount = source.getAccount();
            source.setAccount(oldAccount - sum);
            double newAccount = target.getAccount();
            target.setAccount(newAccount + sum);
            repository.save(source);
            repository.save(target);
        }else{
            throw new IllegalArgumentException("На вашем счете недостаточно денег");
        }
    }

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void scheduleAccountUpdate() {
        System.out.println("Account Increase performed");
        System.out.println(System.currentTimeMillis() / 1000);
        repository.increaseAccounts();
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
