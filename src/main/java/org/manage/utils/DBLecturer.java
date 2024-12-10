package org.manage.utils;

import org.manage.entity.Lecturer;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 教师数据库操作类
 */
public class DBLecturer {
    private static Connection connection;

    static {
        try {
            connection = DBUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addLecturer(Lecturer lecturer) throws SQLException {
        String sql = "INSERT INTO lecturer (id, name, age, sex, birthday, course) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lecturer.getId());
            ps.setString(2, lecturer.getName());
            ps.setInt(3, lecturer.getAge());
            ps.setString(4, lecturer.getSex());
            ps.setDate(5, new Date(lecturer.getBirthday().getTime()));
            ps.setString(6, lecturer.getCourse());
            return ps.executeUpdate();
        }
    }

    public static int deleteLecturer(String id) throws SQLException {
        String sql = "DELETE FROM lecturer WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate();
        }
    }

    public static int updateLecturer(Lecturer lecturer) throws SQLException {
        String sql = "UPDATE lecturer SET name = ?, age = ?, sex = ?, birthday = ?, course = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lecturer.getName());
            ps.setInt(2, lecturer.getAge());
            ps.setString(3, lecturer.getSex());
            ps.setDate(4, new Date(lecturer.getBirthday().getTime()));
            ps.setString(5, lecturer.getCourse());
            ps.setString(6, lecturer.getId());
            return ps.executeUpdate();
        }
    }

    public static Lecturer selectLecturerById(String id) throws SQLException {
        String sql = "SELECT * FROM lecturer WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Lecturer(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("sex"),
                            rs.getString("course"),
                            rs.getDate("birthday")
                    );
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static List<Lecturer> selectAllLecturers() throws SQLException {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturer";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lecturers.add(new Lecturer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("sex"),
                        rs.getString("course"),
                        rs.getDate("birthday")
                ));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return lecturers;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
