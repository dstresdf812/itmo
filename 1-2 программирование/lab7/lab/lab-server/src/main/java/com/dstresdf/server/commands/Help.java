package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Команда 'help'. Выводит список доступных команд.
 * @author dmitrij
 */
public class Help extends Command {
    private final CommandManager commandManager;
    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
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
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
        }
        message = commandManager.getCommands().values().stream()
                        .map(command -> command.getName() + " --- " + command.getDescription())
                        .collect(Collectors.joining("\n"));
        message = "Доступные команды: \n" + message;
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
