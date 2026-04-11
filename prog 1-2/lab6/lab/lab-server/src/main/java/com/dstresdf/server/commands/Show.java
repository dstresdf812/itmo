package com.dstresdf.server.commands;

import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.model.StudyGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 * @author dmitrij
 */
public class Show extends Command {
    private final CollectionManager collectionManager;
    public Show(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = collectionManager.collection.values().stream()
                .sorted(Comparator.comparing(StudyGroup::getName))
                .toList();

        isSuccess = true;
        message = "Команда выполнена! Элементы коллекции";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
