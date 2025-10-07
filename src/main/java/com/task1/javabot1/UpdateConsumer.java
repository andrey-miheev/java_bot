package com.task1.javabot1;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private TelegramClient telegramClient;

    public UpdateConsumer() {
        this.telegramClient = new OkHttpTelegramClient(System.getenv("TELEGRAM_BOT_TOKEN"));
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            System.out.printf("Пришло сообщение %s от %s%n", userText, chatId);

            String replyText = processUserInput(userText);

            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text(replyText)
                    .build();

            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Логика обработки пользовательского ввода вынесена в отдельный метод.
     * Удобно для тестирования.
     */
    public String processUserInput(String userInput) {
        return switch (userInput) {
            case "/start" -> """
                    Привет!
                    Я эхо-бот. 
                    Я умею:
                    - Повторять твои сообщения
                    - Показывать список команд (/help)
                    """;
            case "/help" -> """
                    Вот что я умею:
                    /start - познакомиться со мной
                    /help  - список всех команд
                    (любое другое сообщение я повторю тебе обратно)
                    """;
            default -> "Твое сообщение: " + userInput;
        };
    }
}