package app.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseController {
    public static Connection c = null;
    public static Statement stmt = null;

    public static void connect(){
        try {
            Class.forName("org.postgresql.Driver");
            //изменить название бд
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/NAME", "username", "password");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
        }catch(SQLException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
