package app.DB;

import java.sql.*;

public class DataBaseController {
    public static Connection c = null;
    public static Statement stmt = null;

    public static void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            //TODO: изменить название бд подключить библиотеку
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/NAME", "username", "password");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int getNextId(String field) {
        try {
            connect();
            stmt = c.createStatement();
            String sql = "SELECT * MAX(id)+1 FROM " + field + ";";
            ResultSet rs = stmt.executeQuery(sql);
            int id = 0;
            if (rs.next()) id = rs.getInt(1);
            rs.close();
            stmt.close();
            c.close();
            return id;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return 0;
        }
    }

    public static void deleteRecord(String table, int id){
        try {
            connect();
            stmt = c.createStatement();
            String sql = "DELETE FROM " + table + " WHERE id = " + id + ";";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage());
        }
        System.out.println("Done successfully");
    }
}
