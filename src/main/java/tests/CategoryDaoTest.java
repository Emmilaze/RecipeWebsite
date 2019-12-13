package tests;

import app.category.Category;
import app.category.CategoryDao;
import app.db.DataBaseController;
import app.db.TableCategoryController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {

    private String name = "test";
    private int id;
    private CategoryDao categoryDao = new CategoryDao();

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
    void getCategories() {
        assertNotNull(categoryDao.getCategories());
    }

    @Test
    void getCategoryById() {
        assertEquals(name, categoryDao.getCategoryById(id));
    }

    @Test
    void getCategoryByName() {
        assertEquals(id, categoryDao.getCategoryByName(name));
    }

    @Test
    void getCategoriesName() {
        assertNotNull(categoryDao.getCategoriesName());
    }

    @Test
    void createCategory() {
        Category category = TableCategoryController.getCategoryByName(name);
        assertNotNull(category);
        assertEquals(name, category.getName());
        assertEquals(id, category.getId());
    }
}