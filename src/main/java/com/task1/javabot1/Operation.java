package com.task1.javabot1;

/**
 * Класс для представления финансовой операции
 */
public class Operation {
    private final String name;
    private final Double amount;
    private final String category;

    /**
    * Конструктор класса
    */
    public Operation(String name, Double amount, String category) {
        this.name = name != null ? name.trim() : "";
        this.amount = amount;
        this.category = category != null ? category.trim() : "";
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

