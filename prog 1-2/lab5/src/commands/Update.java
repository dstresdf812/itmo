package commands;

import managers.CollectionManager;

public class Update extends Command {
    private final CollectionManager collectionManager;
    static final int argsLen = 1;
    public Update(CollectionManager collectionManager) {
        super("update (id) (element)", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    public boolean execute(String[] args) {
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
