package commands;

import managers.CollectionManager;
import managers.Console;
import other.StudyGroup;


public class Insert extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    static final int argsLen = 1;
    public Insert(CollectionManager collectionManager, Console console) {
        super("insert", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    public void execute(String[] args) {

    }
}
