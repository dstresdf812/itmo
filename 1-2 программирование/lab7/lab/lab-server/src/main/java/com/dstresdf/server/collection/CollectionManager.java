package com.dstresdf.server.collection;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.server.db.DatabaseManager;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Управление коллекцией.
 * @author dmitrij
 */
public class CollectionManager {
    public Map<Integer, StudyGroup> collection = new ConcurrentHashMap<>();
    public ArrayList<Integer> used_keys = new ArrayList<>();
    public Date initDate = new Date();

    private final DatabaseManager databaseManager;
    public CollectionManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
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
        int id = databaseManager.insertStudyGroup(studyGroup);
        studyGroup.setId(id);
        collection.put(id, studyGroup);
    }

    public boolean updateByKey(Integer key, StudyGroup studyGroup) throws SQLException {
        studyGroup.setId(key);

        boolean t = databaseManager.updateStudyGroup(studyGroup);
        if (t) {
            collection.put(key, studyGroup);
            return true;
        }

        return false;
    }

    public boolean removeByKey(Integer key, String  ownerLogin) throws SQLException {
        boolean t = databaseManager.removeStudyGroup(key, ownerLogin);
        if (t) {
            collection.remove(key);
            return true;
        }
        return false;
    }

    public int clearCollection(String ownerLogin) throws SQLException {
        int count = 0;
        for (Integer key : collection.keySet()) {
            StudyGroup studyGroup = collection.get(key);
            if (studyGroup != null && ownerLogin.equals(studyGroup.getOwnerLogin())) {
                if (removeByKey(key, ownerLogin)) {
                    count++;
                }
            }
        }
        return count;
    }
}
