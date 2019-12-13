package app.recipe;

import app.db.TableUserController;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import static app.Main.categoryDao;

/**
 * Recipe class
 */
public class Recipe {

    /**
     * Recipe unique id.
     */
    private int id;

    /**
     * Recipe name.
     */
    private String name;

    /**
     * Recipe image.
     */
    private String img;

    /**
     * Recipe description.
     */
    private String description;

    /**
     * Recipe ingredients.
     */
    private String ingredients;

    /**
     * Recipe category.
     */
    private int category;

    /**
     * Recipe publication date and time.
     */
    private Date publicationTime;

    /**
     * User's id that write recipe.
     */
    private int authorId;

    /**
     * Recipe rating.
     */
    private int rating;

    /**
     * Recipe rating.
     */
    private boolean approved;

    /**
     * Constructor - creating new user with certain parameters.
     *
     * @param id              - recipe unique id.
     * @param name            - recipe name.
     * @param img             - recipe image.
     * @param ingredients     - recipe ingredients.
     * @param description     - recipe description.
     * @param publicationTime - publication time and date of recipe.
     * @param authorId        - user id that write recipe.
     * @param rating          - recipe rating.
     * @param approved        - recipe status.
     */
    public Recipe(int id, String name, String img, String ingredients, String description, int category,
                  Date publicationTime, int authorId, int rating, boolean approved) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.ingredients = ingredients;
        this.description = description;
        this.category = category;
        this.publicationTime = publicationTime;
        this.authorId = authorId;
        this.rating = rating;
        this.approved = approved;
    }

    /**
     * Method return recipe id.
     *
     * @return unique recipe id.
     */
    public int getId() {
        return id;
    }

    /**
     * Method return recipe name.
     *
     * @return recipe name.
     */
    public String getName() {
        return name;
    }

    /**
     * Method return path of recipe image.
     *
     * @return path of recipe image.
     */
    public String getImg() {
        if (img.equals("logo.png")) {
            return "/../img/" + img;
        } else {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            String url = null;
            try {
                byte[] imageBytes = Files.readAllBytes(Paths.get(path + "/image/" + img));
                Base64.Encoder encoder = Base64.getEncoder();
                url = "data:image/png;base64," + encoder.encodeToString(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return url;
        }
    }

    /**
     * Method return name of recipe image.
     *
     * @return name of recipe image.
     */
    public String getImage() {
        return img;
    }

    /**
     * Method ingredients of recipe.
     *
     * @return ingredients recipe.
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Method return recipe description.
     *
     * @return recipe description.
     */
    public String getDescription() {
        return description;
    }

    public int getCategory() {
        return category;
    }

    /**
     * Method return recipe category.
     *
     * @return recipe category.
     */
    public String getCategoryName() {
        return categoryDao.getCategoryById(getCategory());
    }

    /**
     * Method return recipe time and date of publication.
     *
     * @return recipe time and date.
     */
    public String getPublicationTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return format.format(publicationTime);
    }

    /**
     * Method return recipe author.
     *
     * @return recipe author id.
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Method return recipe rating.
     *
     * @return recipe rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Method return author's username to display.
     *
     * @return author's username.
     */
    public String getAuthorUsername() {
        return TableUserController.getUsername(this.authorId);
    }

    /**
     * Method parse string of ingredients.
     *
     * @return list of ingredients.
     */
    public ArrayList<String> getArrayOfIngredients() {
        ArrayList<String> array = new ArrayList<>();
        for (String oneIngredient : this.ingredients.split(", ")) {
            array.add(oneIngredient);
        }
        return array;
    }

    /**
     * Method formatting the date.
     *
     * @return the date in need format.
     */
    public String getPubDate() {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(publicationTime);
    }

    /**
     * Method return the approve status.
     *
     * @return approve status.
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Method formatting the time.
     *
     * @return the time in need format.
     */
    public String getPubTime() {
        DateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(publicationTime);
    }
}
