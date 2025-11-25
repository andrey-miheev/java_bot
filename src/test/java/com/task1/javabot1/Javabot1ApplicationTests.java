package com.task1.javabot1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/
 * Тесты для обработки финансовых команд в классе MessageHandler.
 * Проверяются основные команды: /start, /help, /add_in, /add_ex,
 * /income, /expense, /delete_in.
 *
 * @see MessageHandler
 */
class MessageHandlerFinanceTests {

    private MessageHandler messageHandler;
    private UserData userData;

    /
     * Инициализация тестового окружения перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        messageHandler = new MessageHandler();
        userData = new UserData();
    }

    /
     * Тест команды /add_in с пустыми параметрами.
     */
    @Test
    void testAddIncomeMissingParams() {
        String result = messageHandler.Response("/add_in", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название. Пример:\n/add_in 50000 Зарплата";
        Assertions.assertEquals(expected, result);
    }

    /
     * Тест команды /add_in с некорректной суммой.
     */
    @Test
    void testAddIncomeInvalidAmount() {
        String result = messageHandler.Response("/add_in", "abc", "Зарплата", userData);
        String expected = "Некорректная сумма: abc";
        Assertions.assertEquals(expected, result);
    }

    /
     * /add_in — успешное добавление
     */
    @Test
    void testAddIncomeSuccess() {
        String result = messageHandler.Response("/add_in", "50000", "Зарплата", userData);

        String expected = "Доход %s на сумму %,.2f добавлен.%n."
                .formatted("Зарплата", 50000.0);
        Assertions.assertEquals(expected, result);
        String result_add = messageHandler.Response("/income", "", "", userData);
        Double amount_test = 50000.00;

        String expected_add = String.format("""
                Ваши доходы:
                — Доход «Зарплата» на сумму %,.2f
                """, amount_test);

        Assertions.assertEquals(expected_add, result_add);

    }

    /
     * Тест команды /add_ex с пустыми параметрами.
     */
    @Test
    void testAddExpenseMissingParams() {
        String result = messageHandler.Response("/add_ex", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название. Пример:\n/add_ex 1500 Продукты";
        Assertions.assertEquals(expected, result);
    }

    /
     * Тест команды /add_ex с некорректной суммой.
     */
    @Test
    void testAddExpenseInvalidAmount() {
        String result = messageHandler.Response("/add_ex", "xyz", "Продукты", userData);
        String expected = "Некорректная сумма: xyz";
        Assertions.assertEquals(expected, result);
    }

    /
     * /add_ex — успешное добавление
     */
    @Test
    void testAddExpenseSuccess() {
        String result = messageHandler.Response("/add_ex", "1500", "Продукты", userData);

        String expected = "Расход %s на сумму %,.2f добавлен.%n."
                .formatted("Продукты", 1500.0);

        Assertions.assertEquals(expected, result);
        String result_add = messageHandler.Response("/expense", "", "", userData);
        Double amount_test = 1500.00;

        String expected_add = String.format("""
                Ваши расходы:
                — Расход «Продукты» на сумму %,.2f
                """, amount_test);

        Assertions.assertEquals(expected_add, result_add);
    }

    /
     * Тест команды /income при отсутствии доходов.
     */
    @Test
    void testShowEmptyIncomes() {
        String result = messageHandler.Response("/income", "", "", userData);
        String expected = "— Доходов пока нет";
        Assertions.assertEquals(expected, result);
    }

    /
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

    /
     * Тест команды /expense при отсутствии расходов.
     */
    @Test
    void testShowEmptyExpenses() {
        String result = messageHandler.Response("/expense", "", "", userData);
        String expected = "— Расходов пока нет";
        Assertions.assertEquals(expected, result);
    }

    /
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

    /
     * Тест команды /delete_in с пустыми параметрами.
     */
    @Test
    void testDeleteIncomeMissingParams() {
        String result = messageHandler.Response("/delete_in", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название:\n/delete_in 25000 Премия";
        Assertions.assertEquals(expected, result);
    }

    /
     * Тест команды /delete_in с некорректной суммой.
     */
    @Test
    void testDeleteIncomeInvalidAmount() {
        String result = messageHandler.Response("/delete_in", "abc", "Премия", userData);
        String expected = "Некорректная сумма: abc";
        Assertions.assertEquals(expected, result);
    }

    /
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
                "Доход 'Премия' на сумму 25000.0 удален.",
                result
        );

        String result_del = messageHandler.Response("/income", "", "", userData);
        String expected_del = "— Доходов пока нет";
        Assertions.assertEquals(expected_del, result_del);
    }

    /
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
                "Сумма 1000.0 не найдена в доходе 'Бонус'",
                result
        );
    }
    /
     * Тест команды /delete_ex с пустыми параметрами.
     */
    @Test
    void testDeleteExpenseMissingParams() {
        String result = messageHandler.Response("/delete_ex", "", "", userData);
        String expected = "Ошибка! Укажите сумму и название:\n/delete_ex 1500 Продукты";
        Assertions.assertEquals(expected, result);
    }

    /
     * Тест команды /delete_ex с некорректной суммой.
     */
    @Test
    void testDeleteExpenseInvalidAmount() {
        String result = messageHandler.Response("/delete_ex", "abc", "Продукты", userData);
        String expected = "Некорректная сумма: abc";
        Assertions.assertEquals(expected, result);
    }

    /
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
                "Расход 'Продукты' на сумму 1500.0 удален.",
                result
        );

        String result_del = messageHandler.Response("/expense", "", "", userData);
        String expected_del = "— Расходов пока нет";
        Assertions.assertEquals(expected_del, result_del);
    }

    /
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
                "Сумма 1000.0 не найдена в расходе 'Продукты'",
                result
        );
    }
}
