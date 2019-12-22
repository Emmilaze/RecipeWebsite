package app.recipe;

import app.util.Cleaner;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

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
    @OpenApi(
            path = "/recipes/{recipeId}",
            method = HttpMethod.GET,
            summary = "Serve one recipe",
            description = "Recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler fetchOneRecipe = ctx -> {
        if (recipeDao.isExisted(Integer.parseInt(getParamId(ctx)))) {
            Recipe recipe = recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx)));
            if (!recipe.isApproved()) {
                if (getSessionCurrentUser(ctx).getPrivilege() > 2 || getSessionCurrentUser(ctx).getId() == recipe.getAuthorId()) {
                    Map<String, Object> model = ViewUtil.baseModel(ctx);
                    model.put("recipe", recipe);
                    model.put("comments", commentDao.getCommentByRecipe(recipe.getId()));
                    model.put("versions", recipeDao.getOldVersions(recipe.getId()));
                    ctx.render(Path.Template.RECIPES_ONE, model);
                }
            } else {
                Map<String, Object> model = ViewUtil.baseModel(ctx);
                model.put("recipe", recipe);
                model.put("haveVoted", voteDao.haveVoted(getSessionCurrentUser(ctx), recipe.getId()));
                model.put("comments", commentDao.getCommentByRecipe(recipe.getId()));
                model.put("versions", recipeDao.getOldVersions(recipe.getId()));
                ctx.render(Path.Template.RECIPES_ONE, model);
            }
        } else {
            ctx.redirect("/index/1");
        }
    };

    /**
     * Method-post to increase recipe rating. Call function to make change in Data Base.
     */
    @OpenApi(
            path = "/like/{recipeId}",
            method = HttpMethod.GET,
            summary = "Up the rating recipe by user",
            description = "Like the recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler likePost = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 0) {
            voteDao.addVote(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
        }
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method-post to decrease recipe rating. Call function to make change in Data Base.
     */
    @OpenApi(
            path = "/dislike/{recipeId}",
            method = HttpMethod.GET,
            summary = "Remove user's like",
            description = "Dislike the recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler dislikePost = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 0)
            voteDao.deleteVote(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method-post to delete recipe. Call function to make change in Data Base. Render the Main page.
     */
    @OpenApi(
            path = "/delete/{recipeId}",
            method = HttpMethod.GET,
            summary = "Deleting the post by user",
            description = "Delete the recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
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
    @OpenApi(
            path = "/approve/{recipeId}",
            method = HttpMethod.GET,
            summary = "Cofirm recipe by the administration",
            description = "Approve the recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public static Handler approve = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 2)
            recipeDao.approveRecipe(Integer.parseInt(getParamId(ctx)));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
    };

    /**
     * Method create record with comment in Data Base.
     */
    @OpenApi(
            path = "/recipes/{recipeId}",
            method = HttpMethod.POST,
            summary = "Leave comment to recipe by user",
            description = "Leave the comment",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public static Handler makeComment = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 0)
            commentDao.createComment(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)),
                    Cleaner.removeBadTags(getQueryComment(ctx)));
        ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));

    };

    /**
     * Method create/update record about amount of downloading.
     */
    @OpenApi(
            path = "/print/{recipeId}",
            method = HttpMethod.GET,
            summary = "Download recipe by user",
            description = "Dowload the recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public static Handler download = ctx -> {
        if (getSessionCurrentUser(ctx) != null) {
            donwloadsDao.downloadTheRecipe(getSessionCurrentUser(ctx).getId(), Integer.parseInt(getParamId(ctx)));
            ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
        } else {
            donwloadsDao.downloadTheRecipe(0, Integer.parseInt(getParamId(ctx)));
            ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
        }
    };

    /**
     * Method render page of one recipe.
     */
    @OpenApi(
            path = "/recipes/{recipeId}",
            method = HttpMethod.GET,
            summary = "Serve one recipe",
            description = "Recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler fetchOldRecipe = ctx -> {
        Recipe recipe = recipeDao.getRecipeByVersion(Integer.parseInt(getParamId(ctx)),
                Integer.parseInt(getParamNumber(ctx)));
        if(recipe == null)
            ctx.redirect("/index/1");
        else{
            if (getSessionCurrentUser(ctx).getPrivilege() > 2 ||
                    getSessionCurrentUser(ctx).getId() == recipe.getAuthorId()) {
                Map<String, Object> model = ViewUtil.baseModel(ctx);
                model.put("recipe", recipe);
                model.put("actual", Integer.parseInt(getParamId(ctx)));
                model.put("currentVersion", Integer.parseInt(getParamNumber(ctx)));
                model.put("versions", recipeDao.getOldVersions(Integer.parseInt(getParamId(ctx))));
                ctx.render(Path.Template.VERSIONS, model);
            } else {
                ctx.redirect("/index/1");
            }
        }
    };
}
