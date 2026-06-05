package com.dstresdf.server.commands;

import com.dstresdf.common.network.Response;
import com.dstresdf.common.network.Request;
import com.dstresdf.server.db.StudyGroupService;
import java.sql.SQLException;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 * @author dmitrij
 */
public class Show extends Command {
    private final StudyGroupService studyGroupService;
    public Show(StudyGroupService studyGroupService) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.studyGroupService = studyGroupService;
    }

    public Response execute(Request request) throws SQLException {
        if (request.getStudyGroupCriteria() != null) {
            return studyGroupService.show(request.getLogin(),request.getStudyGroupCriteria());
        }
        return studyGroupService.show(request.getLogin());
    }
}
