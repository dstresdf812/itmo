package commands;


import managers.CollectionManager;
import managers.Console;

public class PrintFieldAscendingExpelledStudents extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public PrintFieldAscendingExpelledStudents(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_expelled_students", "вывести значения поля expelledStudents всех элементов в порядке возрастания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public void execute(String[] args) {}
}
