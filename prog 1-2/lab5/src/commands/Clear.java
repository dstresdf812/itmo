package commands;

import managers.CollectionManager;
import managers.CommandManager;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author dmitrij
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public Clear(CollectionManager collectionManager, CommandManager commandManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        collectionManager.clearCollection();
        System.out.println("Команда " + this.name + " выполнена");
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
