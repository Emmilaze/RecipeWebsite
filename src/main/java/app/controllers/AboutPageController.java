package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

/**
 * Class controller of terms of use page.
 */
public class AboutPageController {

    /**
     * Serve the page with terms of use.
     */
    public final static Handler serveAboutPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.ABOUT, model);
    };

    /**
     * Serve the page with terms of use.
     */
    public final static Handler serveRobotsPage = ctx -> {
        ctx.render(Path.Template.ROBOTS);
    };
}
