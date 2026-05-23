package com.dstresdf.server.commands;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Request;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.collection.CollectionManager;
import com.dstresdf.server.db.DatabaseManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 * Команда 'insert'. Добавляет новый элемент в коллекцию по заданному ключу.
 * @author dmitrij
 */
public class Login extends Command {
    private final DatabaseManager databaseManager;
    public Login(DatabaseManager databaseManager) {
        super("Login (username)", "авторизоваться :))");
        this.databaseManager = databaseManager;

    }

    /**
     * Выполняет команду
     * @param args
     * @param scanner
     * @return Выполнена ли команда.
     */
    public Response execute(Request request) throws SQLException, NoSuchAlgorithmException {
        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;
        String login = request.getLogin();
        String password = request.getPassword();
        try {
            isSuccess = databaseManager.checkUser(login, password);
            message = isSuccess ? "Успешный вход" : ":(((";
        } catch (Exception e){
            isSuccess = false;
            message = "Ошибка авторизации " + e.getMessage();
        }

        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }
}
