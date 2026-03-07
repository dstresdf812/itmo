import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import commands.*;
import managers.FileManager;

// послед читать элементы
// eyecolor null +
// execute_script
// args fix

public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        CommandManager commandManager = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager();
        commandManager.add("help", new Help(commandManager, console));
        commandManager.add("info", new Info(console, collectionManager, commandManager));
        commandManager.add("show", new Show(console, collectionManager, commandManager));
        commandManager.add("insert", new Insert(collectionManager, console, commandManager));
        commandManager.add("update", new Update(collectionManager, commandManager, console));
        commandManager.add("remove_key", new RemoveKey(commandManager, collectionManager, console));
        commandManager.add("clear", new Clear(collectionManager, commandManager));
        commandManager.add("save", new Save(collectionManager, fileManager, commandManager));
        commandManager.add("execute_script", new ExecuteScript(console, collectionManager, commandManager, fileManager));
        commandManager.add("exit", new Exit());
        commandManager.add("history", new History(console, commandManager));
        commandManager.add("replace_if_greater", new ReplaceIfGreater(collectionManager, console, fileManager, commandManager));
        commandManager.add("remove_greater_key", new RemoveGreaterKey(collectionManager, commandManager, console));
        commandManager.add("average_of_students_count", new AverageOfStudentsCount(console, collectionManager, commandManager));
        commandManager.add("print_unique_students_count", new PrintUniqueStudentsCount(console, collectionManager, commandManager));
        commandManager.add("print_field_ascending_expelled_students", new PrintFieldAscendingExpelledStudents(console, collectionManager, commandManager));

        collectionManager.SetStudyGroup(fileManager.ReadFile("input.json"));

        // System.out.println(collectionManager.collection.get(2));
        console.start(console, commandManager);
    }
}