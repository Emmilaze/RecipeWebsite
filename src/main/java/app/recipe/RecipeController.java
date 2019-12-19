package app.recipe;

import app.util.Cleaner;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.*;
import static app.util.FileMethods.getMemory;
import static app.util.RequestUtil.*;

/**
 * Class controller of Recipe page.
 */
public class RecipeController {

    /**
     * Method render page of one recipe.
     */
    public final static Handler fetchOneRecipe = ctx -> {
        if (recipeDao.isExisted(Integer.parseInt(getParamId(ctx)))) {
            Recipe recipe = recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx)));
            if (!recipe.isApproved()) {
                if (getSessionCurrentUser(ctx).getPrivilege() > 2 || getSessionCurrentUser(ctx).getId() == recipe.getAuthorId()) {
                    Map<String, Object> model = ViewUtil.baseModel(ctx);
                    model.put("recipe", recipe);
                    model.put("haveVoted", voteDao.haveVoted(getSessionCurrentUser(ctx), recipe.getId()));
                    model.put("comments", commentDao.getCommentByRecipe(recipe.getId()));
                    ctx.render(Path.Template.RECIPES_ONE, model);
                }
            } else {
                Map<String, Object> model = ViewUtil.baseModel(ctx);
                model.put("recipe", recipe);
                model.put("haveVoted", voteDao.haveVoted(getSessionCurrentUser(ctx), recipe.getId()));
                model.put("comments", commentDao.getCommentByRecipe(recipe.getId()));
                ctx.render(Path.Template.RECIPES_ONE, model);
            }
        } else {
            ctx.redirect("/index/1");
        }


    };

    /**
     * Method-post to increase recipe rating. Call function to make change in Data Base.
     */
    public final static Handler likePost = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 0) {
            voteDao.addVote(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
        }
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method-post to decrease recipe rating. Call function to make change in Data Base.
     */
    public final static Handler dislikePost = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 0)
            voteDao.deleteVote(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method-post to delete recipe. Call function to make change in Data Base. Render the Main page.
     */
    public final static Handler deletePost = ctx -> {
        int id = Integer.parseInt(getParamId(ctx));
        if (getSessionCurrentUser(ctx).getPrivilege() == 4 ||
                recipeDao.getRecipeById(id).getAuthorId() == getSessionCurrentUser(ctx).getId())
            recipeDao.deleteRecipe(id);

        ctx.redirect("/index/1");
    };

    /**
     * Method approve the current recipe.
     */
    public static Handler approve = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 2)
            recipeDao.approveRecipe(Integer.parseInt(getParamId(ctx)));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));

    };

    /**
     * Method create record with comment in Data Base.
     */
    public static Handler makeComment = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 0)
            commentDao.createComment(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)),
                    Cleaner.removeBadTags(getQueryComment(ctx)));
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
