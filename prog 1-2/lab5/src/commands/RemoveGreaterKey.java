package commands;

import managers.CollectionManager;

public class RemoveGreaterKey extends Command {
    private final CollectionManager collectionManager;
    static final int argsLen = 1;
    public  RemoveGreaterKey(CollectionManager collectionManager) {
        super("remove_greater_key","удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
    }

    public boolean execute(String[] args) {
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
