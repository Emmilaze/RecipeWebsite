package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
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
    @OpenApi(
            path = "/search/{pageNumber}",
            method = HttpMethod.GET,
            summary = "Searching by criterias",
            description = "Searching",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveSearchPage = ctx -> {
        try {
            ingr = getQueryIngredientsForSearch(ctx);
            name = getQueryTitle(ctx);
        } catch (Exception e) {
            System.out.println("Something wrong with search");
            Sentry.capture(e);
        }
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        int present = Integer.parseInt(getParamId(ctx));
        if (isMoreSearch(present, ingr, name)) {
            ctx.redirect("/index/1");
        }
        String search = ctx.fullUrl();
        String[] params = search.split("\\?");
        if (ingr.equals("") && name.equals("")) {
            ctx.redirect("/index/1");
        } else {
            if (ingr.equals("")) {
                recipeDao.seachedList = recipeDao.search(name, "", present);

            } else {
                recipeDao.seachedList = recipeDao.search(name,
                        ingr, present);
            }
        }
        model.put("recipes", recipeDao.seachedList);
        if (getPreviousPage(present) != 0)
            model.put("previous", getPreviousPage(present) + "?" + params[1]);
        else
            model.put("previous", getPreviousPage(present));
        if (getNextPageSearch(present, ingr, name) != 0)
            model.put("next", getNextPageSearch(present, ingr, name) + "?" + params[1]);
        else
            model.put("next", getNextPageSearch(present, ingr, name));
        ctx.render(Path.Template.SEARCH, model);
    };
}
