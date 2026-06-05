package com.dstresdf.client.network;

import com.dstresdf.client.Client;
import com.dstresdf.common.commands.ArgumentType;
import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

public class GuiHelper {
    private int requestCounter = 0;
    private ClientManager clientManager;

    public GuiHelper(String host, int port) throws Exception {
        this.clientManager = new ClientManager(host, port);
    }

    public Request buildRequest(CommandType command, String login, String password) {
        if (command.equals(CommandType.LOGIN) || command.equals(CommandType.REGISTER)) {
            Request request = new Request(requestCounter++, command, null, null);
            request.setLogin(login);
            request.setPassword(password);
            return request;
        }

        if (command.getArg() == ArgumentType.NO_ARGS) {
            Request request = new Request(requestCounter++, command, null, null);
            request.setLogin(login);
            request.setPassword(password);
            return request;
        }
        return null;
    }

    public Request buildRequest(CommandType command, String login, String password, Integer arg) {
        if (command.getArg() == ArgumentType.ONE_ARG) {
            Request request = new Request(requestCounter++, command, arg, null);
            request.setLogin(login);
            request.setPassword(password);
            return request;
        }
        return null;
    }

    public Request buildRequest(CommandType command, String login, String password, Integer arg, StudyGroup studyGroup) {
        if (command.getArg() == ArgumentType.ARG_AND_ELEM) {
            Request request = new Request(requestCounter++, command, arg, studyGroup);
            request.setLogin(login);
            request.setPassword(password);
            return request;
        }
        return null;
    }

    public Response sendRequest(Request request) {
        return clientManager.sendRequest(request);
    }
}
