package com.dstresdf.server.db;

import com.dstresdf.common.model.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = System.getenv("DB_URL");
    private static final String USERNAME = System.getenv("USERNAME");
    private static final String PASSWORD = System.getenv("PASSWORD");

    private HikariDataSource dataSource;

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
        }
    }
    public void initDatabase() throws SQLException {
        String SQL_seq_create = "CREATE SEQUENCE IF NOT EXISTS study_group_id_seq START 1";
        String SQL_users_create = "CREATE TABLE IF NOT EXISTS users(" +
                "login TEXT PRIMARY KEY," +
                "hash TEXT NOT NULL)";
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

    public boolean registerUser(String login, String password) throws SQLException, NoSuchAlgorithmException {
        String SQL = "INSERT INTO users VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, login);
            ps.setString(2, hashPassword(password));
            return ps.executeUpdate() == 1;
        }
    }

    public boolean checkUser(String login, String password) throws SQLException, NoSuchAlgorithmException {
        String SQL = "SELECT hash FROM users WHERE login = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return false;
            }
            return rs.getString("hash").equals(hashPassword(password));
        }
    }

    public List<StudyGroup> getCollection() throws SQLException {
        String SQL = "SELECT * FROM study_groups";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            List<StudyGroup> studyGroups = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                studyGroups.add(readStudyGroup(rs));
            }
            return studyGroups;
        }
    };

    public StudyGroup readStudyGroup(ResultSet rs) throws SQLException {
        StudyGroup studyGroup = new StudyGroup();
        Coordinates coordinates = new Coordinates();
        Person person = new Person();
        studyGroup.setId(rs.getInt("id"));
        studyGroup.setName(rs.getString("name"));

        coordinates.setX(rs.getLong("x"));
        coordinates.setY(rs.getLong("y"));
        studyGroup.setCoordinates(coordinates);

        studyGroup.setCreationDate(rs.getTimestamp("creation_date").toInstant().atZone(ZoneId.systemDefault()));
        studyGroup.setStudentsCount(rs.getInt("students_count"));
        studyGroup.setExpelledStudents(rs.getInt("expelled_students"));
        studyGroup.setShouldBeExpelled(rs.getLong("should_be_expelled"));
        String form = rs.getString("form_of_education");
        if (form != null) {
            studyGroup.setFormOfEducation(FormOfEducation.valueOf(form));
        }
        String adminName = rs.getString("admin_name");
        Double adminWeight = rs.getDouble("admin_weight");
        String adminEyeColor = rs.getString("admin_eye_color");
        String adminHairColor = rs.getString("admin_hair_color");
        if (adminName != null) {
            person.setName(adminName);
            person.setWeight(adminWeight);
            person.setEyeColor(EyeColor.valueOf(adminEyeColor));
            person.setHairColor(HairColor.valueOf(adminHairColor));
        }
        studyGroup.setGroupAdmin(person);
        studyGroup.setOwnerLogin(rs.getString("owner_login"));
        return studyGroup;
    }

    public int insertStudyGroup(StudyGroup studyGroup) throws SQLException {
        String SQL = "INSERT INTO study_groups(" +
                "name, x, y, creation_date, students_count, expelled_students, " +
                "should_be_expelled, form_of_education, admin_name," +
                "admin_weight, admin_eye_color, admin_hair_color, " +
                "owner_login)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            fillStatement(ps, studyGroup);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id");
        }
    }

    public boolean updateStudyGroup(StudyGroup studyGroup) throws SQLException {
        String SQL = "UPDATE study_groups SET "
                + "name = ?, x = ?, y = ?, creation_date = ?, "
                + "students_count = ?, expelled_students = ?, should_be_expelled = ?, form_of_education = ?, "
                + "admin_name = ?, admin_weight = ?, admin_eye_color = ?, admin_hair_color = ?, owner_login = ? "
                + "WHERE id = ? AND owner_login = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            fillStatement(ps,studyGroup);
            ps.setInt(14, studyGroup.getId());
            ps.setString(15, studyGroup.getOwnerLogin());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean removeStudyGroup(int key, String ownerLogin) throws SQLException {
        String SQL = "DELETE FROM study_groups WHERE id = ? AND owner_login = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, key);
            ps.setString(2, ownerLogin);
            return ps.executeUpdate() == 1;
        }
    }
    public void fillStatement(PreparedStatement ps, StudyGroup studyGroup) throws SQLException {

        ps.setString(1, studyGroup.getName());
        ps.setLong(2,studyGroup.getCoordinates().getX());
        ps.setLong(3,studyGroup.getCoordinates().getY());
        ps.setTimestamp(4, Timestamp.from(studyGroup.getCreationDate().toInstant()));
        ps.setInt(5,studyGroup.getStudentsCount());
        ps.setLong(6,studyGroup.getExpelledStudents());
        ps.setLong(7, studyGroup.getShouldBeExpelled());
        if (studyGroup.getFormOfEducation() == null) {
            ps.setString(8, null);
        } else {
            ps.setString(8, studyGroup.getFormOfEducation().name());
        }
        Person admin = studyGroup.getGroupAdmin();
        if (admin == null) {
            ps.setString(9, null);
            ps.setNull(10, java.sql.Types.DOUBLE);
            ps.setString(11, null);
            ps.setString(12, null);
        } else {
            ps.setString(9, admin.getName());
            ps.setDouble(10, admin.getWeight());
            ps.setString(11, admin.getEyeColor() == null ? null : admin.getEyeColor().name());
            ps.setString(12, admin.getHairColor() == null ? null : admin.getHairColor().name());
        }
        ps.setString(13, studyGroup.getOwnerLogin());


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