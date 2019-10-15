package app.DB;

import java.sql.ResultSet;

import static app.DB.DataBaseController.c;
import static app.DB.DataBaseController.connect;
import static app.DB.DataBaseController.stmt;

public class TableRatingController {
    public static void updateRating(int userId, int recipeId) {
        try {
            connect();
            stmt = c.createStatement();
            String sql = "INSERT INTO voted VALUES(default, \"" + userId + "\", \"" + recipeId + "\");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public static boolean haveVoted(int userId, int recipeId) {
        try {
            connect();
            stmt = c.createStatement();
            String sql = "SELECT user FROM voted WHERE recipeId = " + recipeId + ";";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                rs.close();
                stmt.close();
                c.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return true;
        }
    }
}
