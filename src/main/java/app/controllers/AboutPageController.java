package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

import java.util.Map;

/**
 * Class controller of terms of use page.
 */
public class AboutPageController {

    /**
     * Serve the page with terms of use.
     */
    @OpenApi(
            path = "/about",
            method = HttpMethod.GET,
            summary = "Serve license agreement",
            description ="Serve license agreement"
    )
    public final static Handler serveAboutPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.ABOUT, model);
    };
}
