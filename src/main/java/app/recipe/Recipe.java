package app.recipe;

import java.util.Date;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private String ingredients;
    private Date publicationTime;
    private String author;
    private int rating;

    public Recipe(int id, String name, String description, String ingredients, Date publicationTime, String author, int rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.publicationTime = publicationTime;
        this.author = author;
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

    public Date getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(Date publicationTime) {
        this.publicationTime = publicationTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMediumCover() {
        return "G:\\Лера\\Project\\src\\main\\resources\\public\\img\\" + this.id + ".jpg";
    }

    public String getLargeCover() {
        return "G://Лера//Project//src//main//resources//public//img//" + this.id + ".jpg";
    }
}
