package com.dstresdf.server;

import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.commands.CommandManager;
import com.dstresdf.server.commands.*;
import com.dstresdf.server.db.DatabaseManager;
import com.dstresdf.server.db.StudyGroupService;
import com.dstresdf.server.db.UserRepository;
import com.dstresdf.server.db.UserService;
import com.dstresdf.server.io.FileManager;
import com.dstresdf.server.network.ServerManager;

import java.sql.SQLException;
import java.util.HashMap;


public class Server {
    public static void main(String[] args) throws SQLException {
        CommandManager commandManager = new CommandManager();
        DatabaseManager databaseManager = new DatabaseManager();
        CollectionManager collectionManager = new CollectionManager();
        UserService userService = databaseManager.getUserService();
        StudyGroupService studyGroupService = databaseManager.getStudyGroupService();
        studyGroupService.setCollectionManager(collectionManager);

        commandManager.add("help", new Help(commandManager));
        commandManager.add("update", new Update(studyGroupService));
        commandManager.add("info", new Info(collectionManager));
        commandManager.add("show", new Show(collectionManager));
        commandManager.add("insert", new Insert(studyGroupService));
        commandManager.add("remove_key", new RemoveKey(studyGroupService));
        commandManager.add("clear", new Clear(studyGroupService));
        commandManager.add("replace_if_greater", new ReplaceIfGreater(studyGroupService));
        commandManager.add("remove_greater_key", new RemoveGreaterKey(studyGroupService));
        commandManager.add("average_of_students_count", new AverageOfStudentsCount(collectionManager));
        commandManager.add("print_unique_students_count", new PrintUniqueStudentsCount(collectionManager));
        commandManager.add("print_field_ascending_expelled_students", new PrintFieldAscendingExpelledStudents(collectionManager));
        commandManager.add("history", new History(commandManager));
        commandManager.add("register", new Register(userService));
        commandManager.add("login", new Login(userService));
        // collectionManager.SetStudyGroup(fileManager.readFile("input.json"));
        collectionManager.SetStudyGroup(studyGroupService.getCollection());
        try {
            ServerManager serverManager = new ServerManager("localhost", 1090, databaseManager);;
            serverManager.start(commandManager);
        } catch (Exception e) {
            System.out.println("Ошибка запуска сервера: " + e.getMessage());
        }
    }
}

// fix retry help +
// не держать connection с бд connectionpool +
// вынести в отдельные классы db manager controller service repository
// проверка на принадлежность (роль привилегия)
// help кто угодно
// show читать
// update insert читать писать и свой элемент
// админ со всеми правами
// один пользователь может купить элемент коллекции другого