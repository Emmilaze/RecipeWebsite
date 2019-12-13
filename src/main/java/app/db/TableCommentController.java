package app.db;

import app.comment.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.db.DataBaseController.*;

/**
 * Category class for categories.
 */
public class TableCommentController {

    /**
     * Method insert a record with a user's comment to the table.
     *
     * @param userId   - user's unique id.
     * @param recipeId - recipe unique id.
     * @param text     - comment content.
     */
    public static void insertComment(int userId, int recipeId, String text) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO comments VALUES(?, ?, ?, ?, now());")) {
            preparedStatement.setInt(1, DataBaseController.getNextId("comments"));
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, recipeId);
            preparedStatement.setString(4, text);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    /**
     * Method reads and fills the list of objects of comment type.
     *
     * @param recipeId - recipe unique id.
     * @return list of user's comment.
     */
    public static List<Comment> getAllCommentsByRecipe(int recipeId) {
        String sql = "SELECT * FROM comments WHERE recipeid = ?;";
        ArrayList comments = new ArrayList();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, recipeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comments.add(new Comment(rs.getInt("id"), rs.getInt("userId"),
                            rs.getInt("recipeId"), rs.getString("comment"),
                            rs.getString("cDate")));
                }
                return comments;
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method reads and fills the list of objects of comment type.
     *
     * @param id - comment unique id.
     * @return user's comment.
     */
    public static Comment getComment(int id) {
        String sql = "SELECT * FROM comments WHERE id = ?;";
        Comment comment = null;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comment = new Comment(rs.getInt("id"), rs.getInt("userId"),
                            rs.getInt("recipeId"), rs.getString("comment"),
                            rs.getString("cDate"));
                }

            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return comment;
    }
}
