package app.recipe;

import app.db.TableUserController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private String img;
    private String description;
    private String ingredients;
//    private String category;
    private Date publicationTime;
    private int authorId;
    private int rating;

    public Recipe(int id, String name, String img, String ingredients, String description, Date publicationTime,
                  int authorId, int rating) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.ingredients = ingredients;
        this.description = description;
 //       this.category = category;
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

    public String getImg() {
        return "/../img/" + img;
    }

    public String getImage() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }

    public String getPublicationTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return format.format(publicationTime);
    }

    public Date getDateFormat() {
        return publicationTime;
    }

    public void setPublicationTime(Date publicationTime) {
        this.publicationTime = publicationTime;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAuthorUsername(){
        return TableUserController.getUsername(this.authorId);
    }

    public ArrayList<String> getArrayOfIngredients(){
        ArrayList<String> array = new ArrayList<>();
        for (String oneIngredient : this.ingredients.split(", ")) {
            array.add(oneIngredient);
        }
        return array;
    }

//    public String getTime(){
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        return format.format(publicationTime);
//    }
//
//    public String getDate(){
//        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        return format.format(publicationTime);
//    }
}
