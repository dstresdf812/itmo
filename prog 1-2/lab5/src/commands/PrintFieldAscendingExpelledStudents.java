package commands;


import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.StudyGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PrintFieldAscendingExpelledStudents extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public PrintFieldAscendingExpelledStudents(Console console, CollectionManager collectionManager,  CommandManager commandManager) {
        super("print_field_ascending_expelled_students", "вывести значения поля expelledStudents всех элементов в порядке возрастания");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        ArrayList<Long> arr = new ArrayList<>();

        for (StudyGroup elem : collectionManager.collection.values()) {
            arr.add(elem.getExpelledStudents());
        }
        Collections.sort(arr);
        console.println(arr);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
