package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.util.List;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по заданному ключу.
 * @author dmitrij
 */
public class Insert extends Command {
    private final CollectionManager collectionManager;
    public Insert(CollectionManager collectionManager) {
        super("insert (key)", "добавить новый элемент с заданным ключом");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда.
     */
    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        Integer key = request.getIntegerArgument();
        StudyGroup elem = request.getStudyGroup();

        if (collectionManager.used_keys.contains(key)) {
            isSuccess = false;
            message = "Элемент с таким ключом уже существует!";
            Response response = new Response(isSuccess, message, studyGroups);
            return response;
        }
        if (elem == null) {
            isSuccess = false;
            message = "elem = null :((";
            Response response = new Response(isSuccess, message, studyGroups);
            return response;
        }

        elem.setId(key);
        collectionManager.insertByKey(key, elem);
        isSuccess = true;
        message = "все гуд!";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
