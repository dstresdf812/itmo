package com.dstresdf.server.db;

import com.zaxxer.hikari.HikariDataSource;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private HikariDataSource dataSource;

    public UserRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getHash(String login) throws SQLException {
        String SQL = "SELECT hash FROM users WHERE login = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return rs.getString("hash");
        }
    }

    public boolean addNewUser(String login, String hash) throws SQLException, NoSuchAlgorithmException {
        String SQL = "INSERT INTO users(login, hash) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, login);
            ps.setString(2, hash);
            return ps.executeUpdate() == 1;
        }
    }
    public int getPermissions(String login) throws SQLException {
        String SQL = "SELECT permissions FROM users WHERE login = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt("permissions");
        }
    }
}