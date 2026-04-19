package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import managers.FileManager;
import other.CommandStatus;
import other.Request;
import utils.CommandHandler;
import utils.CommandType;

import java.io.*;
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
    public CommandStatus execute(Request request) {
        CommandStatus status = CommandStatus.ERROR;
        String fileName = request.getFileName();
        System.out.println(commandManager.stack);
        if (commandManager.checkStack(fileName)) {
            return CommandStatus.ERROR;
        }
        commandManager.incStack(fileName);
        commandManager.addToHistory(this);
        Scanner old_scanner = console.getScanner();
        try {
            Scanner new_scanner = new Scanner(new FileInputStream(fileName));
            console.isScriptMode = true;
            console.setScanner(new_scanner);
            while (new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                // execute_script script.txt
                String[] new_args = line.split(" ");

                String currentCommand = new_args[0];
                System.out.println("Выполняем: " + currentCommand);
                String[] inputArgs = Arrays.copyOfRange(new_args, 1, new_args.length);

                if (commandManager.getCommands().containsKey(currentCommand)) {
                    Command command = commandManager.getCommands().get(currentCommand);
                    CommandHandler handler = command.getCommandType().getFactory().createHandler(console);
                    status = handler.handle(command, inputArgs);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            console.isScriptMode = false;
            console.setScanner(old_scanner);
        }
        commandManager.decStack(fileName);
        System.out.println("Команда " + this.name + " выполнена");
        return status;
    }

    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
