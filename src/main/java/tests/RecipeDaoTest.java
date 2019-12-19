package tests;

import app.db.DataBaseController;
import app.db.TableRecipeController;
import app.db.TableRecipesController;
import app.recipe.Recipe;
import app.recipe.RecipeDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RecipeDaoTest {
    RecipeDao recipeDao = new RecipeDao();
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
        date = "13.05.2019 00:00";
        userId = 1;
        approve = false;
        TableRecipeController.insertRecipe(name, img, ing, description, category, date, userId, approve);
    }

    @AfterEach
    public void cleanUp(){
        DataBaseController.deleteRecord("recipes", id);
    }

    @Test
    void getAllRecipes() throws SQLException {
        assertNotNull(recipeDao.getAllRecipes(1));
    }

    @Test
    void getRecipeById() {
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertNotNull(recipe);
        assertEquals(id, recipe.getId());
        assertEquals(name, recipe.getName());
        assertEquals(img, recipe.getImage());
        assertEquals(ing, recipe.getIngredients());
        assertEquals(description, recipe.getDescription());
        assertEquals(category, recipe.getCategory());
        assertEquals(date, recipe.getPublicationTime());
        assertEquals(userId, recipe.getAuthorId());
        assertEquals(approve, recipe.isApproved());
    }

    @Test
    void getPopular() {
    }

    @Test
    void getNewest() {
    }

    @Test
    void createRecipe() {
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertNotNull(recipe);
        assertEquals(id, recipe.getId());
        assertEquals(name, recipe.getName());
        assertEquals(img, recipe.getImage());
        assertEquals(ing, recipe.getIngredients());
        assertEquals(description, recipe.getDescription());
        assertEquals(category, recipe.getCategory());
        assertEquals(date, recipe.getPublicationTime());
        assertEquals(userId, recipe.getAuthorId());
        assertEquals(approve, recipe.isApproved());
    }

    @Test
    void updateRecipe() {
        String title = "test";
        String img = "1.jpg";
        String description = "r";
        String ing = "qwerty";
        int category = 2;
        String date = "13/03/2019";
        String time = "00:00";

        recipeDao.updateRecipe(title, img, description, ing, id, category, date, time);
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertNotNull(recipe);
        assertEquals(title, recipe.getName());
        assertEquals(img, recipe.getImage());
        assertEquals(ing, recipe.getIngredients());
        assertEquals(description, recipe.getDescription());
        assertEquals(category, recipe.getCategory());
        assertEquals("13.03.2019 00:00", recipe.getPublicationTime());
    }

    @Test
    void deleteRecipe() {
        int currentRecipeId = DataBaseController.getNextId("recipes");
        TableRecipeController.insertRecipe(name, img, ing, description, category, date, userId, approve);
        recipeDao.deleteRecipe(currentRecipeId);
    }

    @Test
    void getRecipeByUser() {
        assertNotNull(recipeDao.getRecipeByUser(userId));
        assertNotEquals(0, recipeDao.getRecipeByUser(userId).size());
    }

    @Test
    void getUnconfirmedRecipes() {
        assertNotNull(recipeDao.getUnconfirmedRecipes());
    }

    @Test
    void approveRecipe() {
        recipeDao.approveRecipe(id);
        Recipe recipe = TableRecipeController.getRecipe(id);
        assertTrue(recipe.isApproved());
    }

    @Test
    void likedRecipes() {
        assertNotNull(recipeDao.likedRecipes(id));
    }

    @Test
    void getAmount() {
        assertNotEquals(0, recipeDao.getAmount());
    }

    @Test
    void getStatistics() {
        assertNotNull(recipeDao.getStatistics());
    }
}