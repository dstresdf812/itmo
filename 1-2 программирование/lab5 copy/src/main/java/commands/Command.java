package com.dstresdf.common.commands;

import other.CommandStatus;
import other.Request;
import utils.CommandType;
/**
 * Абстрактный класс команды
 * @author dmitrij
 */
public abstract class Command {
    String name;
    String description;
    public static CommandType commandType;
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
    public CommandStatus execute(Request request){
        return CommandStatus.EXIT;
    }

    /**
     * Получить кол-во аргументов команды
     * @return Кол-во аргументов команды
     */
    public abstract CommandType getCommandType();
}
