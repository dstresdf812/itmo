package com.dstresdf.common.commands;

import managers.CommandManager;
import managers.Console;
import managers.CollectionManager;
import other.CommandStatus;
import other.Request;
import other.StudyGroup;
import utils.CommandType;

import java.util.HashSet;
import java.util.Set;

/**
 * Команда 'print_unique_students_count'. Выводит уникальные значения поля studentsCount всех элементов в коллекции.
 * @author dmitrij
 */
public class PrintUniqueStudentsCount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private static final CommandType commandType = CommandType.NO_ARGS;
    public PrintUniqueStudentsCount(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("print_unique_students_count","вывести уникальные значения поля studentsCount всех элементов в коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }
    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public CommandStatus execute(Request request) {
        commandManager.addToHistory(this);
        Set<Integer> keys = new HashSet<>();
        for (StudyGroup elem : collectionManager.collection.values()) {
            keys.add(elem.getStudentsCount());
        }
        console.println(keys);
        System.out.println("Команда " + this.name + " выполнена");
        return CommandStatus.OK;
    }
    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public CommandType getCommandType() {
        return commandType;
    }
}
