package com.task1.javabot1;

/**
 * Класс для представления финансовой операции
 */
public class Operation {
    private final String name;        // Название операции
    private final Double amount;      // Сумма
    private final String category;    // Категория

    public Operation(String name, Double amount, String category) {
        this.name = name != null ? name.trim() : "";
        this.amount = amount;
        this.category = category != null ? category.trim() : "";
    }

    // Геттеры
    public String getName() { return name; }
    public Double getAmount() { return amount; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("Операция: %s, Сумма: %,.2f, Категория: %s",
                name, amount, category);
    }

    /**
     * Проверяет, соответствует ли операция названию и сумме
     */
    public boolean matches(String name, Double amount) {
        return this.name.equals(name.trim()) &&
                Math.abs(this.amount - amount) < 0.01;
    }
}
