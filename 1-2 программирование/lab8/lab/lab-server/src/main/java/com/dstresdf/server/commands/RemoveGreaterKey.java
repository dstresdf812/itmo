package com.dstresdf.server.commands;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;

/**
 * Команда 'remove_greater_key'. Удаляет из коллекции все элементы, ключ которых превышает заданный.
 * @author dmitrij
 */
public class RemoveGreaterKey extends Command {
    private final StudyGroupService studyGroupService;
    public  RemoveGreaterKey(StudyGroupService studyGroupService) {
        super("remove_greater_key (key)","удалить из коллекции все элементы, ключ которых превышает заданный");
        this.studyGroupService = studyGroupService;
    }
    public Response execute(Request request) throws SQLException {
        return studyGroupService.removeGreaterKey(request.getLogin(), request.getIntegerArgument());
    }
}
