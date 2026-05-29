package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;
import java.util.List;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author dmitrij
 */
public class Clear extends Command {
    private final StudyGroupService studyGroupService;
    public Clear(StudyGroupService studyGroupService) {
        super("clear", "очистить коллекцию");
        this.studyGroupService = studyGroupService;
    }

    public Response execute(Request request) throws SQLException {
        return studyGroupService.clearCollection(request.getLogin());
    }
}
