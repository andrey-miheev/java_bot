package com.task1.javabot1;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

/**
 * Основной класс Telegram-бота.
 * Подключает обработчик входящих сообщений и передаёт токен бота.
 */
@Component
public class MyTelegramBot implements SpringLongPollingBot {

    /** Обработчик сообщений от пользователей. */
    private final UpdateConsumer updateConsumer;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param updateConsumer обработчик входящих обновлений
     */
    public MyTelegramBot(UpdateConsumer updateConsumer) {
        this.updateConsumer = updateConsumer;
    }

    /**
     * Возвращает токен Telegram-бота из переменных окружения.
     *
     * @return токен бота
     */
    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

    /**
     * Возвращает обработчик обновлений (сообщений).
     *
     * @return объект UpdateConsumer
     */
    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}
