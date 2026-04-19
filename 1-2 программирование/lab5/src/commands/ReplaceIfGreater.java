package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import managers.FileManager;
import other.CommandStatus;
import other.Request;
import other.StudyGroup;
import utils.CommandType;
import utils.StudyGroupComparator;

import java.util.Scanner;

/**
 * Команда 'replace_if_greater'. Заменяет значение по ключу, если новое значение больше старого&
 * @author dmitrij
 */
public class ReplaceIfGreater extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final FileManager fileManager;
    private final CommandManager commandManager;
    private final StudyGroupComparator studyGroupComparator;
    private static final CommandType commandType = CommandType.ARG_AND_ELEM;
    public ReplaceIfGreater(CollectionManager collectionManager, Console console, FileManager fileManager, CommandManager commandManager) {
        super("replace_if_greater (key)", "заменить значение по ключу, если новое значение больше старого");
        this.collectionManager = collectionManager;
        this.console = console;
        this.fileManager = fileManager;
        this.commandManager = commandManager;
        this.studyGroupComparator = new StudyGroupComparator();
    }
    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда
     */
    public CommandStatus execute(Request request) {
        commandManager.addToHistory(this);
        Integer key = request.getKey();
        StudyGroup elem = request.getStudyGroup();

        if (!collectionManager.used_keys.contains(key)) {
            console.println("Key не использован!");
            return CommandStatus.ERROR;
        }

        StudyGroup elem_by_key = collectionManager.collection.get(key);
        if (studyGroupComparator.compare(elem_by_key, elem) < 0) {
            elem.setId(key);
            collectionManager.collection.put(key, elem);
        }
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
