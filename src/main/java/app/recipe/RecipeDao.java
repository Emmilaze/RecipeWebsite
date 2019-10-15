package app.recipe;

import app.user.User;

import java.util.*;

public class RecipeDao {
    private final List<Recipe> recipes = new ArrayList<>(Arrays.asList(
            new Recipe(1, "1", "Описание", "Ингридиенты", "Закуска", new Date(), 1, 1),
            new Recipe(2, "2", "Описание", "Ингридиенты", "Напиток", new Date(), 2, 0),
            new Recipe(3, "3", "Описание", "Ингридиенты", "Завтрак", new Date(), 3, 2),
            new Recipe(4, "4", "Описание", "Ингридиенты","Десерт", new Date(), 4, 3)
    ));

    public Iterable<Recipe> getAllRecipes() {
        return recipes;
    }

    public Recipe getRecipeById(int id) {
        return recipes.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public Recipe getRandomRecipe() {
        return recipes.get(new Random().nextInt(recipes.size()));
    }

    public List<Recipe> getPopular(){
        recipes.sort(Comparator.comparing(Recipe::getRating).reversed());
        return recipes;
    }

    public List<Recipe> getNewest(){
        recipes.sort(Comparator.comparing(Recipe::getPublicationTime));
        return recipes;
    }
}
