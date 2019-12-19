package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

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
    public final static Handler changePassword = ctx -> {
        userDao.changePassword(getQueryNewPassword(ctx), getSessionCurrentUser(ctx).getId(), userDao);
        ctx.redirect("/profile_page");
    };
}
