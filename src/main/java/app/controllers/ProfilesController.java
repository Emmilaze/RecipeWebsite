package app.controllers;

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
 * Class controller of page with profiles.
 */
public class ProfilesController {

    /**
     * Serve the page with users. Displays list of all registered user and administration panel.
     * If user is not admin, redirect to the main page.
     */
    @OpenApi(
            path = "/profile_page",
            method = HttpMethod.GET,
            summary = "Serve profile page",
            description = "Profile page",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveProfilesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if(getSessionCurrentUser(ctx) != null) {
            model.put("users", userDao.getAllUsers());
            model.put("categories", categoryDao.getCategories());
            model.put("downloads", donwloadsDao.allDownloads());
            model.put("recipesAmount", recipeDao.getAmount());
            model.put("usersAmount", userDao.getUsersAmount());
            model.put("stat", recipeDao.getStatistics());
            model.put("categoryAmount", categoryDao.getAmountOfCategories());
            model.put("categoryStat", categoryDao.getCategoryWithUses());
            ctx.render(Path.Template.PROFILES, model);
        } else {
            ctx.redirect("/index/1");
        }
    };

    /**
     * Method calls the function, that block the user by admin and redirect to profiles page.
     */
    @OpenApi(
            path = "/block/{userId}",
            method = HttpMethod.GET,
            summary = "Block the user by admin",
            description = "Block the user",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler blockUserPost = ctx -> {
        if(getSessionCurrentUser(ctx).getPrivilege() == 4) {
            userDao.blockUser(Integer.parseInt(getParamId(ctx)));
            ctx.redirect("/profile_page");
        }
        ctx.redirect("/profile_page");
    };

    /**
     * This method create new category in the Data Base.
     */
    @OpenApi(
            path = "/addCategory",
            method = HttpMethod.GET,
            summary = "Create the new category by admin",
            description = "Create new category",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler addCategory = ctx -> {
        if(getSessionCurrentUser(ctx).getPrivilege() == 4) {
            categoryDao.createCategory(getQueryNewCategory(ctx));
            ctx.redirect("/profile_page");
        }
        ctx.redirect("/profile_page");
    };

    /**
     * This method change user's password.
     */
    @OpenApi(
            path = "/change_password",
            method = HttpMethod.GET,
            summary = "Change password by user",
            description = "Change password",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler changePassword = ctx -> {
        userDao.changePassword(getQueryNewPassword(ctx), getSessionCurrentUser(ctx).getId(), userDao);
        ctx.redirect("/profile_page");
    };
}
