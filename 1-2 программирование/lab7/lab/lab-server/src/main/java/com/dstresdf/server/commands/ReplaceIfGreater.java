package com.dstresdf.server.commands;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.util.StudyGroupComparator;

import java.util.List;

/**
 * Команда 'replace_if_greater'. Заменяет значение по ключу, если новое значение больше старого&
 * @author dmitrij
 */
public class ReplaceIfGreater extends Command {
    private final CollectionManager collectionManager;
    private final StudyGroupComparator studyGroupComparator;
    public ReplaceIfGreater(CollectionManager collectionManager) {
        super("replace_if_greater (key)", "заменить значение по ключу, если новое значение больше старого");
        this.collectionManager = collectionManager;
        this.studyGroupComparator = new StudyGroupComparator();
    }

    public Response execute(Request request) {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        Integer key = request.getIntegerArgument();
        StudyGroup elem = request.getStudyGroup();

        if (!collectionManager.containsKey(key)) {
            isSuccess = false;
            message = "Элемента с таким ключом не существует :(";
            Response response = new Response(isSuccess, message, studyGroups);
            return response;
        }

        StudyGroup elem_by_key = collectionManager.getByKey(key);
        if (studyGroupComparator.compare(elem_by_key, elem) < 0) {
            elem.setId(key);
            collectionManager.updateByKey(key, elem);
            message = "Элемент изменен";
        } else {
            message = "Элемент не изменен";
        }
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
