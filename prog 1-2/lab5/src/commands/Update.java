package commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.StudyGroup;

import java.util.Scanner;

/**
 * Команда 'update'. Обновляет элемент коллекции по ключу.
 * @author dmitrij
 */
public class Update extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private final Console console;
    static final int argsLen = 1;
    public Update(CollectionManager collectionManager, CommandManager commandManager, Console console) {
        super("update (id)", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.console = console;
    }
    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда
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

        if (!collectionManager.used_keys.contains(key)) {
            console.println("Key не использован!");
            return false;
        }
        StudyGroup elem =  console.readElement(scanner);
        if (elem == null) {return false;}
        elem.setId(key);
        collectionManager.updateByKey(key, elem);
        // collectionManager.collection.put(key, elem);
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
