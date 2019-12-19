package app.db;

import app.recipe.Recipe;
import io.sentry.Sentry;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TableRecipesUtilController {

    /**
     * This method return statistics
     *
     * @return the hashmap with the statistics.
     */
    public static HashMap<String, Integer> getStatistics() {
        HashMap<String, Integer> map = new HashMap<>();
        String sql = "SELECT start, count(*) FROM " +
                "(SELECT date_trunc('days', publication_date::timestamp)::date as start, " +
                "(date_trunc('days', publication_date::timestamp)+ '6 days'::interval)::date as end " +
                "FROM recipes) data\n" +
                "GROUP BY start ORDER BY 1 ASC;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getString("start"), rs.getInt("count"));
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return map;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return map;
        }
        return map;
    }

    /**
     * This method return the amount of pages.
     */
    public static int getAmountOfPages() {
        int amount = 0;
        String sql = "SELECT count(*) FROM recipes WHERE publication_date < NOW() AND approved = 't';";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
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

    /**
     * This method return the amount of recipes.
     */
    public static int getAmountOfRecipes() {
        int amount = 0;
        String sql = "SELECT count(*) FROM recipes;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
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

    /**
     * This method done the request and return the result.
     */
    public static List<Recipe> getRecipesFromResultSet(PreparedStatement ps) {
        List<Recipe> recipes = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                recipes.add(new Recipe(rs.getInt("id"), rs.getString("title"),
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
        return recipes;
    }
}
