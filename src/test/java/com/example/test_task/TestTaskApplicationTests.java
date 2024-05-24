package com.example.test_task;

import com.example.test_task.entity.Email;
import com.example.test_task.entity.PhoneNumber;
import com.example.test_task.entity.User;
import com.example.test_task.service.TransactionService;
import com.example.test_task.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты функционирования функионала денежных перводов.
 * <p>
 *     Здесь не тестируется потокобезоасность переводов, т.к. она достигается за счет применения встроенных инструментов Spring (Аннотация @Transactional с максимальным уровнем изоляции).
 * </p>
 */
@SpringBootTest
class TestTaskApplicationTests {

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserService userService;

	@Test
	void amountOfMoneyTest() {
		User source = User.builder()
				.username("source")
				.password("blank")
				.nameFIO("blank")
				.birthDate(new Date(100))
				.account(40.0)
				.account_limit(200.0)
				.build();
		PhoneNumber sourcePhoneNumber = PhoneNumber.builder()
				.phoneNumber("sourcepn")
				.user(source)
				.build();
		Email sourceEmail = Email.builder()
				.email("sourceemail")
				.user(source)
				.build();
		User target = User.builder()
				.username("target")
				.password("blank")
				.nameFIO("blank")
				.birthDate(new Date(100))
				.account(100.0)
				.account_limit(200.0)
				.build();
		PhoneNumber targetPhoneNumber = PhoneNumber.builder()
				.phoneNumber("targetpn")
				.user(target)
				.build();
		Email targetEmail = Email.builder()
				.email("targetemail")
				.user(target)
				.build();
		userService.create(source, sourcePhoneNumber, sourceEmail);
		userService.create(target, targetPhoneNumber, targetEmail);
		assertThrows(IllegalArgumentException.class, () -> transactionService.transferMoney(source, "target", 50.0));
		assertEquals(40.0, source.getAccount(), 0.01);
		assertEquals(100.0, target.getAccount(), 0.01);
		userService.deleteUser("source");
		userService.deleteUser("target");
	}

	@Test
	void userExistsTest() {
		User source = User.builder()
				.username("source")
				.password("blank")
				.nameFIO("blank")
				.birthDate(new Date(100))
				.account(100.0)
				.account_limit(200.0)
				.build();
		PhoneNumber sourcePhoneNumber = PhoneNumber.builder()
				.phoneNumber("sourcepn")
				.user(source)
				.build();
		Email sourceEmail = Email.builder()
				.email("sourceemail")
				.user(source)
				.build();
		User target = User.builder()
				.username("target")
				.password("blank")
				.nameFIO("blank")
				.birthDate(new Date(100))
				.account(100.0)
				.account_limit(200.0)
				.build();
		PhoneNumber targetPhoneNumber = PhoneNumber.builder()
				.phoneNumber("targetpn")
				.user(target)
				.build();
		Email targetEmail = Email.builder()
				.email("targetemail")
				.user(target)
				.build();
		userService.create(source, sourcePhoneNumber, sourceEmail);
		userService.create(target, targetPhoneNumber, targetEmail);
		transactionService.transferMoney(source, "target", 50.0);
		source = userService.getByUsername("source");
		target = userService.getByUsername("target");
		assertEquals(100.0 - 50.0, source.getAccount(), 0.01);
		assertEquals(100.0 + 50.0, target.getAccount(), 0.01);
		userService.deleteUser("source");
		userService.deleteUser("target");
	}

	@Test
	void userDoesNotExistTest() {
		User source = User.builder()
				.username("source")
				.password("blank")
				.nameFIO("blank")
				.birthDate(new Date(100))
				.account(200.0)
				.account_limit(200.0)
				.build();
		PhoneNumber sourcePhoneNumber = PhoneNumber.builder()
				.phoneNumber("sourcepn")
				.user(source)
				.build();
		Email sourceEmail = Email.builder()
				.email("sourceemail")
				.user(source)
				.build();
		userService.create(source, sourcePhoneNumber, sourceEmail);
		assertThrows(UsernameNotFoundException.class, () -> transactionService.transferMoney(source, "target", 50.0));
		assertEquals(200.0, source.getAccount(), 0.01);
		userService.deleteUser("source");
	}
}
