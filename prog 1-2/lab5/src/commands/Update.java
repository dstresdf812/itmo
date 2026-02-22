package commands;

import managers.CollectionManager;

public class Update extends Command {
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update (id) (element)", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    public void execute(String[] args) {

    }
}
