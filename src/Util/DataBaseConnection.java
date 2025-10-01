package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/cart_management?useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie à MySQL !");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC manquant !");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Erreur SQL !");
            e.printStackTrace();
            return null;
        }
    }
}
