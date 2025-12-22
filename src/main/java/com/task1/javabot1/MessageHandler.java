package com.task1.javabot1;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Основной обработчик сообщений.
 * Отвечает за парсинг пользовательских команд, маршрутизацию и форматирование ответов.
 */
public class MessageHandler {
    private final Map<String, UserData> userDataMap = new ConcurrentHashMap<>();

    /**
     * Приветственное сообщение, отправляемое пользователю при старте бота.
     * Содержит описание функционала и список доступных команд.
     */
    private final String START_MESSAGE = """ 
            – Добро пожаловать! Я ваш личный финансовый помощник.
            Я помогу вам отслеживать доходы, расходы и управлять бюджетом.
            
            /add_in — добавить доход
            /add_ex — добавить расход
            /balance — показать текущий баланс
            /income —  список доходов
            /expense — список расходов
            /delete_in — удалить запись доходов
            /delete_ex — удалить запись расходов
            /statistic — выводит сумму расходов, доходов и оставшийся бюджет
            /top_ex — выводит 3 самых больших расхода
            /top_in — выводит 3 самых больших дохода
            /sum_income — Показывает общий доход
            /sum_expense — Показывает общий расход
            /count_ops — показывает количество доходов, расходов и операций
            /help — помощь по командам
            """;

    /**
     * Справочное сообщение с подробным описанием работы бота.
     * Содержит примеры использования всех команд с ожидаемыми ответами.
     */
    private final String HELP_MESSAGE = """ 
                Справка по работе:
                Я ваш личный финансовый помощник.
                Мои команды:
                /add_in — добавить доход
                /add_ex — добавить расход
                /balance — показать текущий баланс
                /income —  список доходов
                /expense — список расходов
                /delete_in — удалить запись доходов
                /delete_ex — удалить запись расходов
                /statistic — выводит сумму расходов, доходов и оставшийся бюджет
                /top_ex — выводит 3 самых больших расхода
                /top_in — выводит 3 самых больших дохода
                /sum_income — Показывает общий доход
                /sum_expense — Показывает общий расход
                /count_ops — показывает количество доходов, расходов и операций
                /help — помощь по командам
            
                Например:
                /add_in 50000 Зарплата
                – Доход «Зарплата» на сумму 50000 добавлен.
            
                /add_in 25000 Премия
                – Доход «Премия» на сумму 25000 добавлен.
                
                /add_in 30000 Подарок
                – Доход «Подарок» на сумму 30000 добавлен.
            
                /add_ex 1500 Продукты
                – Расход «Продукты» на сумму 1500 добавлен.
            
                /income
                — Доход «Зарплата» на сумму 50000
                — Доход «Премия» на сумму 25000
                — Доход «Подарок» на сумму 30000
            
                /expense
                — Расход «Продукты» на сумму 1500
                
                /statistic
                📊 Статистика:
                — Сумма доходов: 105000,00
                — Сумма расходов: 1500,00
                — Оставшийся бюджет: 103500,00
            
                /top_ex
                📉 Топ-3 самых больших расходов:
                — «Продукты» на сумму 1500,00
            
                /top_in
                📈 Топ-3 самых больших доходов:
                — «Зарплата» на сумму 50000,00
                — «Подарок» на сумму 30000,00
                — «Премия» на сумму 25000,00
            
                /sum_income
                💰 Сумма доходов: 105000,00
            
                /sum_expense
                💸 Сумма расходов: 1500,00
            
                /count_ops
                Количество доходов: 3
                Количество расходов: 1
                Количество операций: 4
            
                /balance
                Текущий баланс: 103500,00
            
                /delete_ex 1500 Продукты
                Расход «Продукты» на сумму 1500.0 удален
            
            """;

    /**
     * Вспомогательный класс для хранения разобранных частей пользовательской команды.
     * Содержит команду и параметры, извлеченные из пользовательского ввода.
     */
    public class CommandParts{
        private final String command;
        private final String parameter_amount;
        private final String parameter_name;

        /**
         * Создает новый экземпляр CommandParts с указанными командой и параметром.
         *
         * @param command
         * @param parameter_amount
         * @param parameter_name
         */
        public CommandParts(String command, String parameter_amount, String parameter_name) {
            this.command = command;
            this.parameter_amount = parameter_amount;
            this.parameter_name = parameter_name;

        }

        /**
         * Возвращает команду пользователя.
         *
         * @return команда в виде строки
         */
        public String getCommand(){
            return command;
        }

        /**
         * Возвращает параметр команды - название операции.
         *
         * @return название операции виде строки
         */
        public String getParameterName(){
            return parameter_name;
        }

        /**
         * Возвращает параметр команды - сумма операции.
         *
         * @return сумма операции в виде строки
         */
        public String getParameterAmount(){
            return parameter_amount;
        }
    }

    /**
     * Парсит пользовательский ввод на команду и параметр.
     * Разделяет входную строку по первому пробелу, если он присутствует.
     *
     * @param userInput пользовательский ввод для парсинга
     * @return объект CommandParts с разобранной командой и параметрами
     */
    private CommandParts parseCommand(String userInput){
    if (userInput.isBlank()){
        return new CommandParts("", "", "");
    }

    String trimmedInput = userInput.trim();
    String[] parts = trimmedInput.split("\\s+", 3);

    String command = parts[0];
    String parameter_amount = "";
    String parameter_name = "";

    if (parts.length == 2) {
        parameter_name = parts[1].trim();
    } 
    else if (parts.length == 3) {
        parameter_amount = parts[1].trim();
        parameter_name = parts[2].trim();
    }

    return new CommandParts(command, parameter_amount, parameter_name);
}

    /**
     * Обрабатывает ввод пользователя и возвращает ответ.
     *
     * @param userInput текст сообщения от пользователя
     * @param userId идентификатор пользователя для логирования
     * @return текстовый ответ бота
     */
    public String processUserInput(String userInput, String userId) {
        System.out.printf("Пришло сообщение %s от %s%n", userInput, userId);
        UserData userData = userDataMap.computeIfAbsent(userId, k -> new UserData());

        CommandParts parts = parseCommand(userInput);
        String command = parts.getCommand();
        String parameter_amount = parts.getParameterAmount();
        String parameter_name = parts.getParameterName();

        return Response(command, parameter_amount, parameter_name, userData);

    }

    /**
     * Выбирает ответ, в зависимости от сообщения пользователя.
     * Маршрутизирует команды к соответствующим методам обработки.
     *
     * @param command   команда для выполнения
     * @param parameter_amount параметр команды - сумма операции
     * @param parameter_name параметр команды - название операции
     * @param userData  данные пользователя для операции
     * @return выбранный текстовый ответ
     */
    public String Response(String command, String parameter_amount, String parameter_name, UserData userData) {

        if ("/start".equals(command)) {
            return START_MESSAGE;
        }

        if ("/help".equals(command)) {
            return HELP_MESSAGE;
        }

        if ("/cat_in".equals(command)) {
            return userData.showIncomeCategories();
        }

        if ("/cat_ex".equals(command)) {
            return userData.showExpenseCategories();
        }

        if ("/add_cat_in".equals(command)) {
            if (parameter_name.isEmpty()) {
                return "Ошибка! Укажите название категории.\nПример: /add_cat_in инвестиции";
            }
            return userData.addIncomeCategory(parameter_name);
        }

        if ("/add_cat_ex".equals(command)) {
            if (parameter_name.isEmpty()) {
                return "Ошибка! Укажите название категории.\nПример: /add_cat_ex кафе";
            }
            return userData.addExpenseCategory(parameter_name);
        }

        if ("/del_cat_in".equals(command)) {
            if (parameter_name.isEmpty()) {
                return "Ошибка! Укажите название категории.\nПример: /del_cat_in инвестиции";
            }
            return userData.deleteIncomeCategory(parameter_name);
        }

        if ("/del_cat_ex".equals(command)) {
            if (parameter_name.isEmpty()) {
                return "Ошибка! Укажите название категории.\nПример: /del_cat_ex кафе";
            }
            return userData.deleteExpenseCategory(parameter_name);
        }

        if ("/add_in".equals(command)) {
            if (parameter_amount.isEmpty() || parameter_name.isEmpty()) {
                return "Ошибка! Укажите сумму, название и категорию.\n" +
                        "Пример: /add_in 50000 Зарплата работа\n" +
                        "Или с датой: /add_in 50000 Зарплата работа 15.12.2025";
            }

            try {
                double amount = Double.parseDouble(parameter_amount);
                String[] nameParts = parameter_name.split("\\s+");
                if (nameParts.length < 2) {
                    return "Ошибка! Укажите название и категорию.\n" +
                            "Пример: /add_in 50000 Зарплата работа";
                }
                String name;
                String category;
                String date = null;

                int lastIndex = nameParts.length - 1;
                String lastPart = nameParts[lastIndex];
                if (lastPart.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                    date = lastPart;
                    lastIndex--;
                }

                category = nameParts[lastIndex];
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = 0; i < lastIndex; i++) {
                    if (i > 0) nameBuilder.append(" ");
                    nameBuilder.append(nameParts[i]);
                }
                name = nameBuilder.toString().trim();
                if (name.isEmpty()) {
                    return "Ошибка! Укажите название операции.";
                }

                return userData.addIncome(name, amount, category, date);
            } catch (NumberFormatException e) {
                return "Некорректная сумма: " + parameter_amount;
            }
        }

        if ("/add_ex".equals(command)) {
            if (parameter_amount.isEmpty() || parameter_name.isEmpty()) {
                return "Ошибка! Укажите сумму, название и категорию.\n" +
                        "Пример: /add_ex 1500 Продукты еда\n" +
                        "Или с датой: /add_ex 1500 Продукты еда 15.12.2025";
            }

            try {
                double amount = Double.parseDouble(parameter_amount);
                String[] nameParts = parameter_name.split("\\s+");
                if (nameParts.length < 2) {
                    return "Ошибка! Укажите название и категорию.\n" +
                            "Пример: /add_ex 1500 Продукты еда";
                }
                String name;
                String category;
                String date = null;

                int lastIndex = nameParts.length - 1;
                String lastPart = nameParts[lastIndex];
                if (lastPart.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                    date = lastPart;
                    lastIndex--;
                }

                category = nameParts[lastIndex];
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = 0; i < lastIndex; i++) {
                    if (i > 0) nameBuilder.append(" ");
                    nameBuilder.append(nameParts[i]);
                }
                name = nameBuilder.toString().trim();
                if (name.isEmpty()) {
                    return "Ошибка! Укажите название операции.";
                }

                return userData.addExpense(name, amount, category, date);
            } catch (NumberFormatException e) {
                return "Некорректная сумма: " + parameter_amount;
            }
        }

        if ("/income".equals(command)) {
            return userData.showIncomes();
        }

        if ("/expense".equals(command)) {
            return userData.showExpenses();
        }

        if ("/statistic".equals(command)) {
            String period = parameter_name.trim().toLowerCase();
            List<String> validPeriods = Arrays.asList("", "today", "week", "month", "year");
            if (!validPeriods.contains(period)) {
                return "Некорректный период.\n" +
                        "Используйте:\n" +
                        " /statistic - за текущий месяц\n" +
                        " /statistic today - за сегодня\n" +
                        " /statistic week - за текущую неделю\n" +
                        " /statistic month - за текущий месяц\n" +
                        " /statistic year - за текущий год";
            }

            return userData.getStatistics(period);
        }

        if ("/delete_in".equals(command)) {
            if (parameter_amount.isEmpty() || parameter_name.isEmpty()) {
                return "Ошибка! Укажите сумму и название:\n/delete_in 25000 Премия";
            }
            try {
                double amount = Double.parseDouble(parameter_amount);
                return userData.deleteIncome(parameter_name, amount);
            } catch (NumberFormatException e) {
                return "Некорректная сумма: " + parameter_amount;
            }
        }

        if ("/delete_ex".equals(command)) {
            if (parameter_amount.isEmpty() || parameter_name.isEmpty()) {
                return "Ошибка! Укажите сумму и название:\n/delete_ex 1500 Продукты";
            }
            try {
                double amount = Double.parseDouble(parameter_amount);
                return userData.deleteExpense(parameter_name, amount);
            } catch (NumberFormatException e) {
                return "Некорректная сумма: " + parameter_amount;
            }
        }

        if ("/balance".equals(command)) {
            List<Operation> incomes = userData.getAllIncomes();
            List<Operation> expenses = userData.getAllExpenses();

            double incomeSum = incomes.stream()
                    .mapToDouble(Operation::getAmount)
                    .sum();
            double expenseSum = expenses.stream()
                    .mapToDouble(Operation::getAmount)
                    .sum();
            double balance = incomeSum - expenseSum;

            return "Текущий баланс: " + String.format("%,.2f", balance);
        }
        if ("/top_ex".equals(command)) {
            List<Operation> allExpenses = userData.getAllExpenses();
            if (allExpenses.isEmpty()) {
                return "— Расходов пока нет";
            }

            allExpenses.sort((a, b) -> Double.compare(b.getAmount(), a.getAmount()));

            StringBuilder top_three_ex = new StringBuilder("📉 Топ-3 самых больших расходов:\n");
            int count = Math.min(3, allExpenses.size());
            for (int i = 0; i < count; i++) {
                Operation expense = allExpenses.get(i);
                top_three_ex.append(String.format("— «%s» на сумму %,.2f (категория: %s)\n",
                        expense.getName(), expense.getAmount(), expense.getCategory()));
            }

            return top_three_ex.toString().trim();
        }

        if ("/top_in".equals(command)) {
            List<Operation> allIncomes = userData.getAllIncomes();
            if (allIncomes.isEmpty()) {
                return "— Доходов пока нет";
            }

            allIncomes.sort((a, b) -> Double.compare(b.getAmount(), a.getAmount()));

            StringBuilder sb = new StringBuilder("📈 Топ-3 самых больших доходов:\n");
            int count = Math.min(3, allIncomes.size());
            for (int i = 0; i < count; i++) {
                Operation income = allIncomes.get(i);
                sb.append(String.format("— «%s» на сумму %,.2f (категория: %s)\n",
                        income.getName(), income.getAmount(), income.getCategory()));
            }

            return sb.toString().trim();
        }

        if ("/sum_income".equals(command)) {
            List<Operation> incomes = userData.getAllIncomes();
            double incomeSum = incomes.stream()
                    .mapToDouble(Operation::getAmount)
                    .sum();

            return "💰 Сумма доходов: " + String.format("%,.2f", incomeSum);
        }

        if ("/sum_expense".equals(command)) {
            List<Operation> expenses = userData.getAllExpenses();
            double expenseSum = expenses.stream()
                    .mapToDouble(Operation::getAmount)
                    .sum();

            return "💸 Сумма расходов: " + String.format("%,.2f", expenseSum);
        }

        if ("/count_ops".equals(command)) {
            List<Operation> incomes = userData.getAllIncomes();
            List<Operation> expenses = userData.getAllExpenses();

            int incomeCount = incomes.size();
            int expenseCount = expenses.size();
            int totalOps = incomeCount + expenseCount;

            StringBuilder sb = new StringBuilder();
            sb.append("📊 Количество операций:\n")
                    .append("➕ Доходы: ").append(incomeCount).append("\n")
                    .append("➖ Расходы: ").append(expenseCount).append("\n")
                    .append(" Всего операций: ").append(totalOps);
            return sb.toString().trim();
        }

        return "Неизвестная команда.\nВведите /help для просмотра доступных команд.";
    }
}
