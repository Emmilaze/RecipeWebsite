package app.category;

/**
 * Category class for categories.
 */
public class Category {

    /**
     * Unique number of category.
     */
    private int id;

    /**
     * Category name.
     */
    private String name;

    /**
     * Constructor - creating new category with certain parameters.
     *
     * @param id - unique user's number.
     * @param name - category name.
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This method return the category id.
     *
     * @return the category id.
     */
    public int getId() {
        return id;
    }

    /**
     * This method return the category name.
     *
     * @return the category name.
     */
    public String getName() {
        return name;
    }
}
