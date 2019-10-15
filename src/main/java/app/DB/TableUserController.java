package app.DB;

import static app.DB.DataBaseController.*;

public class TableUserController {
    public static void insertUser(String email, String username, String password) {
        try {
            connect();
            stmt = c.createStatement();
            String sql = "INSERT INTO users VALUES (" + DataBaseController.getNextId("users") + ", \"" + email +
                    "\", \"" + username + "\", \"" + password + "\", " + 1 + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public static void updateUser(String field, String newValue, int id) {
        try {
            connect();
            stmt = c.createStatement();
            String sql = "UPDATE users SET " + field + " = \"" + newValue + "\" WHERE id = " + id;
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records updated successfully");
    }
}
