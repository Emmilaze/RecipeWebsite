package app.DB;


import static app.DB.DataBaseController.*;

public class TableRecipeController {

    public static void insertRecipe(String name, String description, String ingredients, String category, String date,
                                    String authorId) {
        try {
            connect();
            stmt = c.createStatement();
            String sql = "INSERT INTO recipes VALUES (" + DataBaseController.getNextId("recipes") + ", \"" + name
                    + "\", \"" + description + "\", \"" + ingredients + "\", \"" + category + "\", \"" + date
                    + "\", \"" + authorId + "\", " + 0 + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records create successfully");
    }

    public static void updateRecipe(String name, String description, String ingredients, String category, int id) {
        try {
            connect();
            stmt = c.createStatement();
            String sql = "UPDATE recipes SET name = \"" + name + "\", description =  \"" + description +
                    "\", ingredients = \"" + ingredients + "\", category = \"" + category + "\" WHERE id = " + id + ";";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records updated successfully");
    }

    public static void upRating(int id){
        try {
            connect();
            stmt = c.createStatement();
            String sql = "UPDATE recipes SET viewed+=1 WHERE id = " + id + ";";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records updated successfully");
    }
}
