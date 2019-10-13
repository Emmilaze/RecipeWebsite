package app.recipe;

import java.util.*;

public class RecipeDao {
    private final List<Recipe> recipes = new ArrayList<>(Arrays.asList(
            new Recipe(1, "1", "Описание", "Ингридиенты", new Date(), "Автор", 1),
            new Recipe(1, "2", "Описание", "Ингридиенты", new Date(), "Автор", 0),
            new Recipe(1, "3", "Описание", "Ингридиенты", new Date(), "Автор", 2),
            new Recipe(1, "4", "Описание", "Ингридиенты", new Date(), "Автор", 3)
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

    public Recipe getPopular(){
        recipes.sort(Comparator.comparing(Recipe::getRating).reversed());
        return recipes.get(0);
    }

    public Recipe getNewest(){
        recipes.sort(Comparator.comparing(Recipe::getPublicationTime));
        return recipes.get(0);
    }
}
