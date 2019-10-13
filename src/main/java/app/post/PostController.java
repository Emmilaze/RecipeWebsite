package app.post;

import app.util.Path;
import io.javalin.http.Handler;

public class PostController {

    public static Handler serveIndexPage = ctx -> {
        ctx.render(Path.Template.POST);
    };
}
