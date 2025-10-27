package com.task1.javabot1;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
/**
 * Главный класс для запуска Telegram бота.
 * Инициализирует и запускает бота.
 */
public class Javabot1Application {
    /**
     * Точка входа в приложение.
     * Запускает Telegram бота.
     * @param args аргументы командной строки
     */
    public static void main(String[] args) throws TelegramApiException {
        MyTelegramBot bot = new MyTelegramBot();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        System.out.println("Bot started working");
    }
}
