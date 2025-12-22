package com.task1.javabot1;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Умный менеджер данных пользователя.
 * Инкапсулирует логику работы с задачами
 */
public class UserData {
    private final Map<String, List<Operation>> incomes = new HashMap<>();
    private final Map<String, List<Operation>> expenses = new HashMap<>();

    private final Set<String> incomeCategories = new HashSet<>();
    private final Set<String> expenseCategories = new HashSet<>();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
     * Формат: /add_in сумма название категория дата
     * Если дата не указана - используется текущая
     */
    public String addIncome(String name, Double amount, String category, String dateStr) {
        String trimmedName = name.trim();
        String trimmedCategory = category.trim();

        if (!incomeCategories.contains(trimmedCategory)) {
            return buildCategoryError(trimmedCategory, "доходов", getIncomeCategoriesSorted());
        }

        LocalDate operationDate;

        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                // Пытаемся распарсить дату
                operationDate = LocalDate.parse(dateStr.trim(), dateFormatter);
            } catch (DateTimeParseException e) {
                operationDate = LocalDate.now();
            }
        } else {
            // Дата не указана - используем текущую
            operationDate = LocalDate.now();
        }

        Operation operation = new Operation(trimmedName, amount, trimmedCategory, operationDate);

        incomes.computeIfAbsent(trimmedCategory, k -> new ArrayList<>()).add(operation);

        return "– Доход «" + trimmedName + "» на сумму " +
                String.format("%,.2f", amount) + " добавлен.\n" +
                "Категория: " + trimmedCategory + "\n"+
                "Дата: " + operation.getFormattedDate();
    }

    /**
     * Добавляет операцию расхода с категорией
     * Формат: /add_ex сумма название категория дата
     * Если дата не указана - используется текущая
     */
    public String addExpense(String name, Double amount, String category, String dateStr) {
        String trimmedName = name.trim();
        String trimmedCategory = category.trim();

        if (!expenseCategories.contains(trimmedCategory)) {
            return buildCategoryError(trimmedCategory, "расходов", getExpenseCategoriesSorted());
        }

        LocalDate operationDate;

        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                // Пытаемся распарсить дату из строки
                operationDate = LocalDate.parse(dateStr.trim(), dateFormatter);
            } catch (DateTimeParseException e) {
                operationDate = LocalDate.now();
            }
        } else {
            // Дата не указана - используем текущую
            operationDate = LocalDate.now();
        }

        Operation operation = new Operation(trimmedName, amount, trimmedCategory, operationDate);

        expenses.computeIfAbsent(trimmedCategory, k -> new ArrayList<>()).add(operation);

        return "– Расход «" + trimmedName + "» на сумму " +
                String.format("%,.2f", amount) + " добавлен.\n" +
                "Категория: " + trimmedCategory + "\n" +
                "Дата: " + operation.getFormattedDate();
    }

    /**
     * Удаляет операцию дохода
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
     * Удаляет операцию расхода
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
     * Возвращает статистику за указанный период
     * period: "today", "week", "month", "year" или текущий месяц
     */
    public String getStatistics(String period) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;
        String periodTitle = switch (period.toLowerCase()) {
            case "today" -> {
                startDate = now;
                yield "сегодня (" + now.format(dateFormatter) + ")";
            }
            case "week" -> {
                startDate = now.minusDays(6); // последние 7 дней
                yield "текущую неделю (" + startDate.format(dateFormatter) +
                        " - " + now.format(dateFormatter) + ")";
            }
            case "month" -> {
                startDate = now.with(TemporalAdjusters.firstDayOfMonth());
                yield "текущий месяц (" + now.getMonth().toString().toLowerCase() +
                        " " + now.getYear() + ")";
            }
            case "year" -> {
                startDate = LocalDate.of(now.getYear(), 1, 1);
                yield "текущий год (" + now.getYear() + ")";
            }
            default -> {
                // По умолчанию - текущий месяц
                startDate = now.with(TemporalAdjusters.firstDayOfMonth());
                yield "текущий месяц";
            }
        };

        List<Operation> filteredIncomes = getAllIncomes().stream()
                .filter(op -> !op.getDate().isBefore(startDate) && !op.getDate().isAfter(now))
                .collect(Collectors.toList());

        List<Operation> filteredExpenses = getAllExpenses().stream()
                .filter(op -> !op.getDate().isBefore(startDate) && !op.getDate().isAfter(now))
                .collect(Collectors.toList());

        double totalIncome = filteredIncomes.stream()
                .mapToDouble(Operation::getAmount)
                .sum();
        double totalExpense = filteredExpenses.stream()
                .mapToDouble(Operation::getAmount)
                .sum();
        double balance = totalIncome - totalExpense;

        Map<String, Double> incomeStats = getStatsByCategory(filteredIncomes);
        Map<String, Double> expenseStats = getStatsByCategory(filteredExpenses);
        addEmptyCategories(incomeStats, expenseStats);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Статистика за %s: \n", periodTitle))
                .append(String.format("Сумма доходов: %,.2f \n", totalIncome))
                .append(String.format("Сумма расходов: %,.2f \n", totalExpense))
                .append(String.format("Оставшийся бюджет: %,.2f\n", balance))
                .append("Статистика по категориям:\n\n");

        sb.append("Доходы:\n");
        incomeStats.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .forEach(entry -> sb.append(String.format("• %s: %,.2f\n", entry.getKey(), entry.getValue())));

        sb.append("\nРасходы:\n");
        expenseStats.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .forEach(entry -> sb.append(String.format("• %s: %,.2f\n", entry.getKey(), entry.getValue())));

        return sb.toString().trim();
    }

    /**
     * Показывает доходы с датой
     */
    public String showIncomes() {
        List<Operation> allIncomes = getAllIncomes();
        if (allIncomes.isEmpty()) {
            return "— Доходов пока нет";
        }

        allIncomes.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        StringBuilder sb = new StringBuilder();
        for (Operation op : allIncomes) {
            sb.append("— Доход «").append(op.getName())
                    .append("» на сумму ").append(String.format("%,.2f", op.getAmount()))
                    .append(" (категория: ").append(op.getCategory()).append(")")
                    .append(" Дата: ").append(op.getFormattedDate()).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Показывает расходы с датой
     */
    public String showExpenses() {
        List<Operation> allExpenses = getAllExpenses();
        if (allExpenses.isEmpty()) {
            return "— Расходов пока нет";
        }

        allExpenses.sort((a, b) -> b.getDate().compareTo(a.getDate()));

        StringBuilder sb = new StringBuilder();
        for (Operation op : allExpenses) {
            sb.append("— Расход «").append(op.getName())
                    .append("» на сумму ").append(String.format("%,.2f", op.getAmount()))
                    .append(" (категория: ").append(op.getCategory()).append(")")
                    .append(" Дата: ").append(op.getFormattedDate()).append("\n");
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
    * Получаем общую сумму расходов
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
    private Map<String, Double> getStatsByCategory(List<Operation> operations) {
        Map<String, Double> stats = new HashMap<>();
        for (Operation op : operations) {
            stats.put(op.getCategory(), stats.getOrDefault(op.getCategory(), 0.0) + op.getAmount());
        }
        return stats;
    }

    /**
     * Добавляет категории с нулевой суммой в статистику
     */
    private void addEmptyCategories(Map<String, Double> incomeStats, Map<String, Double> expenseStats) {
        // Добавляем все категории доходов (но только если они существуют)
        for (String category : getIncomeCategoriesSorted()) {
            incomeStats.putIfAbsent(category, 0.0);
        }

        // Добавляем все категории расходов (но только если они существуют)
        for (String category : getExpenseCategoriesSorted()) {
            expenseStats.putIfAbsent(category, 0.0);
        }
    }
}
