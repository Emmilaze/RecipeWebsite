package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.userDao;
import static app.util.RequestUtil.getParamId;

public class ProfilesController {
    public final static Handler serveProfilesPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if(userDao.u.getPrivilege() == 2) {
            model.put("currentUser", userDao.getU());
            model.put("users", userDao.getAllUsers());
            ctx.render(Path.Template.PROFILES, model);
        } else {
            ctx.redirect("/index");
        }
    };

    public final static Handler blockUserPost = ctx -> {
        userDao.blockUser(Integer.parseInt(getParamId(ctx)));
        ctx.redirect("//profile_page");
    };
}
