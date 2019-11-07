package app.db;

import app.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.db.DataBaseController.*;


public class TableUserController {

    public static void insertUser(String email, String username, String salt, String password) {
        if(c == null)
            connect();
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO users VALUES (" + DataBaseController.getNextId("users") + ", \'" +
                    email + "\', \'" + username + "\', \'" + salt + "\', \'" + password + "\', " + 1 + ");";
            stmt.executeUpdate(sql);
            //c.commit();

//            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("User created successfully");
    }

    public static void updateUser(String field, String newValue, int id) {
        try {
            stmt = c.createStatement();
            String sql = "UPDATE users SET " + field + " = \'" + newValue + "\' WHERE id = " + id;
            stmt.executeUpdate(sql);
            //c.commit();

//            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("User updated successfully");
    }

    public static void updatePassword(String salt, String password, int id) {
        try {
            stmt = c.createStatement();
            String sql = "UPDATE users SET salt = \'" + salt + "\', password = \'" + password + "\' " +
                    "WHERE id = " + id;
            stmt.executeUpdate(sql);

//            stmt.close();
            //c.commit();
//            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("User updated successfully");
    }

    public static List<User> getUsers() {
        if(c == null)
            connect();
        List<User> users = new ArrayList<User>();
        try {
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("email"),
                        rs.getString("user_name"), rs.getString("salt"),
                        rs.getString("password"), rs.getInt("privilege")));
            }
            return users;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return users;
        }
    }

    public static String getUsername(int id) {
        try {
            ResultSet rs = c.createStatement().executeQuery("SELECT user_name FROM users WHERE id = " + id + ";");
            while (rs.next()) {
                return rs.getString("user_name");
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return "";
        }
        return "";
    }
}
