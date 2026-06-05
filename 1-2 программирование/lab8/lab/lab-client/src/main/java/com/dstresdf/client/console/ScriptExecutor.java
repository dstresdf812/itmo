package com.dstresdf.client.console;

import com.dstresdf.client.Client;
import com.dstresdf.common.commands.ArgumentType;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.client.network.ClientManager;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Выполняет команды из скрипта.
 */
public class ScriptExecutor {
    private final Client client;
    private final Console console;
    private final ClientManager clientManager;
    private final StudyGroupReader studyGroupReader;
    private Map<String, Integer> stack = new HashMap<>();
    private final int max_stack = 5;
    private static ScriptExecutor scriptExecutor;
    public ScriptExecutor(Client client, Console console, ClientManager clientManager, StudyGroupReader studyGroupReader) {
        this.client = client;
        this.console = console;
        this.clientManager = clientManager;
        this.studyGroupReader = studyGroupReader;
        this.scriptExecutor = this;
    }

    public static ScriptExecutor getScriptExecutor() {
        return scriptExecutor;
    }

    public void executeScript(String fileName) {
        System.out.println(stack);
        if (checkStack(fileName)) {
            System.out.println("No such file exists");
        }
        incStack(fileName);

        Scanner old_scanner = console.getScanner();
        studyGroupReader.setScriptMode(true);
        try {
            Scanner new_scanner = new Scanner(new FileInputStream(fileName));
            console.setScanner(new_scanner);
            while (new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                String[] new_args = line.split(" ");

                String currentCommand = new_args[0];
                System.out.println("Выполняем: " + currentCommand);

                CommandType command = CommandType.getCommand(currentCommand);
                if (command == null) {
                    console.println("Invalid command");
                    continue;
                }
                String commandName = command.getName();
                ArgumentType argumentType = command.getArg();

                String[] args = Arrays.copyOfRange(new_args, 1, new_args.length);

                if (commandName.equals("exit")) {
                    console.println("Завершение работы скрипта.");
                    break;
                }


                if (commandName.equals("execute_script")) {
                    if (args.length != 1) {
                        console.println("У команды execute_script должен быть один аргумент.");
                        continue;
                    }

                    ScriptExecutor scriptExecutor = new ScriptExecutor(client, console, clientManager, studyGroupReader);
                    scriptExecutor.executeScript(args[0]);
                    continue;
                }

                Request request = client.buildRequest(command, argumentType, args);
                if (request == null) {
                    continue;
                }
                Response response = clientManager.sendRequest(request);
                if (response == null) {
                    console.println("Не удалось получить ответ от сервера.");
                    continue;
                }
                client.addAuth(request);
                console.println(response.getMessage());

                if (response.getStudyGroups() != null && !response.getStudyGroups().isEmpty()) {
                    response.getStudyGroups().forEach(console::println);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            studyGroupReader.setScriptMode(false);
            console.setScanner(old_scanner);
        }
        decStack(fileName);
    }

    public void incStack(String scriptName) {
        stack.put(scriptName, stack.getOrDefault(scriptName, 0) + 1);
    }

    public void decStack(String scriptName) {
        stack.put(scriptName, stack.getOrDefault(scriptName, 0) - 1);
    }

    public boolean checkStack(String scriptName) {
        if (stack.containsKey(scriptName)) {
            return stack.get(scriptName) >= max_stack;
        }
        return false;

    }
}