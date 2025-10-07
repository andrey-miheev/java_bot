package com.task1.javabot1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateConsumerTest {

    /**
     * Проверяет, что бот правильно повторяет пользовательское сообщение.
     */
    @Test
    void testEchoCommand() {
        UpdateConsumer consumer = new UpdateConsumer();
        assertEquals("Твое сообщение: TEST", consumer.processUserInput("TEST"));
    }

    /**
     * Проверяет корректность приветственного сообщения при вводе команды /start.
     */
    @Test
    void testStartCommand() {
        UpdateConsumer consumer = new UpdateConsumer();

        String expected = """
                Привет!
                Я эхо-бот. 
                Я умею:
                - Повторять твои сообщения
                - Показывать список команд (/help)
                """;

        assertEquals(expected, consumer.processUserInput("/start"));
    }

    /**
     * Проверяет корректность вывода справки по командам при вводе /help.
     */
    @Test
    void testHelpCommand() {
        UpdateConsumer consumer = new UpdateConsumer();
        String expected = """
                Вот что я умею:
                /start - познакомиться со мной
                /help  - список всех команд
                (любое другое сообщение я повторю тебе обратно)
                """;
        assertEquals(expected, consumer.processUserInput("/help"));
    }
}
