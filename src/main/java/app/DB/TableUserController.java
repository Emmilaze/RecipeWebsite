package app.db;

import app.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class controller of user table in Data Base.
 */
public class TableUserController {

    /**
     * Method takes parameters and add new user to the table.
     *
     * @param email    - user's email.
     * @param username - user's username.
     * @param salt     - unique salt.
     * @param password - user's hashed password.
     */
    public static void insertUser(String email, String username, String salt, String password) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users " +
                     "VALUES (?, ?, ?, ?, ?, 0);")) {
            preparedStatement.setInt(1, DataBaseController.getNextId("users"));
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, salt);
            preparedStatement.setString(5, password);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Method takes parameters and change record in the table.
     *
     * @param newValue - takes new value to change.
     * @param id       - user's unique id.
     */
    public static void updateUser(int newValue, int id) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("UPDATE users SET privilege = ? WHERE id = ?;")) {
            preparedStatement.setInt(1, newValue);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Method takes parameters and update user's password in the table.
     *
     * @param salt     - unique salt.
     * @param password - user's hashed password.
     * @param id       - user's id.
     */
    public static void updatePassword(String salt, String password, int id) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("UPDATE users SET salt = ?, password = ? " +
                             "WHERE id = ?;")) {
            preparedStatement.setString(1, salt);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Method return need user.
     *
     * @return object type User.
     */
    public static User getUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?;";
        User user = null;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("email"),
                            rs.getString("user_name"), rs.getString("salt"),
                            rs.getString("password"), rs.getInt("privilege"));
                }
                return user;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method return list with all users.
     *
     * @return list with all users.
     */
    public static List<User> getUsers() {
        String sql = "SELECT * FROM users;";
        List<User> users = new ArrayList<User>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"), rs.getString("email"),
                            rs.getString("user_name"), rs.getString("salt"),
                            rs.getString("password"), rs.getInt("privilege")));
                }
                return users;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method return user's username by id.
     *
     * @param id - user's unique id.
     * @return user's username.
     */
    public static String getUsername(int id) {
        String sql = "SELECT user_name FROM users WHERE id = ?;";
        String username = "";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    username = rs.getString("user_name");
                }
                return username;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method return the amount of users.
     *
     * @return the amount of users.
     */
    public static int getAmountOfUsers() {
        int amount = 0;
        String sql = "SELECT count(*)-1 FROM users;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    amount = rs.getInt("?column?");
                }
                return amount;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return amount;
        }
    }
}
