package commands;

import managers.CommandManager;
import managers.Console;
import managers.CollectionManager;
import other.StudyGroup;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 * @author dmitrij
 */
public class Show extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    static final int argsLen = 0;
    public Show(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }
    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public boolean execute(String[] args) {
        commandManager.addToHistory(this);
        for (StudyGroup element : collectionManager.collection.values()) {
            System.out.println(element.toString());
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
