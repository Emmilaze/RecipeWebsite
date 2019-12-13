package app.db;

import app.vote.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class controller of vote table in Data Base.
 */
public class TableRatingController {

    /**
     * Method insert a record with a vote for recipe from user.
     *
     * @param userId   - user's unique id.
     * @param recipeId - recipe unique id.
     */
    public static void insertVote(int userId, int recipeId) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO voted VALUES(?, ?, ?);")) {
            preparedStatement.setInt(1, DataBaseController.getNextId("voted"));
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, recipeId);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Method gets records with all votes for recipe from user.
     *
     * @param id - user's unique id.
     * @return List of votes.
     */
    public static List<Vote> getVotes(int id) {
        String sql = "SELECT * FROM voted WHERE user_id = ?;";
        List<Vote> votes = new ArrayList<Vote>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    votes.add(new Vote(rs.getInt("id"), rs.getInt("user_id"),
                            rs.getInt("recipe_id")));
                }
                return votes;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return votes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method delete a record with a vote for recipe from user.
     *
     * @param userId   - user's unique id.
     * @param recipeId - recipe unique id.
     */
    public static void deleteVote(int userId, int recipeId) {
        String sql = "DELETE FROM voted WHERE user_id = " + userId + " AND recipe_id = " + recipeId + ";";
        DataBaseController.updateDataBase(sql);
    }

    /**
     * Method gets records with all votes for recipe from user.
     *
     * @param id - user's unique id.
     * @return List of votes.
     */
    public static Vote getVote(int id) {
        String sql = "SELECT * FROM voted WHERE id = ?;";
        Vote vote = null;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vote = new Vote(rs.getInt("id"), rs.getInt("user_id"),
                            rs.getInt("recipe_id"));
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return vote;
    }
}
