package com.dstresdf.server.commands;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.StudyGroupService;

import java.sql.SQLException;

/**
 * Команда 'average_of_students_count'. Выводит среднее значение поля studentsCount для всех элементов коллекции.
 * @author dmitrij
 */
public class AverageOfStudentsCount extends Command {
    private final StudyGroupService studyGroupService;
    public AverageOfStudentsCount(StudyGroupService studyGroupService) {
        super("average_of_students_count","вывести среднее значение поля studentsCount для всех элементов коллекции");
        this.studyGroupService = studyGroupService;
    }

    public Response execute(Request request) throws SQLException {
        return studyGroupService.averageOfStudentsCount(request.getLogin());
    }
}
