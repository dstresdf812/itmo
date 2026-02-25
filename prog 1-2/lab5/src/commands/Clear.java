package commands;

import managers.CollectionManager;
import managers.CommandManager;

public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public Clear(CollectionManager collectionManager, CommandManager commandManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
