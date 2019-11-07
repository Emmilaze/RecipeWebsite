package app.db;

import app.recipe.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.db.DataBaseController.connect;
import static app.db.DataBaseController.stmt;
import static app.db.DataBaseController.c;


public class TableRecipeController {

    public static void insertRecipe(String name, String img, String ingredients, String description,
                                    String date, int authorId) {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO recipes VALUES (" + DataBaseController.getNextId("recipes") +
                    ", \'" + name + "\', \'" + img + "\', \'" + ingredients + "\', \'" + description +
                    "\', \'" + date + "\', " + authorId + ", " + 0 + ");";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records create successfully");
    }

    public static void updateRecipe(String name, String img, String description, String ingredients,
                                    int id) {
        try {
            stmt = c.createStatement();
            String sql = "UPDATE recipes SET title=\'" + name + "\', img = \'" + img + "\', ingredients=\'" +
                    ingredients + "\', recipe=\'" + description + "\' WHERE id=" + id + ";";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records updated successfully");
    }

    public static List<Recipe> getRecipes() {
        if(c == null)
            connect();
            List<Recipe> recipes = new ArrayList<>();

            try {
                ResultSet rs = c.createStatement().executeQuery("SELECT * FROM recipes " +
                        "WHERE publication_date <= NOW() order by publication_date DESC;");
                while (rs.next()) {
                    recipes.add(new Recipe(rs.getInt("id"), rs.getString("title"),
                            rs.getString("img"), rs.getString("ingredients"),
                            rs.getString("recipe"), rs.getTimestamp("publication_date"),
                            rs.getInt("author_id"), rs.getInt("votes")));
                }
                return recipes;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return recipes;
            }

    }

    public static List<Recipe> getUnposted() {
        List<Recipe> unPostedRecipes = new ArrayList<>();
        try {
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM recipes " +
                    "WHERE publication_date > NOW();");
            while (rs.next()) {
                unPostedRecipes.add(new Recipe(rs.getInt("id"), rs.getString("title"),
                        rs.getString("img"), rs.getString("ingredients"),
                        rs.getString("recipe"), rs.getDate("publication_date"),
                        rs.getInt("author_id"), rs.getInt("votes")));
            }
            return unPostedRecipes;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return unPostedRecipes;
        }
    }

    public static List<Recipe> searchRecipe(String name, String ingredients) {
        List<Recipe> recipes = new ArrayList<>();
        String str;
        if(ingredients != "") {
            str = "SELECT * FROM search_ingr " +
                    "JOIN recipes ON (search_ingr.recipe = recipes.id ) " +
                    "WHERE search @@ to_tsquery(\'" + ingredients + "\') AND publication_date < NOW()";
            if(!(name == null))
                str += "AND upper(title) like upper('%" + name + "%');";
            else
                str += ";";
        }
        else
            str = "SELECT * FROM recipes WHERE upper(title) like upper('%" + name + "%') AND publication_date < NOW();";

        try {
            ResultSet rs = c.createStatement().executeQuery(str);
            while (rs.next()) {
                recipes.add(new Recipe(rs.getInt("id"), rs.getString("title"),
                        rs.getString("img"), rs.getString("ingredients"),
                        rs.getString("recipe"), rs.getTimestamp("publication_date"),
                        rs.getInt("author_id"), rs.getInt("votes")));
            }
            return recipes;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return recipes;
        }
    }

    public static List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();
        String str = "SELECT recipe FROM ingred;";
        try {
            ResultSet rs = c.createStatement().executeQuery(str);
            while (rs.next()) {
                ingredients.add(rs.getString("recipe"));
            }
            return ingredients;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return ingredients;
        }
    }
}
