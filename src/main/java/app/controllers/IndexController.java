package app.controllers;

import app.db.TableRecipeController;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.recipeDao;
import static app.Main.userDao;
import static app.util.RequestUtil.*;

public class IndexController {
    public final static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        model.put("currentUser", userDao.getU());
        model.put("recipes", recipeDao.getAllRecipes());
        model.put("ingredients", TableRecipeController.getIngredients());
        ctx.render(Path.Template.INDEX, model);
    };

    public final static Handler popularRecipes = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("currentUser", userDao.getU());
        model.put("recipes", recipeDao.getPopular());
        model.put("ingredients", TableRecipeController.getIngredients());
        ctx.render(Path.Template.INDEX, model);
    };

    public final static Handler newRecipes = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("currentUser", userDao.getU());
        model.put("recipes", recipeDao.getNewest());
        model.put("ingredients", TableRecipeController.getIngredients());
        ctx.render(Path.Template.INDEX, model);
    };

    public final static Handler searchTheRecipes = ctx -> {
        if (getQueryIngredientsForSearch(ctx).equals(""))
            recipeDao.seachedList = TableRecipeController.searchRecipe(getQueryTitle(ctx), "");
        else
            recipeDao.seachedList = TableRecipeController.searchRecipe(getQueryTitle(ctx),
                    getQueryIngredientsForSearch(ctx));
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("currentUser", userDao.getU());
        model.put("recipes", recipeDao.seachedList);
        model.put("ingredients", TableRecipeController.getIngredients());
        if(recipeDao.seachedList .size() == 0)
            model.put("noMatch", true);
        else
            model.put("noMatch", false);
        ctx.render(Path.Template.INDEX, model);
    };
}
