package com.dstresdf.client;

import com.dstresdf.common.commands.ArgumentType;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.client.console.Console;
import com.dstresdf.client.console.ScriptExecutor;
import com.dstresdf.client.console.StudyGroupReader;
import com.dstresdf.client.network.ClientManager;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import java.util.Arrays;

/**
 * Клиентское приложение.
 */
public class Client {
    private final Console console;
    private final StudyGroupReader studyGroupReader;
    private final ClientManager clientManager;

    private boolean flag = true;
    public Client(String host, int port) throws Exception {
        this.console = new Console();
        this.studyGroupReader = new StudyGroupReader(console);
        this.clientManager = new ClientManager(host, port);
    }

    public void start() {
        while (flag) { // плохо: ввести переменную либо условие
            String[] input = null;

            while (input == null || input.length == 0) {
                input = console.read();
            }

            CommandType command = CommandType.getCommand(input[0]);
            if (command == null) {
                console.println("Invalid command");
                continue;
            }
            String commandName = command.getName();
            ArgumentType argumentType = command.getArg();

            String[] args = Arrays.copyOfRange(input, 1, input.length);

            if (commandName.equals("exit")) {
                console.println("Завершение работы клиента");
                flag = false;
                continue;
            }

            if (commandName.equals("execute_script")) {
                if (args.length != 1) {
                    console.println("У команды execute_script должен быть один аргумент");
                    continue;
                }

                ScriptExecutor scriptExecutor = new ScriptExecutor(this, console, clientManager, studyGroupReader);
                scriptExecutor.executeScript(args[0]);
                continue;
            }

            Request request = buildRequest(command, argumentType, args);
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

    public Request buildRequest(CommandType command, ArgumentType argumentType, String[] args) {
        ArgumentType commandType = command.getArg();

        if (argumentType.equals(ArgumentType.NO_ARGS)) {
            Request request = new Request(command, null, null);
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
            Request request = new Request(command, integerArg, null);
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
            Request request = new Request(command, integerArg, elem);
            return request;
        }
        return null;
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