package com.dstresdf.server.db;

import com.dstresdf.common.network.Response;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(password);
        String hash = userRepository.getHash(username);
        if (hash == null) {
            return false;
        }
        if (!hash.equals(hashedPassword)) {
            return false;
        }
        return true;
    }

    public boolean register(String username, String password) throws NoSuchAlgorithmException, SQLException {
        String hash = hashPassword(password);
        boolean t = userRepository.addNewUser(username, hash);
        return t;
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
