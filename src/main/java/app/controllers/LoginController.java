package app.controllers;

import app.user.User;
import io.javalin.http.Handler;

import java.util.Map;

import app.user.UserController;
import app.util.Path;
import app.util.ViewUtil;

import static app.Main.userDao;
import static app.util.RequestUtil.*;

/**
 * Class controller of login page.
 */
public class LoginController {

    /**
     * Serve login page. If user has logged in already, redirect to the Main page.
     */
    public final static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (getSessionCurrentUser(ctx) == null)
            ctx.render(Path.Template.LOGIN, model);
        else {
            ctx.render("/index/1");
        }
    };

    /**
     * Authentication method-post.
     * Checks date from page with dates from Data Base.
     * If authentication successful, save current user and redirect to Main page.
     * If user has logged in, redirect to Main page.
     * If authentication has fail, redirect to login page.
     */
    public final static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!UserController.authenticate(getQueryUsername(ctx), getQueryPassword(ctx), userDao)) {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.LOGIN, model);
        } else {
            User user = userDao.getUserByUsername(getQueryUsername(ctx));
            if (user.getPrivilege() == 0) {
                ctx.redirect("/message");
            } else {
                ctx.sessionAttribute("currentUser", user);
                model.put("currentUser", getSessionCurrentUser(ctx));
                ctx.redirect("/index/1");
            }
        }
    };

    /**
     * Kill the session.
     * Redirect to the login page.
     */
    public final static Handler handleLogoutPost = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.redirect("/login");
    };
}
