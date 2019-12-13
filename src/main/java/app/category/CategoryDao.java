package app.category;

import app.db.TableCategoryController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class DAO for category methods.
 */
public class CategoryDao {

    /**
     * List with all categories.
     */
    public List<Category> categories = new ArrayList<Category>(TableCategoryController.getCategories());

    /**
     * Method to get all last categories.
     *
     * @return list with categories.
     */
    public Iterable<Category> getCategories(){
        categories = TableCategoryController.getCategories();
        return categories;
    }

    /**
     * Method to get category by unique id.
     *
     * @param id - unique category number.
     *
     * @return category id.
     */
    public String getCategoryById(int id) {
        return TableCategoryController.getCategoryNameById(id);
    }

    /**
     * Method to get category by name.
     *
     * @param name - category name.
     *
     * @return category name.
     */
    public int getCategoryByName(String name) {
        return TableCategoryController.getCategoryIdByName(name);
    }

    /**
     * Method to get list with all categories names.
     *
     * @return list with all categories names.
     */
    public Iterable<String> getCategoriesName(){
        return TableCategoryController.getCategoryNames();
    }

    /**
     * Method to get create category in Data Base.
     *
     * @param title - category name.
     */
    public void createCategory(String title){
        TableCategoryController.insertCategory(title);
        categories = TableCategoryController.getCategories();
    }

    /**
     * Method to get create category in Data Base.
     *
     * @return title - category name.
     */
    public int getAmountOfCategories(){
        return TableCategoryController.getCategoryAmount();
    }

    /**
     * Method return hashmap with names of category and amount of using.
     *
     * @return hashmap with names of category and amount of using.
     */
    public HashMap<String, Integer> getCategoryWithUses(){
        return TableCategoryController.getAmountRecipesByCategory();
    }
}
