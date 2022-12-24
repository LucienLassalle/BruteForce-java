import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import com.mysql.jdbc.Driver;

public class basededonnee {
    public static void basededonne(String hash, String mdp){
        // L'essaie de connexion à votre base de donées
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hash?useSSL=false","root","");
            Statement stmt=con.createStatement();
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }


    }
    public static void insertSQL(String mdp, String hash) throws SQLException {


    }
}
