package app.recipe;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.recipeDao;
import static app.util.RequestUtil.getSessionCurrentUser;

/**
 * Class controller to serve user's recipes.
 */
public class UserRecipesController {

    /**
     * Method serve the page with user's recipes.
     */
    public final static Handler serveUserRecipesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        model.put("recipes", recipeDao.getRecipeByUser(getSessionCurrentUser(ctx).getId()));
        ctx.render(Path.Template.USER_RECIPE, model);
    };

    /**
     * Method serve the page with liked recipes by user.
     */
    public final static Handler serveLikedRecipesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        model.put("recipes", recipeDao.likedRecipes(getSessionCurrentUser(ctx).getId()));
        ctx.render(Path.Template.FOLLOWED, model);
    };
}
