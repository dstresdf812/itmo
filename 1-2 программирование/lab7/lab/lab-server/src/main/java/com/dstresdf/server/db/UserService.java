package com.dstresdf.server.db;

import com.dstresdf.common.model.StudyGroup;
import com.dstresdf.common.network.Response;
import com.dstresdf.server.util.Permissions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Response login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        String hash = userRepository.getHash(username);

        boolean isSuccess;
        String message;
        List<StudyGroup> studyGroups = null;

        if (hash == null) {
            isSuccess = false;
        } else if (!hash.equals(hashedPassword)) {
            isSuccess = false;
        } else {
            isSuccess = true;
        }
        message = isSuccess ? "Успешный вход" : ":(((";
        Response response = new Response(isSuccess, message, studyGroups);
        return response;

        if (hash == null) {
            return false;
        }
        if (!hash.equals(hashedPassword)) {
            return false;
        }
        return true;
    }

    public Response register(String username, String password) throws NoSuchAlgorithmException, SQLException {
        String hash = hashPassword(password);
        boolean isSuccess = userRepository.addNewUser(username, hash);
        String message;
        message = isSuccess ? "Регистрация прошла успешно" : "Анлак";
        List<StudyGroup> studyGroups = null;
        Response response = new Response(isSuccess, message, studyGroups);
        return response;
    }

    public int getPermissions(String username) throws SQLException {
        return userRepository.getPermissions(username);
    }

    public boolean hasPermission(String username, int permission) throws SQLException {
        return (getPermissions(username) & permission) == permission;
    }
    public boolean isAdmin(String username) throws SQLException {
        return hasPermission(username, Permissions.ADMIN);
    }

    public boolean canRead(String username) throws SQLException {
        return isAdmin(username) || hasPermission(username, Permissions.READ);
    }

    public boolean canWrite(String username) throws SQLException {
        return isAdmin(username) || hasPermission(username, Permissions.WRITE);
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-224");
        byte[] hash =  md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
