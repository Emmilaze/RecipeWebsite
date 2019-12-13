package tests;

import app.category.Category;
import app.db.DataBaseController;
import app.db.TableCategoryController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseControllerTest {

    @Test
    void getNextId(){
        assertNotEquals(0, DataBaseController.getNextId("users"));
    }

    @Test
    void deleteRecord(){
        TableCategoryController.insertCategory("test");
        int id = TableCategoryController.getCategoryIdByName("test");
        DataBaseController.deleteRecord("categories", id);
    }

    @Test
    void getDates() {
        String [] dates = DataBaseController.getDates();
        assertNotNull(dates);
        assertEquals("jdbc:postgresql://localhost:5432/postgres", dates[0]);
        assertEquals("postgres", dates[1]);
        assertEquals("password", dates[2]);
    }

    @Test
    void updateDataBase() {
        TableCategoryController.insertCategory("test");
        int id = TableCategoryController.getCategoryIdByName("test");
        String sql = "UPDATE categories SET title = 'test1' WHERE id = " + id + ";";
        DataBaseController.updateDataBase(sql);
        Category category = TableCategoryController.getCategoryByName("test1");
        assertNotNull(category);
        assertEquals("test1", category.getName());
        assertEquals(id, category.getId());
        DataBaseController.deleteRecord("categories", id);
    }
}