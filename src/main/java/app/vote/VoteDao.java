package app.vote;

import app.db.TableRatingController;
import app.db.TableRecipeController;

import java.util.ArrayList;
import java.util.List;

import static app.Main.recipeDao;
import static app.Main.userDao;

public class VoteDao {

//    public List<Vote> votes = new ArrayList<Vote>(TableRatingController.getVotes(userDao.u.getId()));

    public boolean haveVoted(int recipeId){
        if(userDao.u == null)
            return false;
        List<Vote> votes = new ArrayList<>(getVotes());
        for(int i = 0; i < votes.size(); i++){
            if(votes.get(i).getRecipeId() == recipeId) {
                return true;
            }
            else continue;
        }
        return false;
    }

    public List<Vote> getVotes(){
        if(userDao.u == null)
            return null;
        return TableRatingController.getVotes(userDao.u.getId());
    }

    public void addVote(int userId, int recipeId){
        TableRatingController.insertVote(userId, recipeId);
        recipeDao.recipes = TableRecipeController.getRecipes();
    }

    public void deleteVote(int userId, int recipeId){
        TableRatingController.deleteVote(userId, recipeId);
        recipeDao.recipes = TableRecipeController.getRecipes();
    }
}
