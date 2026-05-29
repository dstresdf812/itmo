package com.dstresdf.server.collection;

import com.dstresdf.common.model.StudyGroup;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Управление коллекцией.
 * @author dmitrij
 */
public class CollectionManager {
    public Map<Integer, StudyGroup> collection = new ConcurrentHashMap<>();
    public Date initDate = new Date();

    public CollectionManager() {}
    /**
     * Добавить элемент в коллецию по ключу.
     * @param studyGroups
     */

    public Map<Integer, StudyGroup> getCollection() {
        return collection;
    }

    public void SetStudyGroup(List<StudyGroup> studyGroups) {
        for (StudyGroup studyGroup : studyGroups) {
            int key = studyGroup.getId();
            if (containsKey(key)) {
                System.out.println("ID already exists :(");
            } else {
                collection.put(key, studyGroup);
            }
        }
    }

    public boolean containsKey(int key){
        return collection.containsKey(key);
    }

    public StudyGroup getByKey(int key){
        return collection.get(key);
    }


    public int getLength() {
        return collection.size();
    }

    public String getType() {
        return collection.getClass().getTypeName();
    }

    public Date getInitDate() {
        return initDate;
    }

    public void insertByKey(Integer key, StudyGroup studyGroup) throws SQLException {
        collection.put(key, studyGroup);
    }

    public void updateByKey(Integer key, StudyGroup studyGroup) throws SQLException {
        collection.put(key, studyGroup);
    }

    public void removeByKey(Integer key) throws SQLException {
        collection.remove(key);
    }

    public List<Integer> getUsersElems(String ownerLogin) {
        List<Integer> usersElems = new ArrayList<>();
        for (Integer key : collection.keySet()) {
            StudyGroup studyGroup = collection.get(key);
            if (studyGroup != null && ownerLogin.equals(studyGroup.getOwnerLogin())) {
                usersElems.add(studyGroup.getId());
            }
        }
        return usersElems;
    }
}
