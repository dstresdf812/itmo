package commands;

import managers.CollectionManager;
import managers.CommandManager;

public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    public Clear(CollectionManager collectionManager, CommandManager commandManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public void execute(String[] args) {
        commandManager.addToHistory(this);
    }
}
