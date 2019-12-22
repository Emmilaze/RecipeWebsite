package app.db;

import app.recipe.Recipe;
import io.sentry.Sentry;

import java.sql.*;
import java.util.HashMap;

public class TableOldRecipesController {

    /**
     * Method return list with all last versions of recipe.
     *
     * @return list with all last versions of recipe.
     */
    public static HashMap<Integer, Recipe> getOldVersionsOfRecipes(int id) {
        String sql = "SELECT * FROM recipes_hist JOIN recipes ON (recipes.id = relates) WHERE relates = ?;";
        HashMap<Integer, Recipe> recipes = new HashMap<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int i = 1;
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    recipes.put(i++, new Recipe(rs.getInt("id"), rs.getString("title"),
                            rs.getString("img"), rs.getString("ingredients"),
                            rs.getString("recipe"), rs.getInt("category"),
                            rs.getTimestamp("publication_date"), rs.getInt("author_id"),
                            rs.getInt("votes"), rs.getBoolean("approved")));
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return null;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }

    public static boolean isExisted(int id) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM recipes_hist " +
                     "WHERE id = ?;")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
            return false;
        }
    }

    /**
     * This method return one recipe by unique number.
     *
     * @return the vote id.
     */
    public static Recipe getRecipe(int id, int versionId) {
        String sql = "SELECT recipes_hist .*, recipes.votes, recipes.author_id, recipes.publication_date, " +
                "recipes.approved FROM recipes_hist JOIN recipes ON (recipes.id = relates) WHERE relates = ? " +
                "AND recipes_hist.id = ?;";
        Recipe recipe = null;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, versionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recipe = new Recipe(rs.getInt("id"), rs.getString("title"),
                            rs.getString("img"), rs.getString("ingredients"),
                            rs.getString("recipe"), rs.getInt("category"),
                            rs.getTimestamp("publication_date"),
                            rs.getInt("author_id"), rs.getInt("votes"),
                            rs.getBoolean("approved"));
                }
                return recipe;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return recipe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return recipe;
        }
    }
}
