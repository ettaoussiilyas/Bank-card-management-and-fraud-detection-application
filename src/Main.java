import UI.MenuPrincipal;
import db.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DataBaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Connexion à la base de données réussie!");
                conn.close();
                
                MenuPrincipal menu = new MenuPrincipal();
                menu.afficherMenu();
                
            } else {
                System.err.println("Impossible de se connecter à la base de données!");
                System.err.println("Vérifiez votre configuration MySQL.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur de base de données: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur inattendue: " + e.getMessage());
        }
    }
}