package app.controllers;

import app.db.TableRecipeController;
import io.javalin.http.Handler;

import java.util.Map;

import app.user.UserController;
import app.util.Path;
import app.util.ViewUtil;

import static app.Main.recipeDao;
import static app.Main.userDao;
import static app.util.RequestUtil.*;

public class LoginController {

    public final static Handler serveLoginPage = ctx -> {
        if(userDao.u == null)
        ctx.render(Path.Template.LOGIN);
        else{
            ctx.render("/index");
        }
    };

    public final static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!UserController.authenticate(getQueryUsername(ctx), getQueryPassword(ctx))) {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.LOGIN, model);
        } else {
            ctx.sessionAttribute("currentUser", userDao.getUserByUsername(getQueryUsername(ctx)));
            userDao.setU(userDao.getUserByUsername(getQueryUsername(ctx)));
            model.put("authenticationSucceeded", true);
            model.put("currentUser", userDao.getU());
            model.put("recipes", recipeDao.getAllRecipes());
            model.put("ingredients", TableRecipeController.getIngredients());
            if (getQueryLoginRedirect(ctx) != null) {
                ctx.redirect(getQueryLoginRedirect(ctx));
            }
            ctx.render(Path.Template.INDEX, model);
        }
    };

    public final static Handler handleLogoutPost = ctx -> {
        userDao.setU(null);
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.redirect(Path.Web.LOGIN);
    };
}
