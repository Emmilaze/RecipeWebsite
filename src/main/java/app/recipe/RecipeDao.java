package app.recipe;

import app.db.*;
import io.sentry.Sentry;

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
     * Flag. Need sorting or not.
     */
    public boolean sort = false;

    /**
     * Method to fill the list with posted recipes.
     *
     * @return list of recipes.
     */
    public Iterable<Recipe> getAllRecipes(int pageNumber) {
        return recipes = TableRecipesController.getRecipes(pageNumber);
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
            return recipes = TableRecipesController.getPopularRecipes(page);
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
            return recipes = TableRecipesController.getRecipes(page);
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
            Date parsedDate = sourceFormat.parse(date + " " + time);
            str = dataBaseFormat.format(parsedDate);
            TableRecipeController.updateRecipe(name, img, description, ingredients, id, category, str);
        } catch (Exception e) {
            System.out.println(e);
            Sentry.capture(e);
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
        return TableRecipesController.getUserRecipes(id);
    }

    /**
     * Method search the unconfirmed recipes in the Data Base.
     *
     * @return the list of recipes.
     */
    public Iterable<Recipe> getUnconfirmedRecipes() {
        return TableRecipesController.getUnconfirmedRecipes();
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
        return TableRecipesController.getLikedRecipes(userId);
    }

    /**
     * Method return the amount of recipes.
     *
     * @return amount of recipes.
     */
    public int getAmount() {
        return TableRecipesUtilController.getAmountOfRecipes();
    }

    /**
     * Method return the amount of recipes.
     *
     * @return hashmap with the statistics.
     */
    public HashMap<String, Integer> getStatistics() {
        return TableRecipesUtilController.getStatistics();
    }

    /**
     * Method return the amount of pages.
     *
     * @return amount of pages.
     */
    public int getAmountOfPages() {
        int allRecipes = TableRecipesUtilController.getAmountOfPages();
        if(allRecipes < 9) return 1;
        if (allRecipes % 9 == 0)
            return allRecipes / 9;
        else
            return allRecipes / 9 + 1;
    }

    /**
     * Method return exist the recipe or not.
     *
     * @return true or false.
     */
    public boolean isExisted(int id) {
        return TableRecipeController.isExisted(id);
    }

    public List<Recipe> search(String ingr, String name, int page){
        return SearchController.searchRecipe(ingr, name, page);
    }

    /**
     * Method return the amount of pages.
     *
     * @return amount of pages.
     */
    public int getPagesForSearch(String ingredients, String name) {
        int allRecipes = SearchController.getAmountOfSearch(ingredients, name);
        if(allRecipes < 9) return 1;
        if (allRecipes % 9 == 0)
            return allRecipes / 9;
        else
            return allRecipes / 9 + 1;
    }

    /**
     * Method return list with all last versions of recipe.
     *
     * @return list with all last versions of recipe.
     */
    public HashMap<Integer, Recipe> getOldVersions(int id) {
        return TableOldRecipesController.getOldVersionsOfRecipes(id);
    }

    /**
     * Method return exist the version of recipe or not.
     *
     * @return true or false.
     */
    public boolean isExistVersion(int id) {
        return TableOldRecipesController.isExisted(id);
    }

    /**
     * Method return recipe by id.
     *
     * @param id - recipe id.
     * @return recipe.
     */
    public Recipe getRecipeByVersion(int id, int versionId) {
        return TableOldRecipesController.getRecipe(id, versionId);
    }
}
