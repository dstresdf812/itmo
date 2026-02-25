package commands;

import managers.CommandManager;
import managers.Console;
import managers.CollectionManager;

import java.util.Date;

public class Info extends Command {
    private final Console console;
    private final  CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public Info(Console console, CollectionManager collectionManager,CommandManager commandManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public  boolean execute(String[] args) {
        commandManager.addToHistory(this);
        int size = collectionManager.getLength();
        Date initDate = collectionManager.getInitDate();
        String type = collectionManager.getType();
        console.println("Тип: " + type + "\nДата" +
                " инициализации: " + initDate + "\nРазмер: " + size);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
