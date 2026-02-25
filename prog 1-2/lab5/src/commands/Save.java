package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import other.StudyGroup;

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

    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        fileManager.saveToFile(collectionManager.collection);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
