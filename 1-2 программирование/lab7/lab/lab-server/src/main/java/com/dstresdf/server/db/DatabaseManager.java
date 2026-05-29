package com.dstresdf.server.db;

import com.dstresdf.common.model.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabaseManager {
    private static final String URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    private HikariDataSource dataSource;
    private UserRepository userRepository;
    private StudyGroupRepository studyGroupRepository;
    private UserService userService;
    private StudyGroupService studyGroupService;

    public DatabaseManager() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(0);
        config.setIdleTimeout(30000);

        try {
            this.dataSource = new HikariDataSource(config);
            initDatabase();
        } catch (Exception e) {
            try {
                if (dataSource != null) {
                    dataSource.close();
                }
                config.setJdbcUrl(System.getenv("SERVER_DB_URL"));
                config.setUsername(System.getenv("SERVER_USERNAME"));
                config.setPassword(System.getenv("SERVER_PASSWORD"));
                this.dataSource = new HikariDataSource(config);
                initDatabase();
            } catch (Exception ex) {
                if (dataSource != null) {
                    dataSource.close();
                }

                System.out.println("серв и локал бд печально все");
                throw new SQLException("серв и локал бд печально все");
            }
        } finally {
            userRepository = new UserRepository(dataSource);
            studyGroupRepository = new StudyGroupRepository(dataSource);
            userService = new UserService(userRepository);
            studyGroupService = new StudyGroupService(studyGroupRepository,  userService);
        }
    }

    public void initDatabase() throws SQLException {
        String SQL_seq_create = "CREATE SEQUENCE IF NOT EXISTS study_group_id_seq START 1";
        String SQL_users_create = "CREATE TABLE IF NOT EXISTS users(" +
                "login TEXT PRIMARY KEY," +
                "hash TEXT NOT NULL," +
                "permissions INTEGER NOT NULL DEFAULT 3)";
        String SQL_groups_create = "CREATE TABLE IF NOT EXISTS study_groups ("
                + "id INTEGER PRIMARY KEY DEFAULT nextval('study_group_id_seq'),"
                + "name TEXT NOT NULL,"
                + "x BIGINT NOT NULL,"
                + "y BIGINT NOT NULL,"
                + "creation_date TIMESTAMP NOT NULL,"
                + "students_count INTEGER NOT NULL,"
                + "expelled_students BIGINT NOT NULL,"
                + "should_be_expelled BIGINT,"
                + "form_of_education TEXT,"
                + "admin_name TEXT,"
                + "admin_weight DOUBLE PRECISION,"
                + "admin_eye_color TEXT,"
                + "admin_hair_color TEXT,"
                + "owner_login TEXT NOT NULL REFERENCES users)";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();) {
            statement.execute(SQL_seq_create);
            statement.execute(SQL_users_create);
            statement.execute(SQL_groups_create);
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public StudyGroupService getStudyGroupService() {
        return studyGroupService;
    }

    public boolean registerUser(String login, String password) throws SQLException, NoSuchAlgorithmException {
        return  userService.register(login, password);
    }

    public boolean checkUser(String login, String password) throws SQLException, NoSuchAlgorithmException {
        if (login == null || password == null) {
            return false;
        }
        return userService.login(login, password);
    }
}