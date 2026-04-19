package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.util.List;

/**
 * Команда 'remove_key'. Удаляет элемент из коллекции по его ключу.
 * @author dmitrij
 */
public class RemoveKey extends Command {
    private final CollectionManager collectionManager;
    public RemoveKey(CollectionManager collectionManager) {
        super("remove_key (key)", "удалить элемент из коллекции по его ключу");
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

        Integer key = request.getIntegerArgument();

        if (!collectionManager.containsKey(key)) {
            isSuccess = false;
            message = "Элемента с таким ключом не существует :(";
            Response response = new Response(isSuccess, message, studyGroups);
            return response;
        }

        collectionManager.removeByKey(key);
        isSuccess = true;
        message = "Элемент удален!";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
