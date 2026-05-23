package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Команда 'remove_greater_key'. Удаляет из коллекции все элементы, ключ которых превышает заданный.
 * @author dmitrij
 */
public class RemoveGreaterKey extends Command {
    private final CollectionManager collectionManager;
    public  RemoveGreaterKey(CollectionManager collectionManager) {
        super("remove_greater_key (key)","удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
    }
    public Response execute(Request request) throws SQLException {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        Integer key = request.getIntegerArgument();
        int count = 0;

        Iterator<Map.Entry<Integer, StudyGroup>> iterator = collectionManager.getCollection().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer,StudyGroup> entry = iterator.next();
            if (entry.getKey() > key) {
                collectionManager.removeByKey(key,request.getLogin());
                count++;
            }
        }

        isSuccess = true;
        message = "Удалено " + count + " элементов";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
