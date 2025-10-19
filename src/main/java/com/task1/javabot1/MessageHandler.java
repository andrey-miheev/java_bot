package com.task1.javabot1;

/**
 * Обрабатывает входящие сообщения для бота.
 */
public class MessageHandler {

    /**
     * Обрабатывает ввод пользователя и возвращает ответ.
     *
     * @param userInput текст сообщения от пользователя
     * @param userId идентификатор пользователя для логирования
     * @return текстовый ответ бота
     */
    public String processUserInput(String userInput, String userId) {
        System.out.printf("Пришло сообщение %s от %s%n", userInput, userId);
        return Response(userInput);

    }

    /**
     * Выбирает ответ, в зависимости от сообщения пользователя.
     *
     * @param userInput текст сообщения от пользователя
     * @return выбранный текстовый ответ
     */
    public String Response(String userInput) {
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