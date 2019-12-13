package app.vote;
/**
 * Vote class for user's votes.
 */
public class Vote {

    /**
     * Unique vote id.
     */
    private int id;
    /**
     * Unique recipe id.
     */
    private int recipeId;
    /**
     * Unique user's number.
     */
    private int userId;
    /**
     * Constructor - creating new vote with certain parameters.
     *
     * @param id - unique vote id.
     * @param userId - unique user's number.
     * @param recipeId - recipe id.
     */
    public Vote(int id, int userId, int recipeId) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
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
     * This method return the recipe id.
     *
     * @return the recipe id.
     */
    public int getRecipeId() {
        return recipeId;
    }

    /**
     * This method return the user's id.
     *
     * @return the user's id.
     */
    public int getUserId() {
        return userId;
    }
}
