package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import managers.FileManager;
import other.Request;
import utils.CommandType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Команда 'execute_script'. Выполняет скрипт из файла.
 * @author dmitrij
 */
public class ExecuteScript extends Command{
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final FileManager fileManager;
    private static final CommandType commandType = CommandType.FILE;
    public ExecuteScript(Console console, CollectionManager collectionManager,  CommandManager commandManager, FileManager fileManager) {
        super("execute_script (script_name.txt)", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.fileManager = fileManager;
    }

    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда
     */
    public boolean execute(Request request) {
        String fileName = request.getFileName();
        System.out.println(commandManager.stack);
        if (commandManager.checkStack(fileName)) {
            return false;
        }
        commandManager.incStack(fileName);
        commandManager.addToHistory(this);
        InputStream origin = System.in;
        try {
            System.setIn(new FileInputStream(fileName));
            Scanner new_scanner = new Scanner(System.in);
            while (new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                System.out.println("Выполняем: " + line);
                String[] new_args = line.split(" ");
                String currentCommand = new_args[0];
                String[] inputArgs = Arrays.copyOfRange(new_args, 1, new_args.length);
                Request req2 = new Request(request.getKey(), (inputArgs.length == 0 ? "" : inputArgs[0]), new_scanner, request.getStudyGroup());
                System.out.println(req2.toString());
                if (commandManager.getCommands().containsKey(currentCommand)) {
                    Command command = commandManager.getCommands().get(currentCommand);
                    console.isScriptMode = true;
                    command.execute(req2);
                    console.isScriptMode = false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.setIn(origin);
        }
        commandManager.decStack(fileName);
        System.out.println("Команда " + this.name + " выполнена");
        return true;
    }

    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
