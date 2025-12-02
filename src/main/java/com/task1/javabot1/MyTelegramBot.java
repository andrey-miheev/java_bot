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
    private final Keyboard keyboard = new Keyboard();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userInput = mapButtonToCommand(update.getMessage().getText());
            String userId = update.getMessage().getFrom().getId().toString();
            String chatId = update.getMessage().getChatId().toString();

            String response = messageHandler.processUserInput(userInput,userId);
            sendMessage(chatId,response);
        }
    }
    
    /**
     * Преобразует текст кнопки в соответствующую команду бота.
     * Если текст не совпадает ни с одной кнопкой — возвращает его без изменений.
     *
     * @param input текст, пришедший от пользователя
     * @return строка с командой вида /command или исходный текст
     */
    private String mapButtonToCommand(String input) {
        return switch (input) {
            case "Список доходов" -> "/income";
            case "Список расходов" -> "/expense";
            case "Баланс" -> "/balance";
            case "Статистика" -> "/statistic";
            case "Топ доходов" -> "/top_in";
            case "Топ расходов" -> "/top_ex";
            case "Сумма доходов" -> "/sum_income";
            case "Сумма расходов" -> "/sum_expense";
            case "Количество операций" -> "/count_ops";
            case "Помощь" -> "/help";
            default -> input;
        };
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
        message.setReplyMarkup(keyboard.mainKeyboard());

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





