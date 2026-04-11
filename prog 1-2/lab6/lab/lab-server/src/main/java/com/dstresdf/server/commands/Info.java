package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.util.Date;
import java.util.List;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 * @author dmitrij
 */
public class Info extends Command {
    private final CollectionManager collectionManager;
    public Info(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
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

        int size = collectionManager.getLength();
        Date initDate = collectionManager.getInitDate();
        String type = collectionManager.getType();
        message = "Тип: " + type + "\nДата" +
                " инициализации: " + initDate + "\nРазмер: " + size;
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
