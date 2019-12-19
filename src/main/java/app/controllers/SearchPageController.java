package app.controllers;

import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.sentry.Sentry;

import java.util.Map;

import static app.Main.recipeDao;
import static app.util.RequestUtil.*;
import static app.util.ViewUtil.*;

public class SearchPageController {
    public static String ingr;
    public static String name;

    /**
     * Serve Main page with recipes from the Data Base.
     */
    public final static Handler serveSearchPage = ctx -> {
        try {
            ingr = getQueryIngredientsForSearch(ctx);
            name = getQueryTitle(ctx);
        }catch (Exception e){
            System.out.println("Something wrong with search");
            Sentry.capture(e);
        }
        Map<String, Object> model = ViewUtil.baseModel(ctx);
            int present = Integer.parseInt(getParamId(ctx));
            if (isMoreSearch(present, ingr, name)) {
                ctx.redirect("/index/1");
            }
            if (ingr.equals("") && name.equals("")) {
                ctx.redirect("/index/1");
            } else {
                if (ingr.equals(""))
                    recipeDao.seachedList = recipeDao.search(name, "", present);
                else
                    recipeDao.seachedList = recipeDao.search(name,
                            ingr, present);
            }
            model.put("recipes", recipeDao.seachedList);
            model.put("previous", getPreviousPage(present));
            model.put("next", getNextPageSearch(present, ingr, name));
    };
}
