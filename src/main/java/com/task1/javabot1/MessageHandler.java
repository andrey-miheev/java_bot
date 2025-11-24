package com.task1.javabot1;

import java.util.HashMap;
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
                /help — помощь по командам
            
                Например:
                /add_in 50000 Зарплата
                – Доход “Зарплата” на сумму 50000 добавлен.
            
                /add_in 25000 Премия
                – Доход “Премия” на сумму 25000 добавлен.
            
                /add_ex 1500 Продукты
                – Расход “Продукты” на сумму 1500 добавлен.
            
                /income
                — Доход «Зарплата» на сумму 50000
                — Доход «Премия» на сумму 25000
            
                /expense
                — Расход «Продукты» на сумму 1500
            
                /balance
                Текущий баланс: 73500
            
                /delete_ex 1500 Продукты
                Расход “Продукты” на сумму 1500 удален
            
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
         * @return команда в виде строки
         */
        public String getParameterName(){
            return parameter_name;
        }

        /**
         * Возвращает параметр команды - сумма операции.
         *
         * @return команда в виде строки
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
     * @return объект CommandParts с разобранной командой и параметром
     */
    private CommandParts parseCommand(String userInput){
        if (userInput.isBlank()){
            return new CommandParts("","","");
        }

        String trimmedInput = userInput.trim();
        String[] parts = trimmedInput.split("\\s+", 3);

        String command = parts[0];
        String parameter_amount = parts.length > 2 ? parts[1].trim() : "";
        String parameter_name = parts.length > 2 ? parts[2].trim() : "";

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
        } else if ("/help".equals(command)) {
            return HELP_MESSAGE;
        } else {
            return """
                    Неизвестная команда.
                    Введите /help для просмотра доступных команд.""";
        }
    }
}

