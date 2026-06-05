package com.dstresdf.common.network;

import com.dstresdf.common.model.StudyGroup;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    private final boolean isSuccess;
    private final String message;
    private final List<StudyGroup> studyGroups;

    public Response(boolean isSuccess, String message, List<StudyGroup> studyGroups) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.studyGroups = studyGroups;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public List<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    @Override
    public String toString() {
        return "Response{"
                + "isSuccess=" + isSuccess
                + ", message='" + message + '\''
                + ", studyGroups=" + studyGroups
                + '}';
    }
}
