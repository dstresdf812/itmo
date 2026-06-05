package com.dstresdf.server.commands;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;

/**
 * Команда 'print_unique_students_count'. Выводит уникальные значения поля studentsCount всех элементов в коллекции.
 * @author dmitrij
 */
public class PrintUniqueStudentsCount extends Command {
    private final StudyGroupService studyGroupService;
    public PrintUniqueStudentsCount(StudyGroupService studyGroupService) {
        super("print_unique_students_count","вывести уникальные значения поля studentsCount всех элементов в коллекции");
        this.studyGroupService = studyGroupService;
    }
    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public Response execute(Request request) throws SQLException {
        return studyGroupService.printUniqueStudentsCount(request.getLogin());
    }
}
