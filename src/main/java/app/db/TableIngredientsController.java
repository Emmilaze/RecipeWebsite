package app.db;

import io.sentry.Sentry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableIngredientsController {

    /**
     * Method return list with all ingredients from all recipes.
     *
     * @return list with all ingredients.
     */
    public static List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        String sql = "SELECT recipe FROM ingred;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingredients.add(rs.getString("recipe"));
                }
                return ingredients;
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
    }
}
