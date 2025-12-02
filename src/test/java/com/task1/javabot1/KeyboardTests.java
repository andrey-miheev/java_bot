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
     * Проверяет, что метод mainKeyboard() возвращает корректно созданный объект
     * ReplyKeyboardMarkup: не null, правильного типа, содержит строки
     */
    @Test
    void shouldCreateMainKeyboard() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();

        Assertions.assertEquals(ReplyKeyboardMarkup.class, reply.getClass());
        
        Assertions.assertEquals(true, reply.getKeyboard() != null);
        Assertions.assertEquals(true, reply.getKeyboard().size() > 0);
    }

    /**
     * Проверяет, что общая клавиатура содержит ровно 10 кнопок:
     * 5 строк по 2 кнопки каждая
     */
    @Test
    void shouldContainTenButtonsTotal() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        int totalButtons = 0;
        for (KeyboardRow row : rows) {
            totalButtons += row.size();
        }

        Assertions.assertEquals(10, totalButtons);
    }

    /**
     * Проверяет, что параметры поведения клавиатуры настроены корректно:
     * resizeKeyboard = true, oneTimeKeyboard = false
     */
    @Test
    void shouldConfigureFlagsProperly() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();

        Assertions.assertEquals(Boolean.TRUE, reply.getResizeKeyboard());
        Assertions.assertEquals(Boolean.FALSE, reply.getOneTimeKeyboard());
    }

    /**
     * Проверяет, что клавиатура состоит ровно из 5 строк
     */
    @Test
    void shouldHaveFiveRows() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        Assertions.assertEquals(5, rows.size());
    }

    /**
     * Проверяет, что в каждой строке клавиатуры ровно по 2 кнопки
     */
    @Test
    void shouldHaveTwoButtonsInEachRow() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        for (int i = 0; i < rows.size(); i++) {
            Assertions.assertEquals(2, rows.get(i).size(),
                    "Row " + (i + 1) + " should have 2 buttons");
        }
    }

    /**
     * Проверяет текст кнопок в первой строке клавиатуры
     */
    @Test
    void shouldHaveCorrectButtonTextsInRow1() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        Assertions.assertEquals("Список доходов", rows.get(0).get(0).getText());
        Assertions.assertEquals("Список расходов", rows.get(0).get(1).getText());
    }

    /**
     * Проверяет текст кнопок во второй строке клавиатуры
     */
    @Test
    void shouldHaveCorrectButtonTextsInRow2() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        Assertions.assertEquals("Баланс", rows.get(1).get(0).getText());
        Assertions.assertEquals("Статистика", rows.get(1).get(1).getText());
    }

    /**
     * Проверяет текст кнопок в третьей строке клавиатуры
     */
    @Test
    void shouldHaveCorrectButtonTextsInRow3() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        Assertions.assertEquals("Топ доходов", rows.get(2).get(0).getText());
        Assertions.assertEquals("Топ расходов", rows.get(2).get(1).getText());
    }

    /**
     * Проверяет текст кнопок в четвертой строке клавиатуры
     */
    @Test
    void shouldHaveCorrectButtonTextsInRow4() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        Assertions.assertEquals("Сумма доходов", rows.get(3).get(0).getText());
        Assertions.assertEquals("Сумма расходов", rows.get(3).get(1).getText());
    }

    /**
     * Проверяет текст кнопок в пятой строке клавиатуры
     */
    @Test
    void shouldHaveCorrectButtonTextsInRow5() {
        ReplyKeyboardMarkup reply = keyboard.mainKeyboard();
        List<KeyboardRow> rows = reply.getKeyboard();

        Assertions.assertEquals("Количество операций", rows.get(4).get(0).getText());
        Assertions.assertEquals("Помощь", rows.get(4).get(1).getText());
    }
}
