package com.dstresdf.server.commands;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;

/**
 * Команда 'replace_if_greater'. Заменяет значение по ключу, если новое значение больше старого&
 * @author dmitrij
 */
public class ReplaceIfGreater extends Command {
    private final StudyGroupService studyGroupService;
    public ReplaceIfGreater(StudyGroupService studyGroupService) {
        super("replace_if_greater (key)", "заменить значение по ключу, если новое значение больше старого");
        this.studyGroupService = studyGroupService;
    }

    public Response execute(Request request) throws SQLException {
        return studyGroupService.replaceIfGreater(request.getLogin(),request.getIntegerArgument(), request.getStudyGroup());
    }
}
