package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'print_unique_students_count'. Выводит уникальные значения поля studentsCount всех элементов в коллекции.
 * @author dmitrij
 */
public class PrintUniqueStudentsCount extends Command {
    private final CollectionManager collectionManager;
    public PrintUniqueStudentsCount(CollectionManager collectionManager) {
        super("print_unique_students_count","вывести уникальные значения поля studentsCount всех элементов в коллекции");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду
     * @param args
     * @return Выполнена ли команда
     */
    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        isSuccess = true;
        message = collectionManager.getCollection().values().stream()
                .map(StudyGroup::getStudentsCount)
                .distinct()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
