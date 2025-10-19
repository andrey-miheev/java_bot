package com.task1.javabot1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс для запуска Telegram бота.
 * Инициализирует Spring Boot приложение и запускает бота.
 */
@SpringBootApplication
public class Javabot1Application {

    /**
     * Точка входа в приложение.
     * Запускает Spring Boot контекст и активирует Telegram бота.
     *
     * @param args аргументы командной строки
     */
	public static void main(String[] args) {

        SpringApplication.run(Javabot1Application.class, args);
	}
}
