package app.controllers;

import app.user.User;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.userDao;
import static app.util.RequestUtil.*;

public class RegisterController {

    public final static Handler serveRegisterPage = ctx -> {
        if(userDao.u == null)
        ctx.render(Path.Template.REGISTER);
        else{
            ctx.render("/index");
        }
    };

    public final static Handler handleRegisterPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if(userDao.haveRegistered(getQueryEmail(ctx)) == false) {
            User user = userDao.createUser(getQueryEmail(ctx), getQueryUsername(ctx), getQueryPassword(ctx));
            ctx.sessionAttribute("currentUser", userDao.getUserByUsername(getQueryUsername(ctx)));
            userDao.setU(user);
            ctx.redirect("/index");
        }
        else
        {
            model.put("authenticationFailed", true);
            ctx.render(Path.Template.REGISTER, model);
        }
    };
}
