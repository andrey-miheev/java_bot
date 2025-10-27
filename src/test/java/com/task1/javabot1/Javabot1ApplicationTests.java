package com.task1.javabot1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для Telegram бота.
 * Проверяет корректность обработки пользовательских сообщений.
 */
class MessageHandlerTest {
    private final MessageHandler messageHandler = new MessageHandler();

    /**
     * Проверяет, что бот правильно повторяет пользовательское сообщение.
     */
    @Test
    void testEchoCommand() {
        String testInput = "TEST";
        String userId = "testUser115";
        assertEquals("Твое сообщение: TEST", messageHandler.processUserInput(testInput, userId));
    }


}
