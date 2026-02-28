package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.StudyGroup;

import java.util.Scanner;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по заданному ключу.
 * @author dmitrij
 */
public class Insert extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final CommandManager commandManager;
    static final int argsLen = 1;
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
    public boolean execute(String[] args, Scanner scanner) {
        commandManager.addToHistory(this);
        Integer key;
        try {
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            console.println("ID должен быть целым числом");
            return false;
        }

        if (collectionManager.used_keys.contains(key)) {
            console.println("Key уже использован!");
            return false;
        }
        StudyGroup elem = console.readElement(scanner);
        if (elem == null) {return false;}
        elem.setId(key);
        collectionManager.insertByKey(key, elem);
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
