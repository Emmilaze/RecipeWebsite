package app.vote;

import app.db.TableRatingController;
import app.db.TableRecipeController;
import app.user.User;

import java.util.ArrayList;
import java.util.List;

import static app.Main.recipeDao;

/**
 * Vote class for votes.
 */
public class VoteDao {

    /**
     * The method checks if the user voted for this recipe.
     *
     * @param recipeId accepts the recipe id.
     *
     * @return the result of cheking user's vote.
     */
    public boolean haveVoted(User user, int recipeId){
        if(user == null)
            return false;
        List<Vote> votes = new ArrayList<>(getVotes(user));
        for(int i = 0; i < votes.size(); i++){
            if(votes.get(i).getRecipeId() == recipeId) {
                return true;
            }
            else continue;
        }
        return false;
    }

    /**
     * The method checks the user's session and looks at the database for a list of his votes.
     *
     * @return the list of user's votes.
     *
     */
    public List<Vote> getVotes(User user){
        return TableRatingController.getVotes(user.getId());
    }

    /**
     * The method calls the function of writing to the voice database.
     *
     * @param userId takes user's id.
     * @param recipeId takes recipe id.
     *
     */
    public void addVote(int userId, int recipeId){
        TableRatingController.insertVote(userId, recipeId);
    }

    /**
     * The method calls the function of deleting the vote from database.
     *
     * @param userId takes user's id.
     * @param recipeId takes recipe id.
     *
     */
    public void deleteVote(int userId, int recipeId){
        TableRatingController.deleteVote(userId, recipeId);
    }
}
