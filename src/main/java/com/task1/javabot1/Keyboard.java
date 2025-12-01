package com.task1.javabot1;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс создания кнопок
 */
public class Keyboard {

    /**
     * Основная клавиатура с командами бота.
     * @return ReplyKeyboardMarkup – клавиатура с кнопками команд
     */
    public ReplyKeyboardMarkup mainKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> rows = new ArrayList<>();


        KeyboardRow row1 = new KeyboardRow();
        row1.add("Список доходов");
        row1.add("Список расходов");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Баланс");
        row2.add("Статистика");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Топ доходов");
        row3.add("Топ расходов");

        KeyboardRow row4 = new KeyboardRow();
        row4.add("Сумма доходов");
        row4.add("Сумма расходов");

        KeyboardRow row5 = new KeyboardRow();
        row5.add("Количество операций");
        row5.add("Помощь");

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);

        keyboard.setKeyboard(rows);
        return keyboard;
    }
}
