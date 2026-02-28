package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import managers.FileManager;
import other.StudyGroup;
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
    static final int argsLen = 1;
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
    public boolean execute(String[] args, Scanner scanner) {
        commandManager.addToHistory(this);
        StudyGroup elem;
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
        elem = console.readElement(scanner);
        StudyGroup elem_by_key = collectionManager.collection.get(key);
        if (studyGroupComparator.compare(elem_by_key, elem) < 0) {
            elem.setId(key);
            collectionManager.collection.put(key, elem);
        }
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
