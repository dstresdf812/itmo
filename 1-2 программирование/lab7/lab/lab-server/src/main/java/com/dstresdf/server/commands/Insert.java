package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;
import java.util.List;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по заданному ключу.
 * @author dmitrij
 */
public class Insert extends Command {
    private final StudyGroupService studyGroupService;
    public Insert(StudyGroupService studyGroupService) {
        super("insert (key)", "добавить новый элемент с заданным ключом");
        this.studyGroupService = studyGroupService;
    }

    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда.
     */
    public Response execute(Request request) throws SQLException {
        return studyGroupService.insertStudyGroup(request.getLogin(), request.getIntegerArgument(), request.getStudyGroup());
    }
}
