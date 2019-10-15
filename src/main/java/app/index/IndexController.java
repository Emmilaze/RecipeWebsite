package app.index;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;

import static app.Main.recipeDao;
import static app.Main.userDao;

public class IndexController {
    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
 //       model.put("users", userDao.getAllUserNames());
        model.put("newRecipe", recipeDao.getNewest());
        model.put("popularRecipe", recipeDao.getPopular());
        model.put("recipe", recipeDao.getRandomRecipe());
        ctx.render(Path.Template.INDEX, model);
    };
}
