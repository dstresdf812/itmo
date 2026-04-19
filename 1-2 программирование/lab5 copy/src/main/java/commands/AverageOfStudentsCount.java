package com.dstresdf.common.commands;

import managers.CollectionManager;
import managers.CommandManager;
import managers.Console;
import other.CommandStatus;
import other.Request;
import other.StudyGroup;
import utils.CommandType;

/**
 * Команда 'average_of_students_count'. Выводит среднее значение поля studentsCount для всех элементов коллекции.
 * @author dmitrij
 */
public class AverageOfStudentsCount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private static final CommandType commandType = CommandType.NO_ARGS;
    public AverageOfStudentsCount(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("average_of_students_count","вывести среднее значение поля studentsCount для всех элементов коллекции");
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
        float totalStudentsCount = 0;
        for (StudyGroup studyGroup : collectionManager.collection.values()) {
            int curStudentsCount = studyGroup.getStudentsCount();
            totalStudentsCount += curStudentsCount;
        }
        System.out.println(totalStudentsCount/collectionManager.collection.size());
        System.out.println("Команда " + this.name + " выполнена");
        return CommandStatus.OK;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
