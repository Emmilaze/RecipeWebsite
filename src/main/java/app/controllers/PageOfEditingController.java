package app.controllers;

import app.recipe.Recipe;
import app.util.Cleaner;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import io.sentry.Sentry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static app.Main.categoryDao;
import static app.Main.recipeDao;
import static app.util.FileMethods.renameFile;
import static app.util.RequestUtil.*;

/**
 * Class controller of page with editing recipe.
 */
public class PageOfEditingController {

    /**
     * Serve the page with editing the recipe.
     */
    @OpenApi(
            path = "/edit/{recipeId}",
            method = HttpMethod.GET,
            summary = "Edit the recipe",
            description = "Editing the need recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveEditPage = ctx -> {
        Recipe recipe = recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx)));
        if (getSessionCurrentUser(ctx).getPrivilege() == 4 || getSessionCurrentUser(ctx).getId() == recipe.getAuthorId()) {
            Map<String, Object> model = ViewUtil.baseModel(ctx);
            model.put("recipe", recipe);
            model.put("categories", categoryDao.getCategoriesName());
            model.put("str", "");
            ctx.render(Path.Template.EDIT, model);
        } else {
            ctx.redirect("/recipes/" + Integer.parseInt(getParamId(ctx)));
        }
    };

    /**
     * Method-post that download new file, if user upload the new.
     * Add ingredients to the list.
     * Call function to make update with certain parameters in the Data Base.
     * If user don't upload the new image, left the previous.
     * Redirecting to the updated recipe page.
     */
    @OpenApi(
            path = "/edit/{recipeId}",
            method = HttpMethod.POST,
            summary = "Edit the recipe",
            description = "Editing the need recipe",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler handleEditPost = ctx -> {
        Recipe recipe = recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx)));
        if (getSessionCurrentUser(ctx).getPrivilege() == 4 || getSessionCurrentUser(ctx).getId() == recipe.getAuthorId()) {
            ctx.uploadedFiles("files").forEach(file -> {
                try {
                    FileUtils.copyInputStreamToFile(file.getContent(),
                            new File("./image/" + file.getFilename()));
                } catch (IOException e) {
                    Sentry.capture(e);
                }
                String str = "";
                List<String> list = getQueryIngredients(ctx);
                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
                        str = Cleaner.removeAllTags(str);
                        str += list.get(i);
                    } else {
                        str = Cleaner.removeAllTags(str);
                        str += ", " + list.get(i);
                    }
                }
                if (file.getFilename().isEmpty()) {
                    recipeDao.updateRecipe(Cleaner.removeAllTags(getQueryName(ctx)),
                            recipe.getImage(),
                            Cleaner.removeBadTags(getQueryDescription(ctx)), str, Integer.parseInt(getParamId(ctx)),
                            categoryDao.getCategoryByName(getQueryCategory(ctx)), getQueryDate(ctx),
                            getQueryTime(ctx));
                } else {
                    String picName = renameFile(file.getFilename());
                    recipeDao.updateRecipe(Cleaner.removeAllTags(getQueryName(ctx)), picName,
                            Cleaner.removeBadTags(getQueryDescription(ctx)), str,
                            Integer.parseInt(getParamId(ctx)),
                            categoryDao.getCategoryByName(getQueryCategory(ctx)), getQueryDate(ctx),
                            getQueryTime(ctx));
                }
            });
            ctx.redirect("/recipes/" + recipe.getId());
        } else {
            ctx.redirect("/recipes/" + recipe.getId());
        }
    };
}
