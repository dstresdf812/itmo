package managers;

import other.StudyGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionManager {
    public static int id = 0;
    public LinkedHashMap<Integer, StudyGroup> collection = new LinkedHashMap<>();
    public ArrayList<Integer> used_keys = new ArrayList<>();
    public Date initDate = new Date();
    public CollectionManager() {

    }

    public StudyGroup getStudyGroup(int id) {
        return collection.get(id);
    }

    public void SetStudyGroup(ArrayList<StudyGroup> studyGroups) {
        for (StudyGroup studyGroup : studyGroups) {
            int key = studyGroup.getId();
            if (used_keys.contains(key)) {
                System.out.println("ID already exists :(((");
            } else {
                used_keys.add(key);
                collection.put(key, studyGroup);
            }
        }
    }

    public int getIncId() {
        return id++;
    }
    // COMMAND INFO
    public int getLength() {
        return collection.size();
    }
    public String getType() {
        return collection.getClass().getTypeName();
    }
    public Date getInitDate() {
        return initDate;
    }

    // COMMAND INSERT
    public void insertByKey(Integer key, StudyGroup studyGroup) {
        used_keys.add(key);
        collection.put(key, studyGroup);
    }

    // COMMAND UPDATE
    public void updateByKey(Integer key, StudyGroup studyGroup) {
        collection.put(key, studyGroup);
    }

    // COMMAND REMOVE
    public void removeByKey(Integer key) {
        collection.remove(key);
    }

    // COMMAND CLEAR
    public void clearCollection() {
        collection.clear();
    }


}
