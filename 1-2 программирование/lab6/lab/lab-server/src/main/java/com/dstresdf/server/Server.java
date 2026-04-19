package com.dstresdf.server;

import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.commands.CommandManager;
import com.dstresdf.server.commands.*;
import com.dstresdf.server.io.FileManager;
import com.dstresdf.server.network.ServerManager;


public class Server {
    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager();
        commandManager.add("help", new Help(commandManager));
        commandManager.add("update", new Update(collectionManager));
        commandManager.add("help", new Help(commandManager));
        commandManager.add("info", new Info(collectionManager));
        commandManager.add("show", new Show(collectionManager));
        commandManager.add("insert", new Insert(collectionManager));
        commandManager.add("update", new Update(collectionManager));
        commandManager.add("remove_key", new RemoveKey(collectionManager));
        commandManager.add("clear", new Clear(collectionManager));
        commandManager.add("replace_if_greater", new ReplaceIfGreater(collectionManager));
        commandManager.add("remove_greater_key", new RemoveGreaterKey(collectionManager));
        commandManager.add("average_of_students_count", new AverageOfStudentsCount(collectionManager));
        commandManager.add("print_unique_students_count", new PrintUniqueStudentsCount(collectionManager));
        commandManager.add("print_field_ascending_expelled_students", new PrintFieldAscendingExpelledStudents(collectionManager));
        commandManager.add("history", new History(commandManager));

        collectionManager.SetStudyGroup(fileManager.readFile("input.json"));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> fileManager.saveToFile(collectionManager.getCollection())));
        try {
            ServerManager serverManager = new ServerManager("localhost", 1090);;
            serverManager.start(commandManager);
        } catch (Exception e) {
            System.out.println("Ошибка запуска сервера: " + e.getMessage());
        }
    }
}