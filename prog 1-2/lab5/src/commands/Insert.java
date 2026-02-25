package commands;

import managers.CollectionManager;
import managers.Console;
import other.StudyGroup;


public class Insert extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    static final int argsLen = 1;
    public Insert(CollectionManager collectionManager, Console console) {
        super("insert", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    public boolean execute(String[] args) {
        Integer key;
        try {
            System.out.println(args[0]);
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            console.println("ID должен быть целым числом");
            return false;
        }

        if (collectionManager.used_keys.contains(key)) {
            console.println("Key уже использован!");
            return false;
        }
        StudyGroup elem = console.readElement();
        elem.setId(key);
        collectionManager.insertByKey(key, elem);
        return true;
    }

    public int getArgsLen() {
        return argsLen;
    }
}
