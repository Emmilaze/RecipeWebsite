package app.controllers;

import app.recipe.Recipe;
import app.util.Cleaner;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
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
    public final static Handler handleEditPost = ctx -> {
        ctx.uploadedFiles("files").forEach(file -> {
            try {
                FileUtils.copyInputStreamToFile(file.getContent(),
                        new File("./image/" + file.getFilename()));

            } catch (IOException e) {
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
                        recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx))).getImage(),
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
        ctx.redirect("/index/1");
    };
}
