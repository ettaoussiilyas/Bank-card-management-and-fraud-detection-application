package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/cart_management?useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";

    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("success");
        return connection;

    }
}
