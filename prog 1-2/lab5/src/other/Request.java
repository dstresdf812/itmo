package other;

import java.util.Scanner;

public class Request {
    // добавить передачу команды
    // record mb
    private final int key;
    private final String fileName;
    private final StudyGroup studyGroup;

    public Request(int key, String fileName, Scanner scanner, StudyGroup studyGroup) {
        this.key = key;
        this.fileName = fileName;
        this.studyGroup = studyGroup;
    }
    // расширение request для execute_script

    public int getKey() {
        return key;
    }
    public String getFileName() {
        return fileName;
    }
    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    @Override
    public String toString() {
        return "Request{" +
                "key=" + key +
                ", fileName='" + fileName + '\'' +
                ", studyGroup=" + studyGroup +
                '}';
    }
}
