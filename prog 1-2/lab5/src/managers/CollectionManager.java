package managers;

import other.StudyGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Управление коллекцией.
 * @author dmitrij
 */
public class CollectionManager {
    public static int id = 0;
    public LinkedHashMap<Integer, StudyGroup> collection = new LinkedHashMap<>();
    public ArrayList<Integer> used_keys = new ArrayList<>();
    public Date initDate = new Date();
    public CollectionManager() {

    }

    /**
     * Добавить элемент в коллецию по ключу.
     * @param studyGroups
     */
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

    public int getLength() {
        return collection.size();
    }

    public String getType() {
        return collection.getClass().getTypeName();
    }

    public Date getInitDate() {
        return initDate;
    }

    public void insertByKey(Integer key, StudyGroup studyGroup) {
        used_keys.add(key);
        collection.put(key, studyGroup);
    }

    public void updateByKey(Integer key, StudyGroup studyGroup) {
        collection.put(key, studyGroup);
    }

    public void removeByKey(Integer key) {
        collection.remove(key);
    }

    public void clearCollection() {
        collection.clear();
    }
}
