package app.controllers;

import app.db.TableRecipeController;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.recipeDao;
import static app.util.RequestUtil.*;
import static app.util.ViewUtil.getNextPage;
import static app.util.ViewUtil.getPreviousPage;

/**
 * Class controller of Main page.
 */
public class IndexController {

    /**
     * Serve Main page with recipes from the Data Base.
     */
    public final static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        int present = Integer.parseInt(getParamId(ctx));
        model.put("recipes", recipeDao.getAllRecipes(present));
        model.put("previous", getPreviousPage(present));
        model.put("next", getNextPage(present));
        ctx.render(Path.Template.INDEX, model);
    };

    /**
     * Method-post that call function to sort the current recipes by popularity.
     */
    public final static Handler popularRecipes = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        int present = Integer.parseInt(getParamId(ctx));
        model.put("recipes", recipeDao.getPopular(present));
        model.put("present", present);
        model.put("previous", getPreviousPage(present));
        model.put("next", getNextPage(present));
        ctx.render(Path.Template.INDEX, model);
    };

    /**
     * Method-post that call function to sort the current recipes by new.
     */
    public final static Handler newRecipes = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        int present = Integer.parseInt(getParamId(ctx));
        model.put("recipes", recipeDao.getNewest(present));
        model.put("previous", getPreviousPage(present));
        model.put("next", getNextPage(present));
        ctx.render(Path.Template.INDEX, model);
    };

    /**
     * Method-post that call function to search the recipes with need parameters by name and\or ingredients.
     */
    public final static Handler searchTheRecipes = ctx -> {
        if (getQueryIngredientsForSearch(ctx).equals("") && getQueryTitle(ctx).equals("")) {
            ctx.redirect("/index/1");
        } else {
            if (getQueryIngredientsForSearch(ctx).equals(""))
                recipeDao.seachedList = TableRecipeController.searchRecipe(getQueryTitle(ctx), "");
            else
                recipeDao.seachedList = TableRecipeController.searchRecipe(getQueryTitle(ctx),
                        getQueryIngredientsForSearch(ctx));
            Map<String, Object> model = ViewUtil.baseModel(ctx);
            model.put("recipes", recipeDao.seachedList);

            if (recipeDao.seachedList.size() == 0)
                model.put("noMatch", true);
            else
                model.put("noMatch", false);
            ctx.render(Path.Template.INDEX, model);
        }
    };
}
