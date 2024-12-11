package org.manage.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库工具类
 */
public class DBUtil {
    /**
     * 数据库连接信息
     */
    private static final String URL = "jdbc:mysql://localhost:3306/class_management";
    
    /**
     * 数据库用户名
     */
    private static final String USER = "root";

    /**
     * 数据库密码
     */
    private static final String PASSWORD = "Han0852963741";

    public static Connection getConnection() throws SQLException {
        // 加载数据库驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 加载 MySQL 驱动
        } catch (ClassNotFoundException e) {
            throw new SQLException("数据库驱动加载失败", e);
        }
        // 获取数据库连接
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
