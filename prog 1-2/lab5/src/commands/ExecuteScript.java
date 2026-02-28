package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import managers.FileManager;

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
    static final int argsLen = 1;
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
    public boolean execute(String[] args, Scanner scanner) {
        commandManager.stack += 1;
        if (commandManager.stack >= commandManager.max_stack) {
            System.out.println("OVERFLOWWW " + commandManager.max_stack);
            return false;
        }
        commandManager.addToHistory(this);
        InputStream origin = System.in;
        try {
            System.setIn(new FileInputStream(args[0]));
            Scanner new_scanner = new Scanner(System.in);
            while (new_scanner.hasNextLine()) {
                String line = new_scanner.nextLine();
                System.out.println("Выполняем: " + line);
                String[] new_args = line.split(" ");
                String currentCommand = new_args[0];
                String[] inputArgs = Arrays.copyOfRange(new_args, 1, new_args.length);
                if (commandManager.getCommands().containsKey(currentCommand)) {
                    Command command = commandManager.getCommands().get(currentCommand);
                    console.isScriptMode = true;
                    command.execute(inputArgs, new_scanner);
                    console.isScriptMode = false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.setIn(origin);
        }
        commandManager.stack = 0;
        System.out.println("Команда " + this.name + " выполнена");
        return true;
    }

    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public int getArgsLen() {
        return argsLen;
    }
}
