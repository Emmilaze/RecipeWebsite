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

/**
 * Class controller to serve unconfirmed recipes.
 */
public class UnconfirmedRecipesPage {

    /**
     * Method serve the page with unconfirmed recipes.
     */
    @OpenApi(
            path = "/unconfirmed_recipes",
            method = HttpMethod.GET,
            summary = "Returns unconfirmed recipes",
            description = "Unconfirmed recipes",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveUnconfirmedRecipesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        recipeDao.seachedList = null;
        model.put("recipes", recipeDao.getUnconfirmedRecipes());
        ctx.render(Path.Template.UNCONFIRMED, model);
    };
}
