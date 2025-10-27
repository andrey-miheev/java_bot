package com.task1.javabot1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
