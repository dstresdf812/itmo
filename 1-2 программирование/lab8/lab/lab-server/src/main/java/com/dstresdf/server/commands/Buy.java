package com.dstresdf.server.commands;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по заданному ключу.
 * @author dmitrij
 */
public class Buy extends Command {
    private final StudyGroupService studyGroupService;
    public Buy(StudyGroupService studyGroupService) {
        super("buy (key)", "купить элемент коллекции у пользователя");
        this.studyGroupService = studyGroupService;
    }

    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда.
     */
    public Response execute(Request request) throws SQLException {
        return studyGroupService.buyStudyGroup(request.getLogin(), request.getIntegerArgument());
    }
}
