package com.task1.javabot1;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Основной класс Telegram-бота.
 * Обрабатывает входящие сообщения и отправляет ответы пользователям.
 * Использует Long Polling для получения обновлений от Telegram API.
 */
public class MyTelegramBot extends TelegramLongPollingBot {
    private final MessageHandler messageHandler = new MessageHandler();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userInput = update.getMessage().getText();
            String userId = update.getMessage().getFrom().getId().toString();
            String chatId = update.getMessage().getChatId().toString();

            String response = messageHandler.processUserInput(userInput,userId);
            sendMessage(chatId,response);
        }
    }

    /**
     * Создание сообщения от бота.
     *
     * @param chatId ID чата.
     * @param text обработанный ответ бота.
     */
    private void sendMessage(String chatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try{
            execute(message);
        }catch(TelegramApiException e){

            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "java_mih_grib_bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

}
