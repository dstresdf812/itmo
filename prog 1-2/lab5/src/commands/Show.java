package commands;

import managers.CommandManager;
import managers.Console;
import managers.CollectionManager;
import other.StudyGroup;

public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public Show(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        for (StudyGroup element : collectionManager.collection.values()) {
            System.out.println(element.toString());
        }
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
