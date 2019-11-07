package app.recipe;

import app.db.DataBaseController;
import app.db.TableRecipeController;

import java.text.SimpleDateFormat;
import java.util.*;

public class RecipeDao {

    public List<Recipe> recipes = new ArrayList<Recipe>(TableRecipeController.getRecipes());

    public List<Recipe> seachedList = null;

    public List<Recipe> unPosted = new ArrayList<Recipe>(TableRecipeController.getUnposted());

    public Iterable<Recipe> getAllRecipes() {
        recipes = TableRecipeController.getRecipes();
        return recipes;
    }

    public Recipe getRecipeById(int id) {
        return recipes.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public Recipe getRandomRecipe() {
        return recipes.get(new Random().nextInt(recipes.size()));
    }

    public List<Recipe> getPopular() {
        if (seachedList == null) {
            recipes.sort(Comparator.comparing(Recipe::getRating).reversed());
            return recipes;
        } else {
            seachedList.sort(Comparator.comparing(Recipe::getRating).reversed());
            return seachedList;
        }
    }

    public List<Recipe> getNewest() {
        if (seachedList == null) {
            recipes.sort(Comparator.comparing(Recipe::getPublicationTime).reversed());
            return recipes;
        } else {
            seachedList.sort(Comparator.comparing(Recipe::getPublicationTime).reversed());
            return seachedList;
        }
    }

    public void createRecipe(String name, String img, String ingredients, String description,
                             Date publicationTime, int authorId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        TableRecipeController.insertRecipe(name, img, ingredients, description, format.format(publicationTime),
                authorId);
        Recipe recipe = new Recipe(DataBaseController.getNextId("recipes") - 1, name, img, ingredients,
                description,
                publicationTime, authorId, 0);

        if (publicationTime.compareTo(new Date()) < 0)
            recipes.add(recipe);
        else
            unPosted.add(recipe);
    }

    public void updateRecipe(String name, String img, String description, String ingredients, int id) {
        TableRecipeController.updateRecipe(name, img, description, ingredients, id);
        for(int i = 0; i<recipes.size(); i++){
            Recipe recipe = recipes.get(i);
            if(recipe.getId() == id)
            {
                recipe.setName(name);
                recipe.setImg(img);
                recipe.setDescription(description);
                recipe.setIngredients(ingredients);
                return;
            }
        }
    }

    public void deleteRecipe(int id) {
        DataBaseController.deleteRecord("recipes", id);
    }

    public Iterable<Recipe> getUnposted() {
        return unPosted;
    }
}
