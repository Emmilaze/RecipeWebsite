package app.db;

import io.sentry.Sentry;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

/**
 * Class controller for connection to Data Base.
 */
public class DataBaseController {

    public static String host = Objects.requireNonNull(getDates())[0];
    public static String user = Objects.requireNonNull(getDates())[1];
    public static String password = Objects.requireNonNull(getDates())[2];

    /**
     * Method gets next unique id from the need table.
     *
     * @param field - determines in which field you need to get the next ID.
     * @return number of next free id.
     */
    public static int getNextId(String field) {

        String sql = "SELECT coalesce((SELECT max(id) FROM " + field + ") + 1, 1);";

        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                int id = 0;
                if (rs.next()) id = rs.getInt(1);
                return id;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return 0;
        }
    }

    /**
     * Method delete the record from need table in the Data Base.
     *
     * @param table - Determines in which table the deleting will be
     * @param id    - field id.
     */
    public static void deleteRecord(String table, int id) {
        String sql = "DELETE FROM " + table + " WHERE id = ?;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
    }

    /**
     * Method gets dates about connection to the data base.
     */
    public static String[] getDates() {
        Properties property = new Properties();
        //"./src/main/resources/WEB_INF/config.properties"
        try (FileInputStream fis = new FileInputStream("./config.properties")) {
            property.load(fis);

            String host = property.getProperty("db.host");
            String login = property.getProperty("db.login");
            String password = property.getProperty("db.password");
            return new String[]{host, login, password};
        } catch (IOException e) {
            System.err.println("File not found!");
            Sentry.capture(e);
            return null;
        }
    }

    /**
     * Method makes changes in the tables by making request using the string.
     */
    public static void updateDataBase(String sql) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
    }
}
