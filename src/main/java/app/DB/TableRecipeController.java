package app.db;

import app.recipe.Recipe;
import io.sentry.Sentry;

import java.sql.*;

public class TableRecipeController {

    /**
     * Method make record with new recipe in the table.
     *
     * @param name        - recipes name.
     * @param img         - name of file with image for recipe.
     * @param ingredients - recipe ingredients.
     * @param description - recipe description.
     * @param category    - recipe category
     * @param date        - date and time of publication.
     * @param authorId    - author's id.
     * @param approved    - recipe status.
     */
    public static void insertRecipe(String name, String img, String ingredients, String description, int category,
                                    String date, int authorId, boolean approved) {
        String sql = "INSERT INTO recipes VALUES (?, ?, ?, ?, ?, ?, to_timestamp(?,'yyyy-MM-dd HH24-MI'), ?, 0, ?);";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, DataBaseController.getNextId("recipes"));
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, img);
            preparedStatement.setString(4, ingredients);
            preparedStatement.setString(5, description);
            preparedStatement.setInt(6, category);
            preparedStatement.setString(7, date);
            preparedStatement.setInt(8, authorId);
            preparedStatement.setBoolean(9, approved);

            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
    }

    /**
     * Method make update the recipe in the table.
     *
     * @param name        - recipe name.
     * @param img         - name of file for recipe image.
     * @param description - recipe description.
     * @param ingredients - recipe ingredients.
     * @param id          - recipe unique id.
     * @param category    - recipe category.
     * @param date        - publication date.
     */
    public static void updateRecipe(String name, String img, String description, String ingredients,
                                    int id, int category, String date) {
        String sql = "UPDATE recipes SET title = ?, img = ?, ingredients = ?, recipe = ?, category = ?, " +
                "publication_date = to_timestamp(?,'yyyy-MM-dd HH24-MI') WHERE id = ?;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, img);
            preparedStatement.setString(3, ingredients);
            preparedStatement.setString(4, description);
            preparedStatement.setInt(5, category);
            preparedStatement.setString(6, date);
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
    }

    /**
     * This method return one recipe by unique number.
     *
     * @return the vote id.
     */
    public static Recipe getRecipe(int id) {
        String sql = "SELECT * FROM recipes WHERE id = ?;";
        Recipe recipe = null;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
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

    /**
     * This method approve the recipe.
     */
    public static void approveRecipe(int id) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("UPDATE recipes SET approved = true " +
                     "WHERE id = ?;")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
    }

    /**
     * This method return the name of the recipe by id.
     *
     * @return the name.
     */
    public static String getRecipeName(int id) {
        String sql = "SELECT * FROM recipes WHERE id = ?;";
        String string = null;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    string = rs.getString("title");
                }
                return string;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return string;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return string;
        }
    }

    public static boolean isExisted(int id) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM recipes " +
                     "WHERE id = ?;")) {
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
            return false;
        }
    }
}
