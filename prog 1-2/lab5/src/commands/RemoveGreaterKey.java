package commands;

import managers.CollectionManager;

public class RemoveGreaterKey extends Command {
    private final CollectionManager collectionManager;

    public  RemoveGreaterKey(CollectionManager collectionManager) {
        super("remove_greater_key","удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
    }

    public void execute(String[] args) {

    }
}
