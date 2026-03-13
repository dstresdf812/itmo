package managers;
import com.fasterxml.jackson.core.*;
import other.StudyGroup;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


/**
 * Чтение из файла/запись в файл.
 * @author dmitrij
 */
public class FileManager {
    final private ObjectMapper parser = new ObjectMapper();
    private StudyGroup[] collection = new StudyGroup[0];
    public FileManager() {
        parser.registerModule(new JavaTimeModule());
    }

    /**
     * Чтение из файла по имени.
     * @param fileName
     * @return Массив элементов типа StudyGroup.
     */
    public ArrayList<StudyGroup> readFile(String fileName) {
        ArrayList<StudyGroup> studyGroups = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            InputStreamReader isr = new InputStreamReader(bis);
            JsonParser jp = parser.createParser(isr);

            if (jp.nextToken() != JsonToken.START_ARRAY) {
                System.out.println("Ожидался массив объектов StudyGroup");
                return studyGroups;
            }

            while (jp.nextToken() != JsonToken.END_ARRAY) {
                try {
                    StudyGroup studyGroup = parser.readValue(jp, StudyGroup.class);

                    if (studyGroup == null) continue;

                    if (!studyGroup.check()) {
                        System.out.println("Невалидный StudyGroup пропущен");
                        continue;
                    }
                    System.out.println("Прочитан ID: " + studyGroup.getId());
                    studyGroups.add(studyGroup);
                } catch (Exception e) {
                    System.out.println("Ошибка при чтении элемента StudyGroup: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e + "FILE MANAGER ERRRRRRRRREOROOREO");
        }
        return studyGroups;
    }

    /**
     * Считывание скрипта из файла.
     * @param fileName
     * @return Массив строк команд.
     */
    public ArrayList<String> ReadScript(String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            InputStreamReader isr = new InputStreamReader(bis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lines;
    }

    public void saveToFile(LinkedHashMap<Integer, StudyGroup> collection) {
        try {
            parser.writeValue(new File("output.json"), collection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
