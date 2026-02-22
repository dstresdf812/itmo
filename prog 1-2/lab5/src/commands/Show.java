package commands;

import managers.Console;
import managers.CollectionManager;
import other.StudyGroup;

public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public void execute(String[] args) {
        for (StudyGroup element : collectionManager.collection.values()) {
            System.out.println(element.toString());
        }
    }
}
