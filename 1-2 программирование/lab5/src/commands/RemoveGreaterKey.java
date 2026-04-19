package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.CommandStatus;
import other.Request;
import utils.CommandType;

import java.util.ArrayList;

/**
 * Команда 'remove_greater_key'. Удаляет из коллекции все элементы, ключ которых превышает заданный.
 * @author dmitrij
 */
public class RemoveGreaterKey extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final Console console;
    private static final CommandType commandType = CommandType.ONE_ARG;
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
    public CommandStatus execute(Request request) {
        commandManager.addToHistory(this);
        Integer key = request.getKey();
        int count = 0;
        ArrayList<Integer> del = new ArrayList<>();
        for (Integer elem_key : collectionManager.used_keys) {
            if (elem_key > key) {
                collectionManager.collection.remove(elem_key);
                del.add(elem_key);
                count++;
            }
        }

        collectionManager.used_keys.removeAll(del);
        console.println("Удалено " + count + " элементов.");
        System.out.println("Команда " + this.name + " выполнена");
        return CommandStatus.OK;
    }
    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
