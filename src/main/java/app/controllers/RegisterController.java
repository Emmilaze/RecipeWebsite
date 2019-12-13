package app.controllers;

import app.user.User;
import app.util.Cleaner;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.emailSenderTLS;
import static app.Main.userDao;
import static app.util.RequestUtil.*;

/**
 * Class controller of register page.
 */
public class RegisterController {

    /**
     * Method serve register page. If person is logged on, redirect him to the Main page.
     */
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
    public final static Handler handleRegisterPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!userDao.haveRegistered(getQueryEmail(ctx))) {
            User user = userDao.createUser(Cleaner.removeAllTags(getQueryEmail(ctx)),
                    Cleaner.removeAllTags(getQueryUsername(ctx)), Cleaner.removeAllTags(getQueryPassword(ctx)), userDao);
            emailSenderTLS.send("Account activation", "To activate an account and use all the features of the site, please click on the link -> http://g3.sumdu-tss.site/activate/"
                            + user.getSalt() + "CER" + user.getId(),
                    Cleaner.removeAllTags(getQueryEmail(ctx)));
            ctx.redirect("/message");
        } else {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.REGISTER, model);
        }
    };
}