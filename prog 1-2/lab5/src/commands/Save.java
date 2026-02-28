package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import other.StudyGroup;

/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 * @author dmitrij
 */
public class Save extends Command {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public Save(CollectionManager collectionManager, FileManager fileManager, CommandManager commandManager) {
        super("save","сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        fileManager.saveToFile(collectionManager.collection);
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
