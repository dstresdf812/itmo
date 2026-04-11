package com.dstresdf.server.commands;

import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.common.model.StudyGroup;

import java.util.List;


/**
 * Команда 'update'. Обновляет элемент коллекции по ключу.
 * @author dmitrij
 */
public class Update extends Command {
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update (id)", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        Integer key  = request.getIntegerArgument();
        StudyGroup elem = request.getStudyGroup();

        if (!collectionManager.containsKey(key)) {
            isSuccess = false;
            message = "Элемента с таким ключом не существует :(";
            Response response = new Response(isSuccess, message, studyGroups);
            return response;
        }

        elem.setId(key);
        collectionManager.updateByKey(key, elem);
        isSuccess = true;
        message = "Команда выполнена!";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
