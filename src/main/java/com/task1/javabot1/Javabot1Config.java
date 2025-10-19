package com.task1.javabot1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Конфигурационный класс для настройки Telegram бота.
 * Регистрирует бота в Telegram API при запуске приложения.
 */
@Configuration
public class Javabot1Config {

    /**
     * Создает и регистрирует Telegram бота в API.
     *
     * @param bot экземпляр бота для регистрации
     * @return настроенный TelegramBotsApi
     * @throws TelegramApiException если регистрация не удалась
     */
    @Bean
    public TelegramBotsApi telegramBotsApi(MyTelegramBot bot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        return botsApi;
    }
}
