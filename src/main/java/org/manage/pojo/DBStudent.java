package org.manage.pojo;
import org.manage.utils.DBUtil;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DBStudent {
    private static Connection connection;

    static {
        try {
            connection = DBUtil.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (id, name, age, sex, birthday) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getSex());
            ps.setDate(5, new Date(student.getBirthday().getTime()));
            return ps.executeUpdate();
        }
    }

    public static int deleteStudent(String id) throws SQLException {
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate();
        }
    }

    public static int updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET name = ?, age = ?, sex = ?, birthday = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getSex());
            ps.setDate(4, new Date(student.getBirthday().getTime()));
            ps.setString(5, student.getId());
            return ps.executeUpdate();
        }
    }

    public static Student selectStudentById(String id) throws SQLException {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("sex"),
                            rs.getDate("birthday")
                    );
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static List<Student> selectAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("sex"),
                        rs.getDate("birthday")
                ));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
