package managers;
import com.fasterxml.jackson.core.*;
import other.StudyGroup;

import java.io.*;

import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.StreamReadFeature;

import static com.fasterxml.jackson.core.JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION;

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
    public ArrayList<StudyGroup> ReadFile(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            InputStreamReader isr = new InputStreamReader(bis);
            collection = parser.readValue(isr, StudyGroup[].class);
        } catch (Exception e) {
            System.out.println(e + "ASDADSAD");
        }
        return new ArrayList<>(Arrays.asList(collection));
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
