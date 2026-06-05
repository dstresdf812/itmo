package com.dstresdf.client;

import com.dstresdf.client.gui.AuthWindow;
import com.dstresdf.client.gui.LocalizationManager;
import com.dstresdf.client.gui.MainWindow;
import com.dstresdf.client.network.GuiHelper;
import com.dstresdf.common.commands.ArgumentType;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.client.console.Console;
import com.dstresdf.client.console.ScriptExecutor;
import com.dstresdf.client.console.StudyGroupReader;
import com.dstresdf.client.network.ClientManager;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import javax.swing.*;
import java.util.Arrays;

/**
 * Клиентское приложение.
 */
public class Client {
    private final Console console;
    private final StudyGroupReader studyGroupReader;
    private final ClientManager clientManager;
    private int requestCounter = 0;
    private boolean isAuthorized = false;
    private String login;
    private String password;
    ScriptExecutor scriptExecutor;
    private boolean flag = true;
    public Client(String host, int port) throws Exception {
        this.console = new Console();
        this.studyGroupReader = new StudyGroupReader(console);
        this.clientManager = new ClientManager(host, port);
        this.scriptExecutor = new ScriptExecutor(this, console, clientManager, studyGroupReader);
    }

    public void start() {
        while (flag) { // плохо: ввести переменную либо условие
            String[] input = null;

            if (!isAuthorized) {
                console.println("Необходима авторизация");
            }

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

            if (!isAuthorized) {
                if (commandName.equals("login") || commandName.equals("register")) {
                    Request request = buildRequest(command, argumentType, args);
                    if (request == null) {
                        continue;
                    }
                    Response response = clientManager.sendRequest(request);
                    if (response.isSuccess()) {
                        isAuthorized = true;
                        login = request.getLogin();
                        password = request.getPassword();
                    }

                    console.println(response.getMessage());
                    continue;
                }
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
            addAuth(request);
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

    public void addAuth(Request request) {
        request.setLogin(login);
        request.setPassword(password);
    }

    public Request buildRequest(CommandType command, ArgumentType argumentType, String[] args) {
        ArgumentType commandType = command.getArg();

        if (command.equals(CommandType.LOGIN) || command.equals(CommandType.REGISTER)) {
            if (args.length != 1) {
                console.println("Введите имя пользователя: ");
                return null;
            }

            console.print("Введите пароль: ");
            String enteredPassword = console.getScanner().nextLine();

            Request request = new Request(requestCounter++, command, null, null);
            request.setLogin(args[0]);
            request.setPassword(enteredPassword);
            return request;
        }

        if (command.equals(CommandType.HELP)) {
            Request request = new Request(requestCounter++, command, null, null);
            return request;
        }

        if (argumentType.equals(ArgumentType.NO_ARGS)) {
            Request request = new Request(requestCounter++, command, null, null);
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
            Request request = new Request(requestCounter++, command, integerArg, null);
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
            Request request = new Request(requestCounter++, command, integerArg, elem);
            return request;
        }
        return null;
    }

    private ClientManager getClientManager() {
        return clientManager;
    }
    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 1090);;
            GuiHelper guiHelper = new GuiHelper("localhost", 1090);
            SwingUtilities.invokeLater(() -> {
                new AuthWindow(guiHelper, new LocalizationManager());
            });
            // client.start();
        } catch (Exception e) {
            System.out.println("Ошибка клиента");
        }
    }
}