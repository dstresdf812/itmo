package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.CommandStatus;
import other.Request;
import other.StudyGroup;
import utils.CommandType;

import java.util.Scanner;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по заданному ключу.
 * @author dmitrij
 */
public class Insert extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final CommandManager commandManager;
    public static final CommandType commandType = CommandType.ARG_AND_ELEM;
    public Insert(CollectionManager collectionManager, Console console,  CommandManager commandManager) {
        super("insert (key)", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда.
     */
    public CommandStatus execute(Request request) {
        commandManager.addToHistory(this);
        Integer key = request.getKey();
        StudyGroup elem = request.getStudyGroup();

        if (collectionManager.used_keys.contains(key)) {return CommandStatus.ERROR;}
        if (elem == null) {return CommandStatus.ERROR;}

        elem.setId(key);
        collectionManager.insertByKey(key, elem);
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
