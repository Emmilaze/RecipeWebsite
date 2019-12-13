package tests;

import app.category.Category;
import app.db.DataBaseController;
import app.db.TableCategoryController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TableCategoryControllerTest {

    private String name = "test";
    private int id;

    @BeforeEach
    public void setUp(){
        TableCategoryController.insertCategory(name);
        id = Objects.requireNonNull(TableCategoryController.getCategoryByName(name)).getId();
    }

    @AfterEach
    public void clean(){
        DataBaseController.deleteRecord("categories", id);
    }

    @Test
    void insertCategory(){
        id = TableCategoryController.getCategoryIdByName(name);
        Category category = new Category(id, name);
        assertEquals(category.getId(), TableCategoryController.getCategoryIdByName(name));
        assertEquals(category.getName(), TableCategoryController.getCategoryNameById(id));
    }

    @Test
    void getCategories() {
        assertNotNull(TableCategoryController.getCategories());
        assertNotEquals(0, TableCategoryController.getCategories().size());
    }

    @Test
    void getCategoryIdByName() {
        assertEquals(id, TableCategoryController.getCategoryIdByName(name));
    }

    @Test
    void getCategoryNameById() {
        assertEquals(name, TableCategoryController.getCategoryNameById(id));
    }

    @Test
    void getCategoryNames() {
        assertNotEquals(0, Objects.requireNonNull(TableCategoryController.getCategoryNames()).size());
    }

    @Test
    void getCategoryByName() {
        Category category = TableCategoryController.getCategoryByName(name);
        assertNotNull(category);
        assertEquals(name, category.getName());
        assertEquals(id, category.getId());
    }

    @Test
    void getAmountRecipesByCategory() {
        assertNotNull(TableCategoryController.getAmountRecipesByCategory());
        assertNotEquals(0, TableCategoryController.getAmountRecipesByCategory().size());
    }

    @Test
    void getCategoryAmount() {
        assertNotEquals(0, TableCategoryController.getCategoryAmount());
    }
}