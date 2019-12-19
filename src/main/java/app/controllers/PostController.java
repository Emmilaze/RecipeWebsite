package app.controllers;

import app.util.Cleaner;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.sentry.Sentry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static app.Main.*;
import static app.util.FileMethods.getMemory;
import static app.util.FileMethods.renameFile;
import static app.util.RequestUtil.*;

/**
 * Class controller of recipe creating page.
 */
public class PostController {

    /**
     * Serve the page with creating recipe. If user is not logged in - redirect to main page.
     */
    public final static Handler servePostPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("categories", categoryDao.getCategoriesName());
        if (getSessionCurrentUser(ctx).getPrivilege() > 0) {
            ctx.render(Path.Template.POST, model);
        } else {
            ctx.render("/index/1");
        }
    };

    /**
     * Method-post with creating a recipe.
     * Download the user's image for recipe to the img package.
     * Add ingredients to the list.
     * Convert date to the right format.
     * Try to make a record in the Data Base.
     * Redirect to the main page.
     */
    public final static Handler handleCreatePost = ctx -> {
        if (getSessionCurrentUser(ctx).getPrivilege() > 0) {
            ctx.uploadedFiles("files").forEach(file -> {
                try {
                    FileUtils.copyInputStreamToFile(file.getContent(),
                            new File("./image/" + file.getFilename()));
                } catch (IOException e) {
                    System.out.println(e);
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
                String string = getQueryDate(ctx) + " " + getQueryTime(ctx);
                string = Cleaner.removeAllTags(string);
                DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date;
                try {
                    date = format1.parse(string);
                    boolean approved = false;
                    if (getSessionCurrentUser(ctx).getPrivilege() > 1)
                        approved = true;
                    if (file.getFilename().isEmpty())
                        recipeDao.createRecipe(Cleaner.removeAllTags(getQueryName(ctx)), "logo.png", str,
                                Cleaner.removeBadTags(getQueryDescription(ctx)),
                                categoryDao.getCategoryByName(getQueryCategory(ctx)),
                                date, getSessionCurrentUser(ctx).getId(), approved);
                    else {
                        String picName = renameFile(file.getFilename());
                        recipeDao.createRecipe(Cleaner.removeAllTags(getQueryName(ctx)), picName, str,
                                Cleaner.removeBadTags(getQueryDescription(ctx)),
                                categoryDao.getCategoryByName(getQueryCategory(ctx)),
                                date, getSessionCurrentUser(ctx).getId(), approved);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Sentry.capture(e);
                } catch (Exception e) {
                    Sentry.capture(e);
                }
            });
            ctx.redirect("/index/1");
        } else {
            ctx.redirect("/index/1");
        }
    };
}
