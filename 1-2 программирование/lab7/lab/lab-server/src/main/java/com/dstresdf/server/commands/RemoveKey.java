package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;
import java.util.List;

/**
 * Команда 'remove_key'. Удаляет элемент из коллекции по его ключу.
 * @author dmitrij
 */
public class RemoveKey extends Command {
    private final StudyGroupService studyGroupService;
    public RemoveKey(StudyGroupService studyGroupService) {
        super("remove_key (key)", "удалить элемент из коллекции по его ключу");
        this.studyGroupService = studyGroupService;
    }
    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public Response execute(Request request) throws SQLException {
        return studyGroupService.removeStudyGroup(request.getLogin(), request.getIntegerArgument());
    }
}
