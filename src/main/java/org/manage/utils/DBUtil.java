package org.manage.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/class_management";
    private static final String USER = "root";
    private static final String PASSWORD = "Han0852963741";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载 MySQL 驱动
        } catch (ClassNotFoundException e) {
            throw new SQLException("数据库驱动加载失败", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
