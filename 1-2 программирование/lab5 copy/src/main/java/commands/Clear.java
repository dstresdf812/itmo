package com.dstresdf.common.commands;

import managers.CollectionManager;
import managers.CommandManager;
import other.CommandStatus;
import other.Request;
import utils.CommandType;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author dmitrij
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private static final CommandType commandType = CommandType.NO_ARGS;
    public Clear(CollectionManager collectionManager, CommandManager commandManager) {
        super("clear", "очистить коллекцию");
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
        collectionManager.clearCollection();
        System.out.println("Команда " + this.name + " выполнена");
        return CommandStatus.OK;
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
