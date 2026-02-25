package commands;

import managers.Console;
import managers.CollectionManager;

public class PrintUniqueStudentsCount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    static final int argsLen = 1;
    public PrintUniqueStudentsCount(Console console, CollectionManager collectionManager) {
        super("print_unique_students_count","вывести уникальные значения поля studentsCount всех элементов в коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public boolean execute(String[] args) {
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
