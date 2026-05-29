package com.dstresdf.server.commands;

import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.db.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по заданному ключу.
 * @author dmitrij
 */
public class Login extends Command {
    private final UserService userService;
    public Login(UserService userService) {
        super("Login (username)", "авторизоваться :))");
        this.userService = userService;
    }

    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда.
     */
    public Response execute(Request request) throws SQLException, NoSuchAlgorithmException {
        return userService.login(request.getLogin(), request.getPassword());
    }
}
