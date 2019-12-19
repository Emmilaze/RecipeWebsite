package app.util;

import app.db.TableIngredientsController;
import io.javalin.http.Context;
import io.javalin.http.ErrorHandler;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

import static app.Main.recipeDao;
import static app.util.RequestUtil.getSessionCurrentUser;

/**
 * Class with view utils.
 */
public class ViewUtil {

    /**
     * Method create the base model of the page.
     *
     * @param ctx - dates from page.
     * @return done model for pages.
     */
    public static Map<String, Object> baseModel(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("currentUser", getSessionCurrentUser(ctx));
        model.put("ingredients", TableIngredientsController.getIngredients());
        return model;
    }

    /**
     * Method render the page with error.
     */
    public final static Handler message = ctx -> {
        ctx.render(Path.Template.MESSAGE, baseModel(ctx));
    };

    /**
     * Method render the page with error.
     */
    public final static ErrorHandler notFound = ctx -> {
        ctx.render(Path.Template.NOT_FOUND, baseModel(ctx));
    };

    /**
     * Method render the page with error.
     */
    public final static ErrorHandler serverError = ctx -> {
        ctx.render(Path.Template.SERVER_ERROR, baseModel(ctx));
    };

    public static int getNextPage(int present) {
        return ((present + 1 > recipeDao.getAmountOfPages()) ? 0 : present + 1);
    }

    public static int getPreviousPage(int present) {
        return present - 1;
    }

    public static boolean isMore(int present) {
        return recipeDao.getAmountOfPages() < present;
    }

    public static boolean isMoreSearch(int present, String ingredients, String name) {
        return recipeDao.getPagesForSearch(ingredients, name) < present;
    }

    public static int getNextPageSearch(int present, String ingredients, String name) {
        return ((present + 1 > recipeDao.getPagesForSearch(ingredients, name)) ? 0 : present + 1);
    }
}
