package tests;

import app.category.CategoryDao;
import app.db.DataBaseController;
import app.db.TableUserController;
import app.recipe.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    private int id;
    private Recipe recipe;
    private Date date = new Date(0);
    CategoryDao categoryDao = new CategoryDao();

    @BeforeEach
    public void setUp(){
        id = DataBaseController.getNextId("recipes");
        recipe = new Recipe(id, "name", "logo.png", "ing", "desc", 1,
                date, 1, 0,false);
    }

    @Test
    void getImg() {
        assertEquals("/../img/logo.png", recipe.getImg());
    }

    @Test
    void getPublicationTime() {
        assertEquals("01.01.1970 02:00", recipe.getPublicationTime());
    }

    @Test
    void getAuthorUsername() {
        assertEquals("admin", TableUserController.getUsername(recipe.getAuthorId()));
    }

    @Test
    void getArrayOfIngredients() {
        assertEquals("ing", recipe.getArrayOfIngredients().get(0));
    }

    @Test
    void getPubTime() {
        assertEquals("02:00", recipe.getPubTime());
    }

    @Test
    void getCategoryName() {
        categoryDao.getCategoryById(recipe.getCategory());
    }

    @Test
    void getPubDate() {
        assertEquals("01/01/1970", recipe.getPubDate());
    }
}