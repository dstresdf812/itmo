package com.dstresdf.server.commands;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;

/**
 * Команда 'print_field_ascending_expelled_students'. Выводит значения поля expelledStudents всех элементов в порядке возрастания.
 * @author dmitrij
 */
public class PrintFieldAscendingExpelledStudents extends Command {
    private final StudyGroupService studyGroupService;
    public PrintFieldAscendingExpelledStudents(StudyGroupService studyGroupService) {
        super("print_field_ascending_expelled_students", "вывести значения поля expelledStudents всех элементов в порядке возрастания");
        this.studyGroupService = studyGroupService;
    }

    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public Response execute(Request request) throws SQLException {
        return studyGroupService.printFieldAscendingExpelledStudents(request.getLogin());
    }
}
