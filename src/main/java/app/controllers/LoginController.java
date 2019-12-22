package app.controllers;

import app.user.User;
import io.javalin.http.Handler;

import java.util.Map;

import app.user.UserController;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.plugin.openapi.annotations.*;

import static app.Main.userDao;
import static app.util.RequestUtil.*;

/**
 * Class controller of login page.
 */
public class LoginController {

    /**
     * Serve login page. If user has logged in already, redirect to the Main page.
     */
    @OpenApi(
            path = "/login",
            method = HttpMethod.GET,
            summary = "Authentication",
            description = "Login page",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
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
    @OpenApi(
            path = "/login",
            method = HttpMethod.POST,
            summary = "Authentication",
            description = "Login page",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!UserController.authenticate(getQueryUsername(ctx), getQueryPassword(ctx), userDao)) {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.LOGIN, model);
        } else {
            User user = userDao.getUserByUsername(getQueryUsername(ctx));
            if (user.getPrivilege() == 0) {
                model.put("message", "To be able to use all functionality of the site please confim your registration by\n" +
                        "                following the link from regestered Email. \n" +
                        "                \n" +
                        "                \n" +
                        "                *It may be in spam folder.*");
                ctx.render(Path.Template.MESSAGE, model);
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
    @OpenApi(
            path = "/logout",
            method = HttpMethod.GET,
            summary = "Log out",
            description = "Exit from session",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler handleLogoutPost = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.redirect("/login");
    };
}
