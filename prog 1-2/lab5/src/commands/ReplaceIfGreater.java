package commands;

import managers.CollectionManager;
import managers.Console;
import other.StudyGroup;

public class ReplaceIfGreater extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    static final int argsLen = 1;
    public ReplaceIfGreater(CollectionManager collectionManager, Console console) {
        super("replace_if_greater", "заменить значение по ключу, если новое значение больше старого");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    public void execute(String[] args) {
        StudyGroup elem = new StudyGroup();
        if (args.length != 1) {
            System.out.println("wrong number of arguments");
        } else {
            elem = console.readElement();
            elem.toString();
        }
    }
}
