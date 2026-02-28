package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;

/**
 * Команда 'remove_key'. Удаляет элемент из коллекции по его ключу.
 * @author dmitrij
 */
public class RemoveKey extends Command {
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private final Console console;
    static final int argsLen = 1;
    public RemoveKey(CommandManager commandManager, CollectionManager collectionManager,  Console console) {
        super("remove_key (key)", "удалить элемент из коллекции по его ключу");
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
        this.console = console;
    }
    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        Integer key;
        try {
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {

            console.println("ID должен быть целым числом");
            return false;
        }


        collectionManager.removeByKey(key);
        System.out.println("Команда " + this.name + " выполнена");
        return true;
    }
    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public int getArgsLen() {
        return argsLen;
    }
}
