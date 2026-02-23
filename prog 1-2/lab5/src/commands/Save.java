package commands;

import managers.CollectionManager;
import managers.FileManager;
import other.StudyGroup;

public class Save extends Command {
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    static final int argsLen = 0;
    public Save(CollectionManager collectionManager, FileManager fileManager) {
        super("save","сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }

    public void execute(String[] args) {
        fileManager.saveToFile(collectionManager.collection);
    }
}
