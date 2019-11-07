package app.recipe;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.*;
import static app.util.RequestUtil.*;

public class RecipeController {
    public final static Handler fetchOneRecipe = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("currentUser", userDao.getU());
        model.put("recipe", recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx))));
        model.put("haveVoted", voteDao.haveVoted(Integer.parseInt(getParamId(ctx))));
        ctx.render(Path.Template.RECIPES_ONE, model);
    };

    public final static Handler likePost = ctx -> {
        voteDao.addVote(userDao.u.getId(), Integer.parseInt(getParamId(ctx)));

        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    public final static Handler dislikePost = ctx -> {
        voteDao.deleteVote(userDao.u.getId(), Integer.parseInt(getParamId(ctx)));

        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    public final static Handler deletePost = ctx -> {
        recipeDao.deleteRecipe(Integer.parseInt(getParamId(ctx)));

        ctx.render("/index");
    };
}
