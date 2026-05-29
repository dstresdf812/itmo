package com.dstresdf.common.network;

import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.util.RequestStatus;

import java.io.Serializable;

public class Request implements Serializable {
    private Integer requestId;
    private final CommandType commandType;
    private final Integer integerArgument;
    private final StudyGroup studyGroup;
    private String client;

    private String login;
    private String password;

    public Request(Integer requestID,CommandType commandType, Integer integerArgument, StudyGroup studyGroup) {
        this.requestId = requestID;
        this.commandType = commandType;
        this.integerArgument = integerArgument;
        this.studyGroup = studyGroup;
        this.client = null;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Integer getIntegerArgument() {
        return integerArgument;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getRequestId() {
        return requestId;
    }
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Request{"
                + "commandType=" + commandType
                + ", integerArgument=" + integerArgument
                + ", studyGroup=" + studyGroup
                + ", login='" + login
                + '}';
    }
}
