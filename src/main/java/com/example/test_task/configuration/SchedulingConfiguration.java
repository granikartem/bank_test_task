package com.example.test_task.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Класс конфигурации, необходимый для реализации задач согласно расписанию.
 * В разрабатываемой системе необходим для реализации начисления процентов на счет.
 */

@Configuration
@EnableScheduling
public class SchedulingConfiguration {
}
