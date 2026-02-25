package commands;

import managers.CommandManager;
import managers.Console;
import managers.CollectionManager;
import other.StudyGroup;

import java.util.HashSet;
import java.util.Set;

public class PrintUniqueStudentsCount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public PrintUniqueStudentsCount(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("print_unique_students_count","вывести уникальные значения поля studentsCount всех элементов в коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        Set<Integer> keys = new HashSet<>();
        for (StudyGroup elem : collectionManager.collection.values()) {
            keys.add(elem.getStudentsCount());
        }
        console.println(keys);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
