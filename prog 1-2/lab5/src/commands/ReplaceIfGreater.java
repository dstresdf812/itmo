package commands;

import managers.CollectionManager;

public class ReplaceIfGreater extends Command {
    private final CollectionManager collectionManager;

    public ReplaceIfGreater(CollectionManager collectionManager) {
        super("replace_if_greater", "заменить значение по ключу, если новое значение больше старого");
        this.collectionManager = collectionManager;
    }

    public void execute(String[] args) {

    }
}
