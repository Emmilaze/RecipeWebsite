package app.db;

import app.recipe.Recipe;
import io.sentry.Sentry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.db.TableRecipesUtilController.getRecipesFromResultSet;

/**
 * Class controller of recipe table in Data Base.
 */
public class TableRecipesController {

    /**
     * Method return list with all posted recipes.
     *
     * @return list with all posted recipes.
     */
    public static List<Recipe> getRecipes(int pageNumber) {

        String sql = "SELECT * FROM recipes WHERE publication_date < NOW() AND approved = 't'" +
                " ORDER BY publication_date DESC limit 9 offset (?-1)*9;";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageNumber);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }

    /**
     * This method return the recipes created by current user.
     *
     * @return recipes list.
     */
    public static List<Recipe> getUserRecipes(int id) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM recipes " +
                     "WHERE author_id = ? order by publication_date DESC;")) {
            preparedStatement.setInt(1, id);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }

    /**
     * This method return the upconfirmed recipes.
     *
     * @return recipes list.
     */
    public static List<Recipe> getUnconfirmedRecipes() {
        String sql = "SELECT * FROM recipes " +
                "WHERE approved = false order by publication_date DESC;";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }

    /**
     * This method return the liked recipes by user.
     *
     * @return recipes list.
     */
    public static List<Recipe> getLikedRecipes(int userId) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("SELECT recipes.* FROM recipes " +
                     "JOIN voted ON (recipe_id = recipes.id) WHERE user_id = ?;")) {
            preparedStatement.setInt(1, userId);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }

    /**
     * This method return the most popular recipes.
     *
     * @return recipes list sort by popular.
     */
    public static List<Recipe> getPopularRecipes(int pageNumber) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM recipes " +
                     "WHERE publication_date < NOW() AND approved = 't'" +
                     " ORDER BY votes DESC limit 9 offset (?-1)*9;")) {
            preparedStatement.setInt(1, pageNumber);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }
}
