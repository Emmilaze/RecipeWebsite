package app.db;

import java.sql.*;

public class DataBaseController {
    static Connection c = null;
    static Statement stmt = null;

    public static Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
                    "rey619");
            c.setAutoCommit(true);
            System.out.println("Opened database successfully");
            return c;
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return c;
    }

    public static int getNextId(String field) {
        try {
            stmt = c.createStatement();
            String sql = "SELECT coalesce((SELECT max(id)FROM " + field + ")+1,1);";
            ResultSet rs = stmt.executeQuery(sql);
            int id = 0;
            if (rs.next()) id = rs.getInt(1);

//            rs.close();
//            c.close();
            return id;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return 0;
        }
    }

    public static void deleteRecord(String table, int id) {
        try {
            stmt = c.createStatement();
            String sql = "DELETE FROM " + table + " WHERE id = " + id + ";";
            stmt.executeUpdate(sql);

//            stmt.close();
            //c.commit();
//            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Delete successfully");
    }
}
