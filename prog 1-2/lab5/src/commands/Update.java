package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;

public class Update extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final Console console;
    static final int argsLen = 1;
    public Update(CollectionManager collectionManager, CommandManager commandManager, Console console) {
        super("update (id) (element)", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
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

        collectionManager.collection.put(key, console.readElement());
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
