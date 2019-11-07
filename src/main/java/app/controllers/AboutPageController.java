package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.userDao;

public class AboutPageController {
    public final static Handler serveAboutPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("currentUser", userDao.getU());
        ctx.render(Path.Template.ABOUT, model);
    };
}
