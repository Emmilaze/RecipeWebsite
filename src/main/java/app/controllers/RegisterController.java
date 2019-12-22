package app.controllers;

import app.user.User;
import app.util.Cleaner;
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
 * Class controller of register page.
 */
public class RegisterController {

    /**
     * Method serve register page. If person is logged on, redirect him to the Main page.
     */
    @OpenApi(
            path = "/registr",
            method = HttpMethod.GET,
            summary = "Creating new user",
            description = "Registration",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveRegisterPage = ctx -> {
        if (getSessionCurrentUser(ctx) == null)
            ctx.render(Path.Template.REGISTER);
        else {
            ctx.render("/index/1");
        }
    };

    /**
     * Method-post from register page. Creating user object. Making record in the user's table. After successful
     * registration, redirecting to the Main page. If registration has fail, redirect to the register page.
     */
    @OpenApi(
            path = "/registr",
            method = HttpMethod.POST,
            summary = "Creating new user",
            description = "Registration",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler handleRegisterPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!userDao.haveRegistered(getQueryEmail(ctx))) {
            User user = userDao.createUser(Cleaner.removeAllTags(getQueryEmail(ctx)),
                    Cleaner.removeAllTags(getQueryUsername(ctx)), Cleaner.removeAllTags(getQueryPassword(ctx)), userDao);
            emailSenderTLS.send("Account activation", "To activate an account and use all the features of the site, please click on the link -> http://g3.sumdu-tss.site/activate/"
                            + user.getSalt() + "CER" + user.getId(),
                    Cleaner.removeAllTags(getQueryEmail(ctx)));
            model.put("message", "To be able to use all functionality of the site please confim your registration by\n" +
                    "                following the link from regestered Email. \n" +
                    "                \n" +
                    "                \n" +
                    "                *It may be in spam folder.*");
            ctx.render(Path.Template.MESSAGE, model);
        } else {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.REGISTER, model);
        }
    };
}