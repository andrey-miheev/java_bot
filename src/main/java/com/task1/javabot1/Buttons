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
        row1.add("/add_in");
        row1.add("/add_ex");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("/income");
        row2.add("/expense");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("/balance");
        row3.add("/statistic");

        KeyboardRow row4 = new KeyboardRow();
        row4.add("/top_in");
        row4.add("/top_exp");

        KeyboardRow row5 = new KeyboardRow();
        row5.add("/sum_income");
        row5.add("/sum_expense");

        KeyboardRow row6 = new KeyboardRow();
        row6.add("/count_ops");
        row6.add("/help");

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);
        rows.add(row5);
        rows.add(row6);

        keyboard.setKeyboard(rows);
        return keyboard;
    }
}
