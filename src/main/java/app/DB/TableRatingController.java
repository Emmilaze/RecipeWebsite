package app.db;

import app.vote.Vote;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.db.DataBaseController.c;
import static app.db.DataBaseController.stmt;

public class TableRatingController {

    public static void insertVote(int userId, int recipeId) {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO voted VALUES(default, \'" + userId + "\', \'" +
                    recipeId + "\');";
            stmt.executeUpdate(sql);
            //c.commit();

//          stmt.close();
//          c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Records created successfully");
    }

    public static List<Vote> getVotes(int id) {
        List<Vote> votes = new ArrayList<Vote>();
        try {
            ResultSet rs = c.createStatement().executeQuery("SELECT * FROM voted WHERE user_id=" + id + ";");
            while (rs.next()) {
                votes.add(new Vote(rs.getInt("id"), rs.getInt("user_id"),
                        rs.getInt("recipe_id")));
            }
            return votes;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return votes;
        }
    }

    public static void deleteVote(int userId, int recipeId) {
        try {
            stmt = c.createStatement();
            String sql = "DELETE FROM voted WHERE user_id = " + userId + " AND recipe_id = " + recipeId + ";";
            stmt.executeUpdate(sql);

//            stmt.close();
            //c.commit();
//            c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Done successfully");
    }
}
