package com.dstresdf.common.commands;

import managers.CommandManager;
import managers.Console;
import managers.CollectionManager;
import other.CommandStatus;
import other.Request;
import utils.CommandType;

import java.util.Date;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 * @author dmitrij
 */
public class Info extends Command {
    private final Console console;
    private final  CollectionManager collectionManager;
    private final CommandManager commandManager;
    private static final CommandType commandType = CommandType.NO_ARGS;
    public Info(Console console, CollectionManager collectionManager,CommandManager commandManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
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
        int size = collectionManager.getLength();
        Date initDate = collectionManager.getInitDate();
        String type = collectionManager.getType();
        console.println("Тип: " + type + "\nДата" +
                " инициализации: " + initDate + "\nРазмер: " + size);
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
