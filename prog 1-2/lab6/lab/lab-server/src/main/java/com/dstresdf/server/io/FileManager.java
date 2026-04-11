package com.dstresdf.server.io;
import com.fasterxml.jackson.core.*;
import com.dstresdf.common.model.StudyGroup;

import java.io.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FileManager {
    final private ObjectMapper parser = new ObjectMapper();
    private StudyGroup[] collection = new StudyGroup[0];
    public FileManager() {
        parser.registerModule(new JavaTimeModule());
    }

    public ArrayList<StudyGroup> readFile(String fileName) {
        ArrayList<StudyGroup> studyGroups = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            InputStreamReader isr = new InputStreamReader(bis);
            JsonParser jp = parser.createParser(isr);

            while (jp.nextToken() != null) {
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

    public void saveToFile(Map<Integer, StudyGroup> collection) {
        try {
            parser.writeValue(new File("input.json"), collection);
            System.out.println("Коллекция сохранена");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
