package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import managers.FileManager;
import other.StudyGroup;
import utils.StudyGroupComparator;

public class ReplaceIfGreater extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final FileManager fileManager;
    private final CommandManager commandManager;
    private final StudyGroupComparator studyGroupComparator;
    static final int argsLen = 1;
    public ReplaceIfGreater(CollectionManager collectionManager, Console console, FileManager fileManager, CommandManager commandManager) {
        super("replace_if_greater", "заменить значение по ключу, если новое значение больше старого");
        this.collectionManager = collectionManager;
        this.console = console;
        this.fileManager = fileManager;
        this.commandManager = commandManager;
        this.studyGroupComparator = new StudyGroupComparator();
    }

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        StudyGroup elem = new StudyGroup();
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
        elem = console.readElement();
        StudyGroup elem_by_key = collectionManager.collection.get(key);
        if (studyGroupComparator.compare(elem_by_key, elem) < 0) {
            elem.setId(key);
            collectionManager.collection.put(key, elem);
        }
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
