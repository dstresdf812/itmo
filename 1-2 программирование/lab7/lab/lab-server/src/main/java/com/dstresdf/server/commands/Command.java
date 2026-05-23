package com.dstresdf.server.commands;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Абстрактный класс команды
 * @author dmitrij
 */
public abstract class Command {
    String name;
    String description;
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
    public Response execute(Request request) throws SQLException, NoSuchAlgorithmException {
        return null;
    }

}
