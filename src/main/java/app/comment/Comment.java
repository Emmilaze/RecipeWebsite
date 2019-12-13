package app.comment;

import app.db.TableUserController;

/**
 * Category class for categories.
 */
public class Comment {

    /**
     * Unique comment id.
     */
    private int id;

    /**
     * User, that create comment.
     */
    private int userId;

    /**
     * The recipe to which the comment refers.
     */
    private int recipeId;

    /**
     * Comment content.
     */
    private String text;

    /**
     * The time and date of comment publication.
     */
    private String date;

    /**
     * Constructor - creating new comment with certain parameters.
     *
     * @param id - comment unique id.
     * @param userId - user's id, that leave the comment.
     * @param recipeId - the recipe to which the comment refers.
     * @param text - comment content.
     * @param date - time and date of comment publication.
     */
    public Comment(int id, int userId, int recipeId, String text, String date) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
        this.text = text;
        this.date = date;
    }

    /**
     * This method return the vote id.
     *
     * @return the vote id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method return the vote id.
     *
     * @return the vote id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This method return the vote id.
     *
     * @return the vote id.
     */
    public int getRecipeId() {
        return recipeId;
    }

    /**
     * This method return the vote id.
     *
     * @return the vote id.
     */
    public String getText() {
        return text;
    }

    /**
     * This method return the vote id.
     *
     * @return the vote id.
     */
    public String getDate() {
        return date;
    }

    /**
     * This method return user name from Data Base.
     *
     * @return username.
     */
    public String getUserName() {
        return TableUserController.getUsername(this.getUserId());
    }
}
