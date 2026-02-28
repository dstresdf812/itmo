package commands;

import managers.CommandManager;

import java.util.Scanner;

/**
 * Абстрактный класс команды
 * @author dmitrij
 */
public abstract class Command {
    String name;
    String description;
    public static int argsLen;
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Получить название команды
     * @return Название команды
     */
    public String getName() {
        return name;
    }

    /**
     * Получить описание команды
     * @return Описание команды
     */
    public  String getDescription() {
        return description;
    }

    /**
     * Выполняет команду
     * @return Выполнена ли команда
     */
    public boolean execute(String[] args){
        return false;
    }
//class req {
//        string[] args;
//        elem el;
//        scanner scanner;
//}
    // execute -> interface

    /**
     * Выполняет команду
     * @return Выполнена ли команда
     */
    public boolean execute(String[] args, Scanner scanner) {
        return execute(args);
    }

    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public abstract int getArgsLen();
}
