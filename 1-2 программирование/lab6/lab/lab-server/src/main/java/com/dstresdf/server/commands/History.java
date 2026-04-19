package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import java.util.List;

public class History extends Command {
    private final CommandManager commandManager;
    public History(CommandManager commandManager) {
        super("history", "вывести последние 8 команд (без их аргументов)");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    @Override
    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;
        String client = request.getClient();
        Integer key = request.getIntegerArgument();
        message = commandManager.getCommandHistory(client).toString();
        message = "История: \n" + message;
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}