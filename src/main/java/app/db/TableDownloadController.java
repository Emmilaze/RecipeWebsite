package app.db;

import app.downloads.Downloads;
import io.sentry.Sentry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDownloadController {

    /**
     * This method create the record about downloading or increment the amount of downloading.
     */
    public static void downloadRecipe(int userId, int recipeId) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("INSERT into down values(default, ?, ?, 1)" +
                     "ON CONFLICT ON CONSTRAINT person_down " +
                     "do update set downloaded = down.downloaded + 1 where down.user_id = ? AND down.recipe_id = ?;")) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, recipeId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, recipeId);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
    }

    /**
     * This method done the request and return the result.
     */
    public static List<Downloads> getTheDownloads() {
        String sql = "SELECT * FROM down";
        List<Downloads> downloads = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    downloads.add(new Downloads(rs.getInt("id"), rs.getInt("user_id"),
                            rs.getInt("recipe_id"), rs.getInt("downloaded")));
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return null;
        }
        return downloads;
    }
}