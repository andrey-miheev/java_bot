package com.task1.javabot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.List;

/**
 * Тесты для проверки корректной структуры и содержания клавиатуры,
 * создаваемой классом Keyboard
 */
class KeyboardTest {

    private Keyboard keyboard;

    /**
     * Создаёт новый экземпляр Keyboard перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        keyboard = new Keyboard();
    }

    /**
     * Проверяет, что метод mainKeyboard() возвращает корректно созданную клавиатуру:
     * содержит 5 строк по 2 кнопки с ожидаемыми текстами
     */
    @Test
    void shouldHaveCorrectLayoutAndButtonTexts() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();

        Assertions.assertEquals(ReplyKeyboardMarkup.class, reply.getClass());

        List<KeyboardRow> rows = reply.getKeyboard();
        Assertions.assertEquals(5, rows.size());

        String[][] expected = {
                {"Список доходов", "Список расходов"},
                {"Баланс", "Статистика"},
                {"Топ доходов", "Топ расходов"},
                {"Сумма доходов", "Сумма расходов"},
                {"Количество операций", "Помощь"}
        };

        for (int i = 0; i < expected.length; i++) {
            KeyboardRow row = rows.get(i);
            Assertions.assertEquals(2, row.size());

            Assertions.assertEquals(expected[i][0], row.get(0).getText());
            Assertions.assertEquals(expected[i][1], row.get(1).getText());
        }
    }

    /**
     * Проверяет, что параметры resizeKeyboard и oneTimeKeyboard
     * установлены корректно
     */
    @Test
    void shouldConfigureFlagsProperly() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();

        Assertions.assertEquals(Boolean.TRUE, reply.getResizeKeyboard());
        Assertions.assertEquals(Boolean.FALSE, reply.getOneTimeKeyboard());
    }
}