import java.util.Arrays;
import java.util.HashMap;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import commands.*;
import managers.FileManager;
public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        CommandManager commandManager = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager();
        commandManager.add("help", new Help(commandManager, console));
        commandManager.add("remove_key", new RemoveKey(commandManager));
        commandManager.add("info", new Info(console, collectionManager));
        commandManager.add("show", new Show(console, collectionManager));
        commandManager.add("exit", new Exit());
        commandManager.add("save", new Save(collectionManager,fileManager));
        commandManager.add("replace_if_greater", new ReplaceIfGreater(collectionManager, console));

        collectionManager.SetStudyGroup(fileManager.ReadFile());

        // System.out.println(collectionManager.collection.get(2));

        while (true) {
        String[] input = console.read();
        String currentCommand = input[0];
        String[] inputArgs = Arrays.copyOfRange(input,1,input.length);

            if (commandManager.getCommands().containsKey(currentCommand)) {
                Command command = commandManager.getCommands().get(currentCommand);
                while (command.argsLen != inputArgs.length & command.argsLen != 0) {
                    console.println("Wrong amount of arguments. Command needs " + command.argsLen + " arguments.");
                    inputArgs = console.readArgs();
                }
                command.execute(inputArgs);
            } else {
                console.println("Command " + currentCommand + " not found. Use help for list of commands.");
            }
        }
    }
}