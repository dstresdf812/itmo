package com.dstresdf.common.network;

import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.StudyGroup;

import java.io.Serializable;

public class Request implements Serializable {

    private final CommandType commandType;
    private final Integer integerArgument;
    private final StudyGroup studyGroup;
    private String client;

    public Request(CommandType commandType, Integer integerArgument, StudyGroup studyGroup) {
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
    @Override
    public String toString() {
        return "Request{"
                + "commandType=" + commandType
                + ", integerArgument=" + integerArgument
                + ", studyGroup=" + studyGroup
                + '}';
    }
}
