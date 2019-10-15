package app.recipe;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.recipeDao;
import static app.util.RequestUtil.getParamId;

public class RecipeController {
    public static Handler fetchAllRecipes = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("recipes", recipeDao.getAllRecipes());
        ctx.render(Path.Template.RECIPES_ALL, model);
    };

    public static Handler fetchOneRecipe = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("recipe", recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx))));
        ctx.render(Path.Template.RECIPES_ONE, model);
    };

//TODO: метод для создания, редактирования, удаление, голосование


}
