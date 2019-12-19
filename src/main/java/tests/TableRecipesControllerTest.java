package tests;

import app.db.DataBaseController;
import app.db.TableIngredientsController;
import app.db.TableRecipeController;
import app.db.TableRecipesController;
import app.recipe.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableRecipesControllerTest {

    private int id;
    private String name;
    private String img;
    private String ing;
    private String description;
    private int category;
    private String date;
    private int userId;
    private boolean approve;

    @BeforeEach
    public void setUp(){
        id = DataBaseController.getNextId("recipes");
        name = "name";
        img = "logo.png";
        ing = "ing";
        description = "recipe";
        category = 1;
        date = "2019.05.13 00:00";
        userId = 1;
        approve = false;
        TableRecipeController.insertRecipe(name, img, ing, description, category, date, userId, approve);
    }

    @AfterEach
    public void cleanUp(){
        DataBaseController.deleteRecord("recipes", id);
    }

    @Test
    void insertRecipe() {
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertNotNull(recipe);
        assertEquals(id, recipe.getId());
        assertEquals(name, recipe.getName());
        assertEquals(img, recipe.getImage());
        assertEquals(ing, recipe.getIngredients());
        assertEquals(description, recipe.getDescription());
        assertEquals(category, recipe.getCategory());
        assertEquals("13.05.2019 00:00", recipe.getPublicationTime());
        assertEquals(userId, recipe.getAuthorId());
        assertEquals(approve, recipe.isApproved());
    }

    @Test
    void updateRecipe() {
        String name = "test";
        String img = "1.jpg";
        String description = "r";
        String ing = "qwerty";
        int category = 2;
        String date = "2019.03.13 00:00";

        TableRecipeController.updateRecipe(name, img, description, ing, id, category, date);
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertNotNull(recipe);
        assertEquals(id, recipe.getId());
        assertEquals(name, recipe.getName());
        assertEquals(img, recipe.getImage());
        assertEquals(ing, recipe.getIngredients());
        assertEquals(description, recipe.getDescription());
        assertEquals(category, recipe.getCategory());
        assertEquals("13.03.2019 00:00", recipe.getPublicationTime());
        assertEquals(userId, recipe.getAuthorId());
        assertEquals(approve, recipe.isApproved());
    }

    @Test
    void getRecipes() {
        assertNotNull(TableRecipesController.getRecipes(1));
        assertNotEquals(0, TableRecipesController.getRecipes(1).size());
    }

    @Test
    void searchRecipe() {
//        TableRecipeController.approveRecipe(id);
//        Recipe recipe = TableRecipeController.getRecipe(id);
//        assertTrue(recipe.isApproved());
//
//        assertNotNull(TableRecipeController.searchRecipe(name, ing));
//        assertNotEquals(0, TableRecipeController.searchRecipe(name, ing).size());
//
//        assertNotNull(TableRecipeController.searchRecipe(name, ""));
//        assertNotEquals(0, TableRecipeController.searchRecipe(name, "").size());
//
//        assertNotNull(TableRecipeController.searchRecipe(null, ing));
//        assertNotEquals(0, TableRecipeController.searchRecipe(null, ing).size());
    }

    @Test
    void getIngredients() {
        assertNotNull(TableIngredientsController.getIngredients());
        assertNotEquals(0, TableIngredientsController.getIngredients().size());
    }

    @Test
    void getRecipe() {
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertNotNull(recipe);
        assertEquals(id, recipe.getId());
        assertEquals(name, recipe.getName());
        assertEquals(img, recipe.getImage());
        assertEquals(ing, recipe.getIngredients());
        assertEquals(description, recipe.getDescription());
        assertEquals(category, recipe.getCategory());
        assertEquals("13.05.2019 00:00", recipe.getPublicationTime());
        assertEquals(userId, recipe.getAuthorId());
        assertEquals(approve, recipe.isApproved());
    }

    @Test
    void getUserRecipes() {
        assertNotNull(TableRecipesController.getUserRecipes(userId));
        assertNotEquals(0, TableRecipesController.getUserRecipes(userId).size());
    }

    @Test
    void getUnconfirmedRecipes() {
        assertNotNull(TableRecipesController.getUnconfirmedRecipes());
        assertNotEquals(0, TableRecipesController.getUnconfirmedRecipes().size());
    }

    @Test
    void approveRecipe() {
        TableRecipeController.approveRecipe(id);
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertTrue(recipe.isApproved());
    }

    @Test
    void getRecipesFromResultSet() {
//        String sql = "SELECT * FROM recipes WHERE id = " + id + ";";
//        List<Recipe> recipes = TableRecipeController.getRecipesFromResultSet(sql);
//        assertNotNull(recipes);
//        assertNotEquals(0, recipes.size());
    }
}