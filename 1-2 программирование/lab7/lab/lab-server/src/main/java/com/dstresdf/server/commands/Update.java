package com.dstresdf.server.commands;

import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;
import java.util.List;


/**
 * Команда 'update'. Обновляет элемент коллекции по ключу.
 * @author dmitrij
 */
public class Update extends Command {
    private final StudyGroupService studyGroupService;

    public Update(StudyGroupService studyGroupService) {
        super("update (id)", "обновить значение элемента коллекции, id которого равен заданному");
        this.studyGroupService = studyGroupService;
    }

    public Response execute(Request request) throws SQLException {
        return studyGroupService.updateStudyGroup(request.getLogin(), request.getIntegerArgument(), request.getStudyGroup());
    }
}
