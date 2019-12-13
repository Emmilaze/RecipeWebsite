package app.downloads;

import app.db.TableRecipeController;
import app.db.TableUserController;

public class Downloads {
    private int id;
    private int userId;
    private int recipeId;
    private int downloaded;

    public Downloads(int id, int userId, int recipeId, int downloaded) {
        this.id = id;
        this.userId = userId;
        this.recipeId = recipeId;
        this.downloaded = downloaded;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getDownloaded() {
        return downloaded;
    }

    public String getAuthorName(){
        return TableUserController.getUsername(this.userId);
    }

    public String getRecipeName(){
        return TableRecipeController.getRecipeName(this.recipeId);
    }
}
