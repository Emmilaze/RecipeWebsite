package app.db;

import app.category.Category;
import io.sentry.Sentry;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class controller of category table in the Data Base.
 */
public class TableCategoryController {

    /**
     * Method insert a record with a category to the table.
     *
     * @param category - recipe category.
     */
    public static void insertCategory(String category) {
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO categories VALUES (default, ?);")) {
            preparedStatement.setString(1, category);
            preparedStatement.executeUpdate();
            System.out.println("Records created/updated successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            Sentry.capture(e);
        }
    }

    /**
     * Method reads and fills the list of objects of categories type.
     *
     * @return list of categories.
     */
    public static Category getCategoryByName(String name) {
        String sql = "SELECT * FROM categories WHERE title = ?;";
        Category category = null;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    category = new Category(rs.getInt("id"), rs.getString("title"));
                }
                return category;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return null;
        }
    }

    /**
     * Method reads and fills the list of objects of categories type.
     *
     * @return list of categories.
     */
    public static List<Category> getCategories() {
        String sql = "SELECT * FROM categories;";
        List<Category> categories = new ArrayList<Category>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    categories.add(new Category(rs.getInt("id"), rs.getString("title")));
                }
                return categories;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return null;
        }
    }

    /**
     * This method return category name by unique id.
     *
     * @return name of category.
     */
    public static String getCategoryNameById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?;";
        String category = "";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    category = rs.getString("title");
                }
                return category;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return null;
        }
    }

    /**
     * This method return category id by category name.
     *
     * @return category id.
     */
    public static int getCategoryIdByName(String name) {
        String sql = "SELECT id FROM categories WHERE title = ?;";
        int id = 0;
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("id");
                }
                return id;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return id;
        }
    }

    /**
     * This method return list of categories name from Data Base.
     *
     * @return the list with categories names.
     */
    public static List<String> getCategoryNames() {
        String sql = "SELECT title FROM categories;";
        List<String> names = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    names.add(rs.getString("title"));
                }
                return names;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return null;
        }
    }

    /**
     * This method return list of categories name with amount of their using.
     *
     * @return the list with categories names.
     */
    public static HashMap<String, Integer> getAmountRecipesByCategory() {
        HashMap<String, Integer> map = new HashMap<>();
        String sql = "SELECT title, amount FROM categories LEFT JOIN " +
                "(SELECT category, count(*) amount FROM recipes GROUP BY category) cat" +
                " ON (cat.category = categories.id);";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getString("title"), rs.getInt("amount"));
                }
                return map;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return null;
        }
    }

    /**
     * This method return the amount of categories.
     *
     * @return amount of categories.
     */
    public static int getCategoryAmount() {
        int amount = 0;
        String sql = "SELECT count(*) FROM categories;";
        try (Connection con = DriverManager.getConnection(DataBaseController.host, DataBaseController.user,
                DataBaseController.password);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    amount = rs.getInt("count");
                }
                return amount;
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                Sentry.capture(e);
                return amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Sentry.capture(e);
            return amount;
        }
    }
}
