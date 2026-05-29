package com.dstresdf.server.commands;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 * @author dmitrij
 */
public class Info extends Command {
    private final StudyGroupService studyGroupService;
    public Info(StudyGroupService studyGroupService) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.studyGroupService = studyGroupService;
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public Response execute(Request request) throws SQLException {
        return studyGroupService.info(request.getLogin());
    }
}
