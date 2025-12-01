package com.task1.javabot1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Умный менеджер данных пользователя.
 * Инкапсулирует логику работы с задачами
 */
public class UserData {
    private final Map<String, List<Double>> incomes = new HashMap<>();
    private final Map<String, List<Double>> expenses = new HashMap<>();

    /**
     * Возвращает копию списка доходов для предотвращения случайных изменений
     */
    public Map<String, List<Double>> getIncomes() {
        return incomes;
    }

    /**
     * Возвращает копию списка расходов для предотвращения случайных изменений
     */
    public Map<String, List<Double>> getExpenses() {
        return expenses;
    }

    /**
     * Добавляет операцию дохода.
     *
     * @param name название операции
     * @param amount сумма операции
     */
    public String addIncome(String name, Double amount) {
        String trimmedName = name.trim();
        incomes.computeIfAbsent(trimmedName, k -> new ArrayList<>()).add(amount);
        return "Доход «%s» на сумму %,.2f добавлен.";
    }

    /**
     * Добавляет операцию расхода.
     *
     * @param name название операции
     * @param amount сумма операции
     */
    public String addExpense(String name, Double amount) {
        String trimmedName = name.trim();
        expenses.computeIfAbsent(trimmedName, k -> new ArrayList<>()).add(amount);
        return "Расход «%s» на сумму %,.2f добавлен.";
    }

    /**
     * Удаляет операцию дохода на  определённую сумму
     *
     * @param name название операции
     * @param amount сумма
     */
    public String deleteIncome(String name, Double amount){
        String trimmedName = name.trim();

        List<Double> amounts = incomes.get(trimmedName);

        boolean removed = amounts.removeIf(a -> Math.abs(a - amount) < 0.01);

        if (removed) {
            if (amounts.isEmpty()) {
                incomes.remove(trimmedName);
            }
            return "Доход «" + trimmedName + "» на сумму " + amount + " удален.";
        }
        else {
            return "Сумма " + amount + " не найдена в доходе «" + trimmedName + "»";
        }
    }

    /**
     * Удаляет операцию расхода на определённую сумму
     *
     * @param name название операции
     * @param amount сумма
     */
    public String deleteExpense(String name, Double amount){
        String trimmedName = name.trim();

        List<Double> amounts = expenses.get(trimmedName);

        boolean removed = amounts.removeIf(a -> Math.abs(a - amount) < 0.01);

        if (removed) {
            if (amounts.isEmpty()) {
                expenses.remove(trimmedName);
            }
            return "Расход «" + trimmedName + "» на сумму " + amount + " удален.";
        }
        else {
            return "Сумма " + amount + " не найдена в расходе «" + trimmedName + "»";
        }
    }

    /**
     * Проверяет, есть ли доходы.
     */
    public boolean hasIncomes() {
        return !incomes.isEmpty();
    }

    /**
     * Проверяет, есть ли расходы.
     */
    public boolean hasExpenses() {
        return !expenses.isEmpty();
    }
}
