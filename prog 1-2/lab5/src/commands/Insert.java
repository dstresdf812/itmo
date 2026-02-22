package commands;

import managers.CollectionManager;


public class Insert extends Command {
    private final CollectionManager collectionManager;

    public Insert(CollectionManager collectionManager) {
        super("insert", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
    }

    public void execute(String[] args) {
    }
}
