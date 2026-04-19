package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда 'print_field_ascending_expelled_students'. Выводит значения поля expelledStudents всех элементов в порядке возрастания.
 * @author dmitrij
 */
public class PrintFieldAscendingExpelledStudents extends Command {
    private final CollectionManager collectionManager;
    public PrintFieldAscendingExpelledStudents(CollectionManager collectionManager) {
        super("print_field_ascending_expelled_students", "вывести значения поля expelledStudents всех элементов в порядке возрастания");
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

        message = collectionManager.getCollection().values().stream()
                        .map(StudyGroup::getExpelledStudents)
                        .sorted()
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n"));
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
