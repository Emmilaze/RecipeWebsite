package app.recipe;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

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
    @OpenApi(
            path = "/my_recipes",
            method = HttpMethod.GET,
            summary = "Returns recipes created by current user",
            description = "User's recipes",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveUserRecipesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        model.put("recipes", recipeDao.getRecipeByUser(getSessionCurrentUser(ctx).getId()));
        ctx.render(Path.Template.USER_RECIPE, model);
    };

    /**
     * Method serve the page with liked recipes by user.
     */
    @OpenApi(
            path = "/followed",
            method = HttpMethod.GET,
            summary = "Returns recipes liked by current user",
            description = "Liked recipes",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveLikedRecipesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        model.put("recipes", recipeDao.likedRecipes(getSessionCurrentUser(ctx).getId()));
        ctx.render(Path.Template.FOLLOWED, model);
    };
}
