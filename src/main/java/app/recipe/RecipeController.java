package app.recipe;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.*;
import static app.util.RequestUtil.*;

/**
 * Class controller of Recipe page.
 */
public class RecipeController {

    /**
     * Method render page of one recipe.
     */
    public final static Handler fetchOneRecipe = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("recipe", recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx))));
        model.put("haveVoted", voteDao.haveVoted(getSessionCurrentUser(ctx), Integer.parseInt(getParamId(ctx))));
        model.put("comments", commentDao.getCommentByRecipe(Integer.parseInt(getParamId(ctx))));
        ctx.render(Path.Template.RECIPES_ONE, model);
    };

    /**
     * Method-post to increase recipe rating. Call function to make change in Data Base.
     */
    public final static Handler likePost = ctx -> {
        voteDao.addVote(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method-post to decrease recipe rating. Call function to make change in Data Base.
     */
    public final static Handler dislikePost = ctx -> {
        voteDao.deleteVote(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method-post to delete recipe. Call function to make change in Data Base. Render the Main page.
     */
    public final static Handler deletePost = ctx -> {
        int id = Integer.parseInt(getParamId(ctx));
        if (getSessionCurrentUser(ctx).getPrivilege() == 4 &&
                recipeDao.getRecipeById(id).getAuthorId() == getSessionCurrentUser(ctx).getId()) {
            recipeDao.deleteRecipe(id);
            ctx.redirect("/index/1");
        }
    };

    /**
     * Method approve the current recipe.
     */
    public static Handler approve = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 2) {
            recipeDao.approveRecipe(Integer.parseInt(getParamId(ctx)));
            ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
        }
    };

    /**
     * Method create record with comment in Data Base.
     */
    public static Handler makeComment = ctx -> {
        commentDao.createComment(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)),
                getQueryComment(ctx));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method create/update record about amount of downloading.
     */
    public static Handler download = ctx -> {
        if (getSessionCurrentUser(ctx) != null) {
            donwloadsDao.downloadTheRecipe(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
            ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
        } else {
            donwloadsDao.downloadTheRecipe(0, Integer.parseInt(getParamId(ctx)));
            ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
        }
    };
}
