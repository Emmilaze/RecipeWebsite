package app.db;

import app.recipe.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class controller of recipe table in Data Base.
 */
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
        }
    }

    /**
     * Method return list with all posted recipes.
     *
     * @return list with all posted recipes.
     */
    public static List<Recipe> getRecipes(int pageNumber) {

        String sql = "SELECT * FROM recipes WHERE publication_date < NOW() AND approved = 't'" +
                " ORDER BY publication_date DESC limit ?*9 offset (?-1)*9;";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, pageNumber);
            preparedStatement.setInt(2, pageNumber);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return recipes;
    }

    /**
     * Method make request to find recipes with certain parameters.
     *
     * @param name        - recipe name.
     * @param ingredients - recipe ingredients.
     * @return list with all searched recipes.
     */
    public static List<Recipe> searchRecipe(String name, String ingredients) {
        String sql;
        if (!ingredients.isEmpty()) {
            sql = "SELECT recipes.* FROM search_ingr " +
                    "JOIN recipes ON (search_ingr.recipe = recipes.id) " +
                    "WHERE search @@ to_tsquery(regexp_replace(\'" + ingredients + "\','\\s',',','g')) " +
                    "AND publication_date < NOW() " +
                    "AND approved = 't' ";
            if (!name.isEmpty())
                sql += "AND title IN (SELECT title FROM (SELECT title, " +
                        "word_similarity(upper(title), upper(?)) per " +
                        "FROM recipes WHERE publication_date < NOW() AND approved = 't') res where per > 0.03);";
            else
                sql += ";";
        } else
            sql = "SELECT id, title, img, ingredients, recipe, category, publication_date, author_id, votes, approved " +
                    "FROM (SELECT recipes.*, word_similarity(upper(title), upper(?)) per " +
                    "FROM recipes WHERE publication_date < NOW() AND approved = 't') res where per > 0.03;";

        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            if (!name.isEmpty())
                preparedStatement.setString(1, name);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return recipes;
    }

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
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
                return recipe;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return recipe;
        }
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
        }
        return recipes;
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
        }
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
            return null;
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
        }
        return recipes;
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
                return string;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return string;
        }
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
                return amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return amount;
        }
        return amount;
    }

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
                return map;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }

    /**
     * This method return the amount of pages.
     */
    public static int getAmountOfPages() {
        int amount = 0;
        String sql = "SELECT 1+(count(*)-count(*)%9)/9 FROM recipes;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    amount = rs.getInt("?column?");
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return amount;
        }
        return amount;
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
                     " ORDER BY votes DESC limit ?*9 offset (?-1)*9;")) {
            preparedStatement.setInt(1, pageNumber);
            preparedStatement.setInt(2, pageNumber);
            recipes = getRecipesFromResultSet(preparedStatement);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return recipes;
    }
}
