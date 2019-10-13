package app.recipesAll;

import app.util.Path;
import io.javalin.http.Handler;


public class ListOfRecipesController {
    public static Handler serveAllRecipesPage = ctx -> {
        ctx.render(Path.Template.RECIPES_ALL);
    };
}
