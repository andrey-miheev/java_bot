package com.task1.javabot1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * Класс для представления финансовой операции
 */
public class Operation {
    private final String name;
    private final Double amount;
    private final String category;
    private final LocalDate date;

    /**
    * Конструктор класса
    */
    public Operation(String name, Double amount, String category, LocalDate date) {
        this.name = name != null ? name.trim() : "";
        this.amount = amount;
        this.category = category != null ? category.trim() : "";
        this.date = date != null ? date : LocalDate.now();
    }

    /**
     * Конструктор класса с текущим временем (для обратной совместимости)
     */
    public Operation(String name, Double amount, String category) {
        this(name, amount, category, LocalDate.now());
    }

    /**
    * Возвращаем название операции
    */
    public String getName() { return name; }

    /**
    * Возвращаем сумму операции
    */
    public Double getAmount() { return amount; }
    
    /**
    * Возвращаем категорию операции
    */
    public String getCategory() { return category; }

    /**
     * Возвращаем дату операции
     */
    public LocalDate getDate() { return date; }

    /**
     * Форматированная дата для вывода
     */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("Операция: %s, Сумма: %,.2f, Категория: %s Дата: %s",
                name, amount, category, getFormattedDate());
    }

    /**
     * Проверяет, соответствует ли операция названию и сумме
     */
    public boolean matches(String name, Double amount) {
        return this.name.equals(name.trim()) &&
                Math.abs(this.amount - amount) < 0.01;
    }
}

