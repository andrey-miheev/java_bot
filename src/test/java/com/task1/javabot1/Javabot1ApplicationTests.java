package com.task1.javabot1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateConsumerTest {

    @Test
    void testEchoCommand() {
        UpdateConsumer consumer = new UpdateConsumer();
        assertEquals("Твое сообщение: TEST", consumer.processUserInput("TEST"));
    }

    @Test
    void testStartCommand() {
        UpdateConsumer consumer = new UpdateConsumer();
        String result = consumer.processUserInput("/start");

        assertTrue(result.contains("Привет!"));
        assertTrue(result.contains("Я эхо-бот."));
        assertTrue(result.contains("Повторять твои сообщения"));
    }

    @Test
    void testHelpCommand() {
        UpdateConsumer consumer = new UpdateConsumer();
        assertEquals("Вот что я умею:\n/start - познакомиться со мной\n/help  - список всех команд\n(любое другое сообщение я повторю тебе обратно)\n",
                consumer.processUserInput("/help"));
    }
}