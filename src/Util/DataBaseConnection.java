package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/cart_management?useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
    public static void getConnection()

    {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie à MySQL !");
            conn.close();

        } catch (ClassNotFoundException e) {

            System.out.println("Driver JDBC manquant !");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Erreur SQL !");
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.close();
//            System.out.println("Connexion fermée avec succès !");

        } catch (Exception e) {
            System.out.println("Erreur SQL !");
            e.printStackTrace();
        }
    }
}
