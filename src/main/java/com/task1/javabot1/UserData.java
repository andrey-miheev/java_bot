package com.task1.javabot1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

/**
 * Умный менеджер данных пользователя.
 * Инкапсулирует логику работы с задачами
 */
public class UserData {
    // Храним операции по категориям (как в старом коде, но с Operation)
    private final Map<String, List<Operation>> incomes = new HashMap<>();
    private final Map<String, List<Operation>> expenses = new HashMap<>();

    // Категории пользователя
    private final Set<String> incomeCategories = new HashSet<>();
    private final Set<String> expenseCategories = new HashSet<>();

    public UserData() {
        initDefaultCategories();
    }

    /**
    * Добавление начальных категорий
    */
    private void initDefaultCategories() {
        incomeCategories.add("работа");
        incomeCategories.add("подарок");

        expenseCategories.add("еда");
        expenseCategories.add("транспорт");
        expenseCategories.add("дом");
        expenseCategories.add("здоровье");
        expenseCategories.add("развлечения");
        expenseCategories.add("другое");
    }

    /**
     * Показывает категории доходов
     */
    public String showIncomeCategories() {
        List<String> sorted = getIncomeCategoriesSorted();
        if (sorted.isEmpty()) {
            return "Доступные категории доходов:\n— Категорий пока нет";
        }

        StringBuilder sb = new StringBuilder("Доступные категории доходов:\n");
        for (String category : sorted) {
            sb.append("• ").append(category).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Показывает категории расходов
     */
    public String showExpenseCategories() {
        List<String> sorted = getExpenseCategoriesSorted();
        if (sorted.isEmpty()) {
            return "Доступные категории расходов:\n— Категорий пока нет";
        }

        StringBuilder sb = new StringBuilder("Доступные категории расходов:\n");
        for (String category : sorted) {
            sb.append("• ").append(category).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Добавляет категорию доходов
     */
    public String addIncomeCategory(String category) {
        String trimmedCategory = category.trim();
        if (trimmedCategory.isEmpty()) {
            return "Название категории не может быть пустым.";
        }

        if (incomeCategories.contains(trimmedCategory)) {
            return "Категория «" + trimmedCategory + "» уже существует.";
        }

        incomeCategories.add(trimmedCategory);
        return "Категория «" + trimmedCategory + "» добавлена.";
    }

    /**
     * Добавляет категорию расходов
     */
    public String addExpenseCategory(String category) {
        String trimmedCategory = category.trim();
        if (trimmedCategory.isEmpty()) {
            return "Название категории не может быть пустым.";
        }

        if (expenseCategories.contains(trimmedCategory)) {
            return "Категория «" + trimmedCategory + "» уже существует.";
        }

        expenseCategories.add(trimmedCategory);
        return "Категория «" + trimmedCategory + "» добавлена.";
    }

    /**
     * Удаляет категорию доходов
     */
    public String deleteIncomeCategory(String category) {
        String trimmedCategory = category.trim();

        if (!incomeCategories.contains(trimmedCategory)) {
            return "Категория «" + trimmedCategory + "» не найдена.";
        }

        // Проверяем, используется ли категория
        if (incomes.containsKey(trimmedCategory) &&
                !incomes.get(trimmedCategory).isEmpty()) {
            int count = incomes.get(trimmedCategory).size();
            return "Категория «" + trimmedCategory + "» используется в " + count +
                    " операциях. Сначала удалите или измените эти операции.";
        }

        incomeCategories.remove(trimmedCategory);
        return "Категория «" + trimmedCategory + "» удалена.";
    }

    /**
     * Удаляет категорию расходов
     */
    public String deleteExpenseCategory(String category) {
        String trimmedCategory = category.trim();

        if (!expenseCategories.contains(trimmedCategory)) {
            return "Категория «" + trimmedCategory + "» не найдена.";
        }

        // Проверяем, используется ли категория
        if (expenses.containsKey(trimmedCategory) &&
                !expenses.get(trimmedCategory).isEmpty()) {
            int count = expenses.get(trimmedCategory).size();
            return "Категория «" + trimmedCategory + "» используется в " + count +
                    " операциях. Сначала удалите или измените эти операции.";
        }

        expenseCategories.remove(trimmedCategory);
        return "Категория «" + trimmedCategory + "» удалена.";
    }

    /**
     * Добавляет операцию дохода с категорией
     */
    public String addIncome(String name, Double amount, String category) {
        String trimmedName = name.trim();
        String trimmedCategory = category.trim();

        // Проверяем существование категории
        if (!incomeCategories.contains(trimmedCategory)) {
            return buildCategoryError(trimmedCategory, "доходов", getIncomeCategoriesSorted());
        }

        // Создаем операцию
        Operation operation = new Operation(trimmedName, amount, trimmedCategory);

        // Используем computeIfAbsent как в старом коде
        incomes.computeIfAbsent(trimmedCategory, k -> new ArrayList<>()).add(operation);

        return "– Доход «" + trimmedName + "» на сумму " +
                String.format("%,.2f", amount) + " добавлен.\n" +
                "Категория: " + trimmedCategory;
    }

    /**
     * Добавляет операцию расхода с категорией
     */
    public String addExpense(String name, Double amount, String category) {
        String trimmedName = name.trim();
        String trimmedCategory = category.trim();

        // Проверяем существование категории
        if (!expenseCategories.contains(trimmedCategory)) {
            return buildCategoryError(trimmedCategory, "расходов", getExpenseCategoriesSorted());
        }

        // Создаем операцию
        Operation operation = new Operation(trimmedName, amount, trimmedCategory);

        // Используем computeIfAbsent как в старом коде
        expenses.computeIfAbsent(trimmedCategory, k -> new ArrayList<>()).add(operation);

        return "– Расход «" + trimmedName + "» на сумму " +
                String.format("%,.2f", amount) + " добавлен.\n" +
                "Категория: " + trimmedCategory;
    }

    /**
     * Удаляет операцию дохода (старый стиль - для совместимости)
     */
    public String deleteIncome(String name, Double amount) {
        String trimmedName = name.trim();
        for (Map.Entry<String, List<Operation>> entry : incomes.entrySet()) {
            List<Operation> operations = entry.getValue();
            boolean removed = operations.removeIf(op -> op.matches(trimmedName, amount));

            if (removed) {
                if (operations.isEmpty()) {
                    incomes.remove(entry.getKey());
                }
                return "Доход «" + trimmedName + "» на сумму " + amount + " удален.";
            }
        }

        return "Сумма " + amount + " не найдена в доходе «" + trimmedName + "»";
    }

    /**
     * Удаляет операцию расхода (старый стиль - для совместимости)
     */
    public String deleteExpense(String name, Double amount) {
        String trimmedName = name.trim();
        for (Map.Entry<String, List<Operation>> entry : expenses.entrySet()) {
            List<Operation> operations = entry.getValue();
            boolean removed = operations.removeIf(op -> op.matches(trimmedName, amount));

            if (removed) {
                if (operations.isEmpty()) {
                    expenses.remove(entry.getKey());
                }
                return "Расход «" + trimmedName + "» на сумму " + amount + " удален.";
            }
        }

        return "Сумма " + amount + " не найдена в расходе «" + trimmedName + "»";
    }

    /**
     * Возвращает все операции доходов
     */
    public List<Operation> getAllIncomes() {
        List<Operation> all = new ArrayList<>();
        for (List<Operation> ops : incomes.values()) {
            all.addAll(ops);
        }
        return all;
    }

    /**
     * Возвращает все операции расходов
     */
    public List<Operation> getAllExpenses() {
        List<Operation> all = new ArrayList<>();
        for (List<Operation> ops : expenses.values()) {
            all.addAll(ops);
        }
        return all;
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

    /**
     * Возвращает статистику
     */
    public String getStatistics() {
        double totalIncome = getTotalIncome();
        double totalExpense = getTotalExpense();
        double balance = totalIncome - totalExpense;

        // Получаем статистику по категориям
        Map<String, Double> incomeStats = getIncomeStatsByCategory();
        Map<String, Double> expenseStats = getExpenseStatsByCategory();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Сумма доходов: %,.2f \n", totalIncome))
                .append(String.format("Сумма расходов: %,.2f \n", totalExpense))
                .append(String.format("Оставшийся бюджет: %,.2f\n", balance))
                .append("Статистика по категориям за месяц:\n\n");

        sb.append("Доходы:\n");
        for (String category : getIncomeCategoriesSorted()) {
            double amount = incomeStats.getOrDefault(category, 0.0);
            sb.append(String.format("%s: %,.2f\n", category, amount));
        }

        sb.append("\nРасходы:\n");
        for (String category : getExpenseCategoriesSorted()) {
            double amount = expenseStats.getOrDefault(category, 0.0);
            sb.append(String.format("%s: %,.2f\n", category, amount));
        }

        return sb.toString().trim();
    }

    /**
     * Показывает доходы
     */
    public String showIncomes() {
        List<Operation> allIncomes = getAllIncomes();
        if (allIncomes.isEmpty()) {
            return "— Доходов пока нет";
        }

        StringBuilder sb = new StringBuilder();
        for (Operation op : allIncomes) {
            sb.append("— Доход «").append(op.getName())
                    .append("» на сумму ").append(String.format("%,.2f", op.getAmount()))
                    .append(" (категория: ").append(op.getCategory()).append(")\n");
        }
        return sb.toString().trim();
    }

    /**
     * Показывает расходы
     */
    public String showExpenses() {
        List<Operation> allExpenses = getAllExpenses();
        if (allExpenses.isEmpty()) {
            return "— Расходов пока нет";
        }

        StringBuilder sb = new StringBuilder();
        for (Operation op : allExpenses) {
            sb.append("— Расход «").append(op.getName())
                    .append("» на сумму ").append(String.format("%,.2f", op.getAmount()))
                    .append(" (категория: ").append(op.getCategory()).append(")\n");
        }
        return sb.toString().trim();
    }

    /**
    * Отсортировываем список категорий доходов
    */
    private List<String> getIncomeCategoriesSorted() {
        List<String> sorted = new ArrayList<>(incomeCategories);
        Collections.sort(sorted);
        return sorted;
    }
    
    /**
    * Отсортировываем список категорий расходов
    */
    private List<String> getExpenseCategoriesSorted() {
        List<String> sorted = new ArrayList<>(expenseCategories);
        Collections.sort(sorted);
        return sorted;
    }

    /**
    * Собирает сообщение об ошибке с категориями
    */
    private String buildCategoryError(String category, String type, List<String> availableCategories) {
        StringBuilder sb = new StringBuilder();
        sb.append("Категории «").append(category).append("» нет.\n")
                .append("Доступные категории ").append(type).append(":\n");

        for (String cat : availableCategories) {
            sb.append("• ").append(cat).append("\n");
        }

        return sb.toString().trim();
    }

    /**
    * Получаем общую сумму доходов
    */
    private double getTotalIncome() {
        double total = 0;
        for (List<Operation> ops : incomes.values()) {
            for (Operation op : ops) {
                total += op.getAmount();
            }
        }
        return total;
    }

    /**
    * Получаем общую сумму доходов
    */
    private double getTotalExpense() {
        double total = 0;
        for (List<Operation> ops : expenses.values()) {
            for (Operation op : ops) {
                total += op.getAmount();
            }
        }
        return total;
    }

    /**
    * Получаем статистику категорий доходов
    */
    private Map<String, Double> getIncomeStatsByCategory() {
        Map<String, Double> stats = new HashMap<>();
        for (Map.Entry<String, List<Operation>> entry : incomes.entrySet()) {
            double sum = entry.getValue().stream()
                    .mapToDouble(Operation::getAmount)
                    .sum();
            stats.put(entry.getKey(), sum);
        }

        for (String category : incomeCategories) {
            stats.putIfAbsent(category, 0.0);
        }

        return stats;
    }

    /**
    * Получаем статистику категорий расходов
    */
    private Map<String, Double> getExpenseStatsByCategory() {
        Map<String, Double> stats = new HashMap<>();
        for (Map.Entry<String, List<Operation>> entry : expenses.entrySet()) {
            double sum = entry.getValue().stream()
                    .mapToDouble(Operation::getAmount)
                    .sum();
            stats.put(entry.getKey(), sum);
        }

        // Добавляем категории с нулевой суммой
        for (String category : expenseCategories) {
            stats.putIfAbsent(category, 0.0);
        }

        return stats;
    }
}
