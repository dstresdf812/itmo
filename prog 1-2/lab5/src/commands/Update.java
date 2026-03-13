package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.CommandStatus;
import other.Request;
import other.StudyGroup;
import utils.CommandType;

/**
 * Команда 'update'. Обновляет элемент коллекции по ключу.
 * @author dmitrij
 */
public class Update extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final Console console;
    private static final CommandType commandType = CommandType.ARG_AND_ELEM;
    public Update(CollectionManager collectionManager, CommandManager commandManager, Console console) {
        super("update (id)", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.console = console;
    }
    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда
     */
    public CommandStatus execute(Request request) {
        commandManager.addToHistory(this);
        Integer key  = request.getKey();
        StudyGroup elem = request.getStudyGroup();
        if (!collectionManager.used_keys.contains(key)) {
            console.println("Key не использован!");
            return CommandStatus.ERROR;
        }

        elem.setId(key);
        collectionManager.updateByKey(key, elem);
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
