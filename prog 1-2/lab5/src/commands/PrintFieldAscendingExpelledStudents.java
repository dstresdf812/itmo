package commands;


import managers.CollectionManager;
import managers.Console;

public class PrintFieldAscendingExpelledStudents extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    static final int argsLen = 0;
    public PrintFieldAscendingExpelledStudents(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_expelled_students", "вывести значения поля expelledStudents всех элементов в порядке возрастания");
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
