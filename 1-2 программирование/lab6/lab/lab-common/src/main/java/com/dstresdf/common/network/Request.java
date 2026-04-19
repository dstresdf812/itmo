package com.dstresdf.common.network;

import com.dstresdf.common.commands.CommandType;
import com.dstresdf.common.model.StudyGroup;

import java.io.Serializable;

public class Request implements Serializable {

    private final CommandType commandType;
    private final Integer integerArgument;
    private final StudyGroup studyGroup;

    public Request(CommandType commandType, Integer integerArgument, StudyGroup studyGroup) {
        this.commandType = commandType;
        this.integerArgument = integerArgument;
        this.studyGroup = studyGroup;
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

    @Override
    public String toString() {
        return "Request{"
                + "commandType=" + commandType
                + ", integerArgument=" + integerArgument
                + ", studyGroup=" + studyGroup
                + '}';
    }
}
