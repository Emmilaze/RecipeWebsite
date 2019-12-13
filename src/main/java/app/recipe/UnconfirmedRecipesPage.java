package app.recipe;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.recipeDao;

/**
 * Class controller to serve unconfirmed recipes.
 */
public class UnconfirmedRecipesPage {

    /**
     * Method serve the page with unconfirmed recipes.
     */
    public final static Handler serveUnconfirmedRecipesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        model.put("recipes", recipeDao.getUnconfirmedRecipes());
        ctx.render(Path.Template.UNCONFIRMED, model);
    };
}
