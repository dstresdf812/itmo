package com.dstresdf.server.db;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class StudyGroupService {
    private StudyGroupRepository studyGroupRepository;
    private UserService userService;
    private CollectionManager collectionManager;
    public StudyGroupService(StudyGroupRepository studyGroupRepository, UserService userService) {
        this.studyGroupRepository = studyGroupRepository;
        this.userService = userService;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public List<StudyGroup> getCollection() throws SQLException {
        return studyGroupRepository.getCollection();
    };

    public Response clearCollection(String login) throws SQLException {
        List<Integer> keys;
        boolean isAdmin = userService.isAdmin(login);
        if (isAdmin) {
            keys = collectionManager.getCollection().keySet().stream().toList();
        } else if(userService.canWrite(login)) {
            keys = collectionManager.getUsersElems(login);
        } else {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }

        int count = 0;
        for (Integer key : keys) {
            if (studyGroupRepository.removeStudyGroup(key, login, isAdmin)) {
                count++;
                collectionManager.removeByKey(key);
            }
        }
        return new Response(true, "Удалено " + count + " элементов", null);
    }
    public Response insertStudyGroup(String login, Integer key, StudyGroup studyGroup) throws SQLException {
        if (userService.isAdmin(login) || userService.canWrite(login)) {
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
        } else {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }
    }

    public Response updateStudyGroup(String login, Integer key, StudyGroup elem) throws SQLException {
        boolean isAdmin = userService.isAdmin(login);

        if (!userService.canWrite(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }
        String message;
        if (!collectionManager.containsKey(key)) {
            message = "Элемента с таким ключом не существует :(";
            return new Response(false, message, null);
        }
        elem.setId(key);
        if (isAdmin) {
            elem.setOwnerLogin(collectionManager.getByKey(key).getOwnerLogin());
        } else {
            elem.setOwnerLogin(login);
        }
        if (studyGroupRepository.updateStudyGroup(key, elem, isAdmin)) {
            collectionManager.updateByKey(key, elem);
            return new Response(true, "Элемент обновлен!", null);
        }
        return new Response(false, "Элемент не обновен!", null);
    }

    public Response removeGreaterKey(String login, Integer key) throws SQLException {
        boolean isAdmin = userService.isAdmin(login);
        if (!userService.canWrite(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }
        boolean  isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        int count = 0;

        for (Integer elem_key : collectionManager.getCollection().keySet()) {
            if (elem_key > key) {
                if (studyGroupRepository.removeStudyGroup(elem_key, login, isAdmin)) {
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

        boolean isAdmin = userService.isAdmin(login);
        if (!userService.canWrite(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
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
            elem.setId(key);
            if (isAdmin) {
                elem.setOwnerLogin(collectionManager.getByKey(key).getOwnerLogin());
            } else {
                elem.setOwnerLogin(login);
            }
            isSuccess = studyGroupRepository.updateStudyGroup(key, elem, isAdmin);
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
        boolean isAdmin = userService.isAdmin(login);
        if (!userService.canWrite(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }
        if (studyGroupRepository.removeStudyGroup(key, login, isAdmin)) {
            collectionManager.removeByKey(key);
                return new Response(true, "Элемент удален!", null);
        } else {
            return new Response(false, "Элемент не удален!", null);
        }
    }

    public Response show(String login) throws SQLException {
        boolean isAdmin = userService.isAdmin(login);

        if (!userService.canRead(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = collectionManager.collection.values().stream()
                .sorted(Comparator.comparing(StudyGroup::getName))
                .toList();

        isSuccess = true;
        message = "Команда выполнена! Элементы коллекции";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }

    public Response info(String login) throws SQLException {
        boolean isAdmin = userService.isAdmin(login);

        if (!userService.canRead(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }

        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        int size = collectionManager.getLength();
        Date initDate = collectionManager.getInitDate();
        String type = collectionManager.getType();
        message = "Тип: " + type + "\nДата" +
                " инициализации: " + initDate + "\nРазмер: " + size;
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }

    public Response averageOfStudentsCount(String login) throws SQLException {
        boolean isAdmin = userService.isAdmin(login);
        if (!userService.canRead(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }

        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;
        message = String.valueOf(collectionManager.getCollection().values().stream()
                .mapToInt(StudyGroup::getStudentsCount)
                .average());
        message = "Среднее значение: " + message;
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }

    public Response printUniqueStudentsCount(String login) throws SQLException {
        boolean isAdmin = userService.isAdmin(login);
        if (!userService.canRead(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }

        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        isSuccess = true;
        message = collectionManager.getCollection().values().stream()
                .map(StudyGroup::getStudentsCount)
                .distinct()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }

    public Response printFieldAscendingExpelledStudents(String login) throws SQLException {
        boolean isAdmin = userService.isAdmin(login);
        if (!userService.canRead(login) && !isAdmin) {
            return new Response(false, "У вас нет прав на выполнение этой команды!", null);
        }

        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        message = collectionManager.getCollection().values().stream()
                .map(StudyGroup::getExpelledStudents)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        isSuccess = true;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
