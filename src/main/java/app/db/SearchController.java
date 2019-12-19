package app.db;

import app.recipe.Recipe;
import io.sentry.Sentry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.db.TableRecipesUtilController.getRecipesFromResultSet;

public class SearchController {

    /**
     * Method make request to find recipes with certain parameters.
     *
     * @param name        - recipe name.
     * @param ingredients - recipe ingredients.
     * @param page        - number of page.
     * @return list with all searched recipes.
     */
    public static List<Recipe> searchRecipe(String name, String ingredients, int page) {
        String sql = getRequest(ingredients, name);
        if (!ingredients.isEmpty() && name.isEmpty()) {
            return searchByIngredients(sql + " limit 9 offset (?-1)*9;", page);
        } else if (!ingredients.isEmpty() && !name.isEmpty()) {
            return searchByName(sql + " limit 9 offset (?-1)*9;", name, page);
        } else {
            return searchByName(sql + " limit 9 offset (?-1)*9;", name, page);
        }
    }

    /**
     * Method make request to find recipes with certain parameters.
     *
     * @param page - number of page.
     * @return list with all searched recipes.
     */
    public static List<Recipe> searchByIngredients(String sql, int page) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, page);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }

    /**
     * Method make request to find recipes with certain parameters.
     *
     * @param page - number of page.
     * @return list with all searched recipes.
     */
    public static List<Recipe> searchByName(String sql, String name, int page) {
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, page);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
        return recipes;
    }

    public static String getRequest(String ingredients, String name) {
        String sql;
        if (!ingredients.isEmpty() && name.isEmpty()) {
            sql = "SELECT recipes.* FROM search_ingr " +
                    "JOIN recipes ON (search_ingr.recipe = recipes.id) " +
                    "WHERE search @@ to_tsquery(regexp_replace(\'" + ingredients + "\','\\s',',','g')) " +
                    "AND publication_date < NOW() " +
                    "AND approved = 't'";
            return sql;
        } else if (!ingredients.isEmpty() && !name.isEmpty()) {
            sql = "SELECT recipes.* FROM search_ingr JOIN recipes ON (search_ingr.recipe = recipes.id)" +
                    "WHERE search @@ to_tsquery(regexp_replace(\'" + ingredients + "\','\\s',',','g')) " +
                    "AND publication_date < NOW() AND approved = 't' AND title IN (SELECT title FROM (SELECT title, " +
                    "word_similarity(upper(title), upper(?)) per " +
                    "FROM recipes WHERE publication_date < NOW() AND approved = 't') res where per > 0.15)";
            return sql;
        } else {
            sql = "SELECT id, title, img, ingredients, recipe, category, publication_date, author_id, votes, approved " +
                    "FROM (SELECT recipes.*, word_similarity(upper(title), upper(?)) per " +
                    "FROM recipes WHERE publication_date < NOW() AND approved = 't') res where per > 0.15";
            return sql;
        }
    }

    public static int getAmountOfSearch(String ingredients, String name){
        String sql = "SELECT count(*) FROM (" + getRequest(ingredients, name) + ") res;";
        int amount = 0;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            if(!name.isEmpty())
                ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    amount = rs.getInt("count");
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return amount;
        }
        return amount;
    }
}
