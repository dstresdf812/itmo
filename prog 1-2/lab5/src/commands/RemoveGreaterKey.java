package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;

import java.util.ArrayList;

/**
 * Команда 'remove_greater_key'. Удаляет из коллекции все элементы, ключ которых превышает заданный.
 * @author dmitrij
 */
public class RemoveGreaterKey extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final Console console;
    static final int argsLen = 1;
    public  RemoveGreaterKey(CollectionManager collectionManager, CommandManager commandManager, Console console) {
        super("remove_greater_key (key)","удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
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
        int count = 0;
        try {
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            console.println("ID должен быть целым числом");
            return false;
        }

        ArrayList<Integer> del = new ArrayList<>();
        // System.out.println(collectionManager.used_keys);
        for (Integer elem_key : collectionManager.used_keys) {
            // System.out.println(elem_key);
            if (elem_key > key) {
                collectionManager.collection.remove(elem_key);
                del.add(elem_key);
                count++;
            }
        }

        collectionManager.used_keys.removeAll(del);
        console.println("Удалено " + count + " элементов.");
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
