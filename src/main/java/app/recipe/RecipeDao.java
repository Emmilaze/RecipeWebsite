package app.recipe;

import app.db.DataBaseController;
import app.db.TableDownloadController;
import app.db.TableRecipeController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class DAO for recipes methods.
 */
public class RecipeDao {

    /**
     * List of recipes from the Data Base.
     */
    public List<Recipe> recipes = new ArrayList<Recipe>();

    /**
     * List of recipes from search. Null, if search don't need.
     */
    public List<Recipe> seachedList = null;

    /**
     * Method to fill the list with posted recipes.
     *
     * @return list of recipes.
     */
    public Iterable<Recipe> getAllRecipes(int pageNumber) {
        return recipes = TableRecipeController.getRecipes(pageNumber);
    }

    /**
     * Method return recipe by id.
     *
     * @param id - recipe id.
     * @return recipe.
     */
    public Recipe getRecipeById(int id) {
        return TableRecipeController.getRecipe(id);
    }

    /**
     * Method sort list of recipes by votes.
     *
     * @return list of sorted recipes.
     */
    public List<Recipe> getPopular(int page) {
        if (seachedList == null) {
            return recipes = TableRecipeController.getPopularRecipes(page);
        } else {
            seachedList.sort(Comparator.comparing(Recipe::getRating).reversed());
            return seachedList;
        }
    }

    /**
     * Method sort list of recipes by date.
     *
     * @return list of sorted recipes.
     */
    public List<Recipe> getNewest(int page) {
        if (seachedList == null) {
            return recipes = TableRecipeController.getRecipes(page);
        } else {
            seachedList.sort(Comparator.comparing(Recipe::getPublicationTime).reversed());
            return seachedList;
        }
    }

    /**
     * Method create recipe with certain parameters. Make record in Data Base.
     *
     * @param name            - recipe name.
     * @param img             - image of recipe.
     * @param ingredients     - recipe ingredients.
     * @param description     - recipe description.
     * @param publicationTime - date and time of publication.
     * @param authorId        - recipe author id.
     */
    public void createRecipe(String name, String img, String ingredients, String description, int category,
                             Date publicationTime, int authorId, boolean approved) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        TableRecipeController.insertRecipe(name, img, ingredients, description, category, format.format(publicationTime),
                authorId, approved);
    }

    /**
     * Method update recipe with certain parameters. Make update in Data Base.
     *
     * @param name        - new recipe name.
     * @param img         - new image of recipe.
     * @param description - new recipe description.
     * @param ingredients - new recipe ingredients.
     * @param id          - recipe id.
     */
    public void updateRecipe(String name, String img, String description, String ingredients, int id, int category,
                             String date, String time) {
        DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        DateFormat dataBaseFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        String str;
        try {
            Date parsedDate  = sourceFormat.parse(date + " " + time);
            str = dataBaseFormat.format(parsedDate);
            TableRecipeController.updateRecipe(name, img, description, ingredients, id, category, str);
        }catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Method call function to delete recipe from Data Base.
     *
     * @param id - recipe id.
     */
    public void deleteRecipe(int id) {
        DataBaseController.deleteRecord("recipes", id);
    }

    /**
     * Method search the recipes by user in the Data Base.
     *
     * @return the list of recipes.
     */
    public List<Recipe> getRecipeByUser(int id) {
        return TableRecipeController.getUserRecipes(id);
    }

    /**
     * Method search the unconfirmed recipes in the Data Base.
     *
     * @return the list of recipes.
     */
    public Iterable<Recipe> getUnconfirmedRecipes() {
        return TableRecipeController.getUnconfirmedRecipes();
    }

    /**
     * Method change recipe status.
     */
    public void approveRecipe(int id) {
        TableRecipeController.approveRecipe(id);
    }

    /**
     * Method return liked recipes.
     */
    public List<Recipe> likedRecipes(int userId) {
        return TableRecipeController.getLikedRecipes(userId);
    }

    /**
     * Method return the amount of recipes.
     *
     * @return amount of recipes.
     */
    public int getAmount() {
        return TableRecipeController.getAmountOfRecipes();
    }

    /**
     * Method return the amount of recipes.
     *
     * @return hashmap with the statistics.
     */
    public HashMap<String, Integer> getStatistics() {
        return TableRecipeController.getStatistics();
    }

    /**
     * Method return the amount of pages.
     *
     * @return amount of pages.
     */
    public int getAmountOfPages() {
        return TableRecipeController.getAmountOfPages();
    }
}
