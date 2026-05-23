package com.dstresdf.server.commands;
import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.util.StudyGroupComparator;

import java.sql.SQLException;
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

    public Response execute(Request request) throws SQLException {
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
            elem.setOwnerLogin(request.getLogin());

            isSuccess = collectionManager.updateByKey(key, elem);
            message = isSuccess ? "Элемент изменен" : "Нельзя изменить чужой элемент!";
        } else {
            isSuccess = true;
            message = "Элемент не изменен";
        }

        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
