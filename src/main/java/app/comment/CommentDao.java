package app.comment;

import app.db.TableCommentController;

import java.util.List;

/**
 * Class DAO with all method to manipulate with comments.
 */
public class CommentDao {

    /**
     * List with all categories.
     */
    public List<Comment> comments;

    /**
     * This method return the vote id.
     * @param userId - the comment author.
     * @param recipeId - to which recipe was comment created.
     * @param text - comment content.
     */
    public void createComment(int userId, int recipeId, String text){
        TableCommentController.insertComment(userId, recipeId, text);
    }

    /**
     * This method return list of comments by recipe id.
     *
     * @param recipeId - unique recipe number.
     *
     * @return list of comments.
     */
    public List<Comment> getCommentByRecipe(int recipeId){
        return comments = TableCommentController.getAllCommentsByRecipe(recipeId);
    }
}
