package commands;


import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.StudyGroup;
import utils.CommandType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Команда 'print_field_ascending_expelled_students'. Выводит значения поля expelledStudents всех элементов в порядке возрастания.
 * @author dmitrij
 */
public class PrintFieldAscendingExpelledStudents extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private static final CommandType commandType = CommandType.NO_ARGS;
    public PrintFieldAscendingExpelledStudents(Console console, CollectionManager collectionManager,  CommandManager commandManager) {
        super("print_field_ascending_expelled_students", "вывести значения поля expelledStudents всех элементов в порядке возрастания");
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
        ArrayList<Long> arr = new ArrayList<>();

        for (StudyGroup elem : collectionManager.collection.values()) {
            arr.add(elem.getExpelledStudents());
        }
        Collections.sort(arr);
        console.println(arr);
        System.out.println("Команда " + this.name + " выполнена");
        return true;
    }
    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
