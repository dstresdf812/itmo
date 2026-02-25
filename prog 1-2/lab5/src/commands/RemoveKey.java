package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;

import java.util.Arrays;

public class RemoveKey extends Command {
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private final Console console;
    static final int argsLen = 1;
    public RemoveKey(CommandManager commandManager, CollectionManager collectionManager,  Console console) {
        super("remove_key", "удалить элемент из коллекции по его ключу");
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
        this.console = console;
    }

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        Integer key;
        try {
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            console.println("ID должен быть целым числом");
            return false;
        }

        if (!collectionManager.used_keys.contains(key)) {
            console.println("Key не использован!");
            return false;
        }

        collectionManager.removeByKey(key);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
