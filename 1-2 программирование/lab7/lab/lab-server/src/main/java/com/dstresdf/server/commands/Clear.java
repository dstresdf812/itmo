package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.util.List;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author dmitrij
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        collectionManager.clearCollection();
        isSuccess = true;
        message = "Коллекция очищена";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
