package managers;
import com.fasterxml.jackson.core.*;
import other.StudyGroup;

import java.io.BufferedInputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;

import com.fasterxml.jackson.databind.*;
public class FileManager {
    final private ObjectMapper parser = new ObjectMapper();
    private StudyGroup[] collection = new StudyGroup[0];
    public ArrayList<StudyGroup> ReadFile() {
        try {
            FileInputStream fis = new FileInputStream("input_Test.json");
            BufferedInputStream bis = new BufferedInputStream(fis);
            InputStreamReader isr = new InputStreamReader(bis);
            collection = parser.readValue(isr, StudyGroup[].class);
            // Arrays.stream(collection).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        return new ArrayList<>(Arrays.asList(collection));
    }

    public void saveToFile(LinkedHashMap<Integer, StudyGroup> collection) {
        try {
            System.out.println(collection.toString());
            parser.writeValue(new File("output.json"), collection);
            File a = new File("output.json");
            System.out.println(a.exists());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
