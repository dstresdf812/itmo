package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.CommandStatus;
import other.Request;
import utils.CommandType;

/**
 * Команда 'remove_key'. Удаляет элемент из коллекции по его ключу.
 * @author dmitrij
 */
public class RemoveKey extends Command {
    private final CommandManager commandManager;
    private final CollectionManager collectionManager;
    private final Console console;
    public static final CommandType commandType = CommandType.ONE_ARG;
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
    public CommandStatus execute(Request request) {
        commandManager.addToHistory(this);
        Integer key = request.getKey();
        collectionManager.removeByKey(key);
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
