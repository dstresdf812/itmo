package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.util.List;

/**
 * Команда 'average_of_students_count'. Выводит среднее значение поля studentsCount для всех элементов коллекции.
 * @author dmitrij
 */
public class AverageOfStudentsCount extends Command {
    private final CollectionManager collectionManager;
    public AverageOfStudentsCount(CollectionManager collectionManager) {
        super("average_of_students_count","вывести среднее значение поля studentsCount для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        float totalStudentsCount = 0;
        for (StudyGroup studyGroup : collectionManager.collection.values()) {
            int curStudentsCount = studyGroup.getStudentsCount();
            totalStudentsCount += curStudentsCount;
        }
        System.out.println(totalStudentsCount/collectionManager.collection.size());
        System.out.println("Команда " + this.name + " выполнена");

        message = String.valueOf(collectionManager.getCollection().values().stream()
                    .mapToInt(StudyGroup::getStudentsCount)
                    .average());
        message = "Среднее значение: " + message;
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
