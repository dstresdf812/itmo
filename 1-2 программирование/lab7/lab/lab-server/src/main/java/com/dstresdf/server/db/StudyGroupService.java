package com.dstresdf.server.db;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class StudyGroupService {
    private StudyGroupRepository studyGroupRepository;
    private CollectionManager collectionManager;
    public StudyGroupService(StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public List<StudyGroup> getCollection() throws SQLException {
        return studyGroupRepository.getCollection();
    };

    public Response clearCollection(String login) throws SQLException {
        List<Integer> keys = collectionManager.getUsersElems(login);
        int count = 0;

        for (Integer key : keys) {
            if (studyGroupRepository.removeStudyGroup(key, login)) {
                count++;
                collectionManager.removeByKey(key);
            }
        }
        return new Response(true, "Удалено " + count + " элементов", null);
    }
    public Response insertStudyGroup(String login, Integer key, StudyGroup studyGroup) throws SQLException {
        String message;

        if (studyGroup == null) {
            message = "elem = null :((";
            Response response = new Response(false, message, null);
            return response;
        }

        studyGroup.setOwnerLogin(login);
        int id = studyGroupRepository.insertStudyGroup(studyGroup);
        studyGroup.setId(id);
        collectionManager.insertByKey(id, studyGroup);
        message = "все гуд!";
        Response response = new Response(true, message, null);
        return response;
    }

    public Response updateStudyGroup(String login, Integer key, StudyGroup elem) throws SQLException {
        String message;
        if (!collectionManager.containsKey(key)) {
            message = "Элемента с таким ключом не существует :(";
            return new Response(false, message, null);
        }

        elem.setOwnerLogin(login);
        elem.setId(key);
        if (studyGroupRepository.updateStudyGroup(key, elem)) {
            collectionManager.updateByKey(key, elem);
            return new Response(true, "Элемент обновлен!", null);
        }
        return new Response(false, "Элемент не обновен!", null);
    }

    public Response removeGreaterKey(String login, Integer key) throws SQLException {
        boolean  isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        int count = 0;

        for (Integer elem_key : collectionManager.getCollection().keySet()) {
            if (elem_key > key) {
                if (studyGroupRepository.removeStudyGroup(elem_key, login)) {
                    collectionManager.removeByKey(elem_key);
                    count++;
                }
            }
        }

        isSuccess = true;
        message = "Удалено " + count + " элементов";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }

    public Response replaceIfGreater(String login, Integer key, StudyGroup elem) throws SQLException {
        class StudyGroupComparator implements Comparator<StudyGroup> {
            public int compare(StudyGroup o1, StudyGroup o2) {
                return o1.getStudentsCount() - o2.getStudentsCount();
            }
        }
        StudyGroupComparator studyGroupComparator = new StudyGroupComparator();
        String message;
        boolean isSuccess;
        if (!collectionManager.containsKey(key)) {
            message = "Элемента с таким ключом не существует :(";
            Response response = new Response(false, message, null);
            return response;
        }

        StudyGroup elem_by_key = collectionManager.getByKey(key);
        if (studyGroupComparator.compare(elem_by_key, elem) < 0) {
            elem.setOwnerLogin(login);
            elem.setId(key);
            isSuccess = studyGroupRepository.updateStudyGroup(key, elem);
            if (isSuccess) {
                collectionManager.updateByKey(key, elem);
                message = "Элемент изменен";
            } else {
                message = "Нельзя изменить чужой элемент!";
            }
        } else {
            isSuccess = true;
            message = "Элемент не изменен";
        }

        Response response = new Response(isSuccess, message, null);
        return response;
    }
    public Response removeStudyGroup(String login, Integer key) throws SQLException {
        if (studyGroupRepository.removeStudyGroup(key, login)) {
            collectionManager.removeByKey(key);
                return new Response(true, "Элемент удален!", null);
        } else {
            return new Response(false, "Элемент не удален!", null);
        }
    }
}
