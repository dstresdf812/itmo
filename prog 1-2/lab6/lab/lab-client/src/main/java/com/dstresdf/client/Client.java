package com.dstresdf.client;

import com.dstresdf.client.commands.ArgumentType;
import com.dstresdf.client.commands.Command;
import com.dstresdf.client.commands.CommandList;
import com.dstresdf.client.console.Console;
import com.dstresdf.client.console.HistoryManager;
import com.dstresdf.client.console.ScriptExecutor;
import com.dstresdf.client.console.StudyGroupReader;
import com.dstresdf.client.network.ClientManager;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import java.net.InetAddress;
import java.util.Arrays;

/**
 * Клиентское приложение.
 */
public class Client {
    private final Console console;
    private final HistoryManager historyManager;
    private final StudyGroupReader studyGroupReader;
    private final ClientManager clientManager;

    private boolean flag = true;
    public Client(String host, int port) throws Exception {
        this.console = new Console();
        this.historyManager = new HistoryManager();
        this.studyGroupReader = new StudyGroupReader(console);
        this.clientManager = new ClientManager(host, port);
    }

    public void start() {
        while (flag) { // плохо: ввести переменную либо условие
            String[] input = null;

            while (input == null || input.length == 0) {
                input = console.read();
            }

            CommandList command = CommandList.getCommand(input[0]);
            if (command == null) {
                console.println("Invalid command");
                continue;
            }
            String commandName = command.getCommandName();
            ArgumentType argumentType = command.getArgumentType();

            String[] args = Arrays.copyOfRange(input, 1, input.length);

            if (commandName.equals("history")) { // на сервере
                console.println(historyManager.getHistory().toString());
                continue;
            }

            if (commandName.equals("exit")) {
                console.println("Завершение работы клиента");
                flag = false;
                continue;
            }

            historyManager.addToHistory(commandName);

            if (commandName.equals("execute_script")) {
                if (args.length != 1) {
                    console.println("У команды execute_script должен быть один аргумент");
                    continue;
                }

                ScriptExecutor scriptExecutor = new ScriptExecutor(this, console, historyManager, clientManager, studyGroupReader);
                scriptExecutor.executeScript(args[0]);
                continue;
            }

            Request request = buildRequest(commandName, argumentType, args);
            if (request == null) {
                continue;
            }

            System.out.println(request.toString());
            Response response = clientManager.sendRequest(request);
            if (response == null) {
                console.println("Сервер не отвечает");
                continue;
            }

            console.println(response.getMessage());
            if (response.getStudyGroups() != null) {
                response.getStudyGroups().stream().forEach(System.out::println);
            }
        }
    }

    public Request buildRequest(String commandName, ArgumentType argumentType, String[] args) {
        CommandType commandType = getCommandType(commandName);
        if (commandType == null) {
            console.println("Неизвестная команда. Используйте help.");
            return null;
        }

        if (argumentType.equals(ArgumentType.NO_ARGS)) {
            Request request = new Request(commandType, null, null);
            return request;
        }

        if (argumentType.equals(ArgumentType.ONE_ARG)) {
            if (args.length != 1) {
                console.println("Некорректное кол-во аргументов! Необоходимое кол-во - 1.");
                return null;
            }
            Integer integerArg;
            try {
                integerArg = Integer.parseInt(args[0]);
            } catch (Exception e) {
                console.println("Неверный формат аргумента. Требуется число.");
                return null;
            }
            Request request = new Request(commandType, integerArg, null);
            return request;
        }

        if (argumentType.equals(ArgumentType.ARG_AND_ELEM)) {
            if (args.length != 1) {
                console.println("Некорректное кол-во аргументов! Необоходимое кол-во - 1.");
                return null;
            }
            Integer integerArg;
            try {
                integerArg = Integer.parseInt(args[0]);
            } catch (Exception e) {
                console.println("Неверный формат аргумента. Требуется число.");
                return null;
            }
            StudyGroup elem = studyGroupReader.readElement(console.getScanner());
            Request request = new Request(commandType, integerArg, elem);
            return request;
        }
        return null;
    }

    private CommandType getCommandType(String commandName) { // убрать : получать с сервера
        switch (commandName) {
            case "help":
                return CommandType.HELP;
            case "info":
                return CommandType.INFO;
            case "show":
                return CommandType.SHOW;
            case "insert":
                return CommandType.INSERT;
            case "update":
                return CommandType.UPDATE;
            case "remove_key":
                return CommandType.REMOVE_KEY;
            case "clear":
                return CommandType.CLEAR;
            case "replace_if_greater":
                return CommandType.REPLACE_IF_GREATER;
            case "remove_greater_key":
                return CommandType.REMOVE_GREATER_KEY;
            case "average_of_students_count":
                return CommandType.AVERAGE_OF_STUDENTS_COUNT;
            case "print_unique_students_count":
                return CommandType.PRINT_UNIQUE_STUDENTS_COUNT;
            case "print_field_ascending_expelled_students":
                return CommandType.PRINT_FIELD_ASCENDING_EXPELLED_STUDENTS;
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 1090);
            client.start();
        } catch (Exception e) {
            System.out.println("Ошибка клиента");
        }
    }
}