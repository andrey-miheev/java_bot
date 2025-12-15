package com.task1.javabot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для обработки финансовых команд в классе MessageHandler.
 * Проверяются основные команды: /start, /help, /add_in, /add_ex,
 * /income, /expense, /delete_in, /balance, /statistic, /top_ex, /top_in,
 * /sum_income, /sum_expense, /count_ops
 *
 * @see MessageHandler
 */
class MessageHandlerFinanceTests {

    private MessageHandler messageHandler;
    private UserData userData;
    private UserData userData2;

    /**
     * Инициализация тестового окружения перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        messageHandler = new MessageHandler();
        userData = new UserData();
        userData2 = new UserData();
    }

    /**
     * Тест команды /add_in с пустыми параметрами.
     */
    @Test
    void testAddIncomeMissingParams() {
        String result = messageHandler.Response("/add_in", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название. Пример:\n/add_in 50000 Зарплата";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /add_in с некорректной суммой.
     */
    @Test
    void testAddIncomeInvalidAmount() {
        String result = messageHandler.Response("/add_in", "abc", "Зарплата", userData);
        String expected = "Некорректная сумма: abc";
        Assertions.assertEquals(expected, result);
    }

    /**
     * /add_in — успешное добавление
     */
    @Test
    void testAddIncomeSuccess() {
        String result = messageHandler.Response("/add_in", "50000", "Зарплата", userData);

        String expected = "Доход «Зарплата» на сумму %,.2f добавлен."
                .formatted(50000.0);
        Assertions.assertEquals(expected, result);
        String result_add = messageHandler.Response("/income", "", "", userData);
        Double amount_test = 50000.00;

        String expected_add = String.format("""
                Ваши доходы:
                — Доход «Зарплата» на сумму %,.2f
                """, amount_test);

        Assertions.assertEquals(expected_add, result_add);

    }

    /**
     * Тест команды /add_ex с пустыми параметрами.
     */
    @Test
    void testAddExpenseMissingParams() {
        String result = messageHandler.Response("/add_ex", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название. Пример:\n/add_ex 1500 Продукты";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /add_ex с некорректной суммой.
     */
    @Test
    void testAddExpenseInvalidAmount() {
        String result = messageHandler.Response("/add_ex", "xyz", "Продукты", userData);
        String expected = "Некорректная сумма: xyz";
        Assertions.assertEquals(expected, result);
    }

    /**
     * /add_ex — успешное добавление
     */
    @Test
    void testAddExpenseSuccess() {
        String result = messageHandler.Response("/add_ex", "1500", "Продукты", userData);

        String expected = "Расход «Продукты» на сумму %,.2f добавлен."
                .formatted(1500.0);

        Assertions.assertEquals(expected, result);
        String result_add = messageHandler.Response("/expense", "", "", userData);
        Double amount_test = 1500.00;

        String expected_add = String.format("""
                Ваши расходы:
                — Расход «Продукты» на сумму %,.2f
                """, amount_test);

        Assertions.assertEquals(expected_add, result_add);
    }

    /**
     * Тест команды /income при отсутствии доходов.
     */
    @Test
    void testShowEmptyIncomes() {
        String result = messageHandler.Response("/income", "", "", userData);
        String expected = "— Доходов пока нет";
        Assertions.assertEquals(expected, result);
    }

    /**
     * /income — есть доходы
     */
    @Test
    void testShowIncomes() {
        messageHandler.Response("/add_in", "50000", "Зарплата", userData);
        messageHandler.Response("/add_in", "10000", "Зарплата", userData);

        String result = messageHandler.Response("/income", "", "", userData);
        Double amount1_test = 50000.00;
        Double amount2_test = 10000.00;
        String expected = String.format("""
                Ваши доходы:
                — Доход «Зарплата» на сумму %,.2f
                — Доход «Зарплата» на сумму %,.2f
                """, amount1_test, amount2_test);

        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /expense при отсутствии расходов.
     */
    @Test
    void testShowEmptyExpenses() {
        String result = messageHandler.Response("/expense", "", "", userData);
        String expected = "— Расходов пока нет";
        Assertions.assertEquals(expected, result);
    }

    /**
     * /expense — есть расходы
     */
    @Test
    void testShowExpenses() {
        messageHandler.Response("/add_ex", "1500", "Продукты", userData);
        messageHandler.Response("/add_ex", "300", "Продукты", userData);

        String result = messageHandler.Response("/expense", "", "", userData);
        Double amount1_test = 1500.00;
        Double amount2_test = 300.00;

        String expected = String.format("""
                Ваши расходы:
                — Расход «Продукты» на сумму %,.2f
                — Расход «Продукты» на сумму %,.2f
                """, amount1_test, amount2_test);

        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /delete_in с пустыми параметрами.
     */
    @Test
    void testDeleteIncomeMissingParams() {
        String result = messageHandler.Response("/delete_in", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название:\n/delete_in 25000 Премия";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /delete_in с некорректной суммой.
     */
    @Test
    void testDeleteIncomeInvalidAmount() {
        String result = messageHandler.Response("/delete_in", "abc", "Премия", userData);
        String expected = "Некорректная сумма: abc";
        Assertions.assertEquals(expected, result);
    }

    /**
     * /delete_in — успешное удаление
     */
    @Test
    void testDeleteIncomeSuccess() {
        messageHandler.Response("/add_in", "25000", "Премия", userData);

        String result_add = messageHandler.Response("/income", "", "", userData);
        Double amount1_test = 25000.00;
        String expected_add = String.format("""
                Ваши доходы:
                — Доход «Премия» на сумму %,.2f
                """, amount1_test);
        Assertions.assertEquals(expected_add, result_add);

        String result = messageHandler.Response("/delete_in", "25000", "Премия", userData);
        Assertions.assertEquals(
                "Доход «Премия» на сумму 25000.0 удален.",
                result
        );

        String result_del = messageHandler.Response("/income", "", "", userData);
        String expected_del = "— Доходов пока нет";
        Assertions.assertEquals(expected_del, result_del);
    }

    /**
     * /delete_in — сумма не найдена
     */
    @Test
    void testDeleteIncomeAmountNotFound() {
        messageHandler.Response("/add_in", "5000", "Бонус", userData);

        String result_add = messageHandler.Response("/income", "", "", userData);
        Double amount1_test = 5000.00;
        String expected_add = String.format("""
                Ваши доходы:
                — Доход «Бонус» на сумму %,.2f
                """, amount1_test);
        Assertions.assertEquals(expected_add, result_add);

        String result = messageHandler.Response("/delete_in", "1000", "Бонус", userData);
        Assertions.assertEquals(
                "Сумма 1000.0 не найдена в доходе «Бонус»",
                result
        );
    }
    /**
     * Тест команды /delete_ex с пустыми параметрами.
     */
    @Test
    void testDeleteExpenseMissingParams() {
        String result = messageHandler.Response("/delete_ex", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название:\n/delete_ex 1500 Продукты";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /delete_ex с некорректной суммой.
     */
    @Test
    void testDeleteExpenseInvalidAmount() {
        String result = messageHandler.Response("/delete_ex", "abc", "Продукты", userData);
        String expected = "Некорректная сумма: abc";
        Assertions.assertEquals(expected, result);
    }

    /**
     * /delete_ex — успешное удаление
     */
    @Test
    void testDeleteExpenseSuccess() {
        messageHandler.Response("/add_ex", "1500", "Продукты", userData);

        String result_add = messageHandler.Response("/expense", "", "", userData);
        Double amount1_test = 1500.00;
        String expected_add = String.format("""
                Ваши расходы:
                — Расход «Продукты» на сумму %,.2f
                """, amount1_test);
        Assertions.assertEquals(expected_add, result_add);

        String result = messageHandler.Response("/delete_ex", "1500", "Продукты", userData);
        Assertions.assertEquals(
                "Расход «Продукты» на сумму 1500.0 удален.",
                result
        );

        String result_del = messageHandler.Response("/expense", "", "", userData);
        String expected_del = "— Расходов пока нет";
        Assertions.assertEquals(expected_del, result_del);
    }

    /**
     * /delete_ex — сумма не найдена
     */
    @Test
    void testDeleteExpenseAmountNotFound() {
        messageHandler.Response("/add_ex", "5000", "Продукты", userData);

        String result_add = messageHandler.Response("/expense", "", "", userData);
        Double amount1_test = 5000.00;
        String expected_add = String.format("""
                Ваши расходы:
                — Расход «Продукты» на сумму %,.2f
                """, amount1_test);
        Assertions.assertEquals(expected_add, result_add);

        String result = messageHandler.Response("/delete_ex", "1000", "Продукты", userData);
        Assertions.assertEquals(
                "Сумма 1000.0 не найдена в расходе «Продукты»",
                result
        );
    }

    /**
     * Тест команды /balance при отсутствии операций.
     */
    @Test
    void testBalanceEmpty() {
        String result = messageHandler.Response("/balance", "", "", userData);
        String expected = String.format("Текущий баланс: %,.2f", 0.00);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /balance с доходами и расходами.
     */
    @Test
    void testBalanceWithData() {
        messageHandler.Response("/add_in", "50000", "Зарплата", userData);
        messageHandler.Response("/add_in", "15000", "Премия", userData);

        messageHandler.Response("/add_ex", "15000", "Аренда", userData);
        messageHandler.Response("/add_ex", "5000", "Продукты", userData);

        String result = messageHandler.Response("/balance", "", "", userData);
        String expected = String.format("Текущий баланс: %,.2f", 45000.00);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /statistic при отсутствии операций.
     */
    @Test
    void testStatisticEmpty() {
        String result = messageHandler.Response("/statistic", "", "", userData);
        String expected = String.format("📊 Статистика:\n" +
                "— Сумма доходов: %,.2f\n" +
                "— Сумма расходов: %,.2f\n" +
                "— Оставшийся бюджет: %,.2f", 0.00, 0.00, 0.00);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /statistic с доходами и расходами.
     */
    @Test
    void testStatisticWithData() {
        messageHandler.Response("/add_in", "50000", "Зарплата", userData);
        messageHandler.Response("/add_in", "15000", "Премия", userData);

        messageHandler.Response("/add_ex", "15000", "Аренда", userData);
        messageHandler.Response("/add_ex", "5000", "Продукты", userData);

        String result = messageHandler.Response("/statistic", "", "", userData);
        String expected = String.format("📊 Статистика:\n" +
                "— Сумма доходов: %,.2f\n" +
                "— Сумма расходов: %,.2f\n" +
                "— Оставшийся бюджет: %,.2f", 65000.0, 20000.0, 45000.0);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /top_ex при отсутствии расходов.
     */
    @Test
    void testTopExpensesEmpty() {
        String result = messageHandler.Response("/top_ex", "", "", userData);
        String expected = "— Расходов пока нет";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /top_ex с несколькими расходами.
     */
    @Test
    void testTopExpensesWithData() {
        messageHandler.Response("/add_ex", "1000", "Кофе", userData);
        messageHandler.Response("/add_ex", "50000", "Аренда", userData);
        messageHandler.Response("/add_ex", "15000", "Продукты", userData);
        messageHandler.Response("/add_ex", "2000", "Транспорт", userData);
        messageHandler.Response("/add_ex", "30000", "Кредит", userData);

        String result = messageHandler.Response("/top_ex", "", "", userData);
        String expected = String.format("📉 Топ-3 самых больших расходов:\n" +
                "— «Аренда» на сумму %,.2f\n" +
                "— «Кредит» на сумму %,.2f\n" +
                "— «Продукты» на сумму %,.2f\n", 50000.0, 30000.0, 15000.0);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /top_in при отсутствии расходов.
     */
    @Test
    void testTopIncomesEmpty() {
        String result = messageHandler.Response("/top_in", "", "", userData);
        String expected = "— Доходов пока нет";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /top_in с несколькими расходами.
     */
    @Test
    void testTopIncomesWithData() {
        messageHandler.Response("/add_in", "1000", "Кофе", userData);
        messageHandler.Response("/add_in", "50000", "Аренда", userData);
        messageHandler.Response("/add_in", "15000", "Продукты", userData);
        messageHandler.Response("/add_in", "2000", "Транспорт", userData);
        messageHandler.Response("/add_in", "30000", "Кредит", userData);

        String result = messageHandler.Response("/top_in", "", "", userData);
        String expected = String.format("📈 Топ-3 самых больших доходов:\n" +
                "— «Аренда» на сумму %,.2f\n" +
                "— «Кредит» на сумму %,.2f\n" +
                "— «Продукты» на сумму %,.2f\n", 50000.0, 30000.0, 15000.0);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /sum_income при отсутствии доходов.
     */
    @Test
    void testSumIncomeEmpty() {
        String result = messageHandler.Response("/sum_income", "", "", userData);
        String expected = String.format("💰 Сумма доходов: %,.2f", 0.00);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /sum_income с доходами.
     */
    @Test
    void testSumIncomeWithData() {
        messageHandler.Response("/add_in", "50000", "Зарплата", userData);
        messageHandler.Response("/add_in", "15000", "Премия", userData);
        messageHandler.Response("/add_in", "10000", "Фриланс", userData);

        String result = messageHandler.Response("/sum_income", "", "", userData);
        String expected = String.format("💰 Сумма доходов: %,.2f", 75000.00);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /sum_expense при отсутствии доходов.
     */
    @Test
    void testSumExpenseEmpty() {
        String result = messageHandler.Response("/sum_expense", "", "", userData);
        String expected = String.format("💸 Сумма расходов: %,.2f", 0.00);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /sum_expense с доходами.
     */
    @Test
    void testSumExpenseWithData() {
        messageHandler.Response("/add_ex", "50000", "Зарплата", userData);
        messageHandler.Response("/add_ex", "15000", "Премия", userData);
        messageHandler.Response("/add_ex", "10000", "Фриланс", userData);

        String result = messageHandler.Response("/sum_expense", "", "", userData);
        String expected = String.format("💸 Сумма расходов: %,.2f", 75000.00);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /count_ops при отсутствии операций.
     */
    @Test
    void testCountOpsEmpty() {
        String result = messageHandler.Response("/count_ops", "", "", userData);
        String expected = String.format("Количество доходов: %d\n" +
                "Количество расходов: %d\n" +
                "Количество операций: %d", 0,0,0);
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /count_ops с доходами и расходами.
     */
    @Test
    void testCountOpsWithData() {
        messageHandler.Response("/add_in", "50000", "Зарплата", userData);
        messageHandler.Response("/add_in", "15000", "Премия", userData);
        messageHandler.Response("/add_in", "10000", "Премия", userData);

        messageHandler.Response("/add_ex", "30000", "Аренда", userData);
        messageHandler.Response("/add_ex", "15000", "Продукты", userData);
        messageHandler.Response("/add_ex", "5000", "Продукты", userData);
        messageHandler.Response("/add_ex", "2000", "Транспорт", userData);

        String result = messageHandler.Response("/count_ops", "", "", userData);
        String expected = String.format("Количество доходов: %d\n" +
                "Количество расходов: %d\n" +
                "Количество операций: %d", 3,4,7);;
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест команды /add_cat_in с пустым названием категории.
     */
    @Test
    void testAddIncomeCategoryMissingName() {
        String result = messageHandler.Response("/add_cat_in", "", "", userData);
        String expected = "Ошибка! Укажите название категории.\nПример: /add_cat_in инвестиции";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест ввода неизвестной команды
     */
    @Test
    void testUnknownCommand(){
        String result = messageHandler.Response("/abcd", "", "", userData);
        String expected = "Неизвестная команда.\nВведите /help для просмотра доступных команд.";
        Assertions.assertEquals(expected, result);
    }

    /**
     * Тест на независимость данных для разных пользователей
     */
    @Test
    void testDifferentUserData(){
        messageHandler.Response("/add_in", "50000", "Зарплата", userData);
        messageHandler.Response("/add_in", "15000", "Премия", userData);
        messageHandler.Response("/add_in", "10000", "Премия", userData2);

        messageHandler.Response("/add_ex", "30000", "Аренда", userData);
        messageHandler.Response("/add_ex", "15000", "Продукты", userData);
        messageHandler.Response("/add_ex", "5000", "Продукты", userData2);
        messageHandler.Response("/add_ex", "2000", "Транспорт", userData2);

        String result1 = messageHandler.Response("/statistic", "", "", userData);
        String expected1 = String.format("📊 Статистика:\n" +
                "— Сумма доходов: %,.2f\n" +
                "— Сумма расходов: %,.2f\n" +
                "— Оставшийся бюджет: %,.2f", 65000.0, 45000.0, 20000.0);
        Assertions.assertEquals(expected1, result1);

        String result2 = messageHandler.Response("/statistic", "", "", userData2);
        String expected2 = String.format("📊 Статистика:\n" +
                "— Сумма доходов: %,.2f\n" +
                "— Сумма расходов: %,.2f\n" +
                "— Оставшийся бюджет: %,.2f", 10000.0, 7000.0, 3000.0);
        Assertions.assertEquals(expected2, result2);
    }
}
