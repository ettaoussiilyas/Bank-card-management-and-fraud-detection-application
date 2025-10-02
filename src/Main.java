import Util.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection conn = DataBaseConnection.getConnection();
        if (conn != null){
            System.out.println("Connexion réussie à MySQL ! Main");
        }else{
            System.out.println("Connexion échouée à MySQL ! Main");
        }

    }
}