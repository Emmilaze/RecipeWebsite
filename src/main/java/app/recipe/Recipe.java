package app.recipe;

import app.user.User;

import java.util.Date;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private String ingredients;
    private String category;
    private Date publicationTime;
    private int authorId;
    private int rating;

    public Recipe(int id, String name, String description, String ingredients, String category, Date publicationTime,
                  int authorId, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.category = category;
        this.publicationTime = publicationTime;
        this.authorId = authorId;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public Date getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(Date publicationTime) {
        this.publicationTime = publicationTime;
    }

    public int getAuthor() {
        return authorId;
    }

    public void setAuthor(int author) {
        this.authorId = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLargeCover() { return "/img/" + this.getId() + ".jpg"; }
}
