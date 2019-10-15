package app;

import app.index.IndexController;
import app.login.LoginController;
import app.post.PostController;
import app.registration.RegistrationController;
import app.recipe.RecipeController;
import app.recipe.RecipeDao;
import app.user.UserDao;
import app.util.HerokuUtil;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;


public class Main {

    public static RecipeDao recipeDao;
    public static UserDao userDao;

    public static void main(String[] args) {

        recipeDao = new RecipeDao();
        userDao = new UserDao();

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(HerokuUtil.getHerokuAssignedPort());

        app.routes(() -> {
            before(LoginController.ensureLoginBeforeViewingBooks);
            get(Path.Web.INDEX, IndexController.serveIndexPage);
            get(Path.Web.REGISTRATION, RegistrationController.serveRegistrationPage);
            get(Path.Web.RECIPES, RecipeController.fetchAllRecipes);
            get(Path.Web.ONE_RECIPE, RecipeController.fetchOneRecipe);
            get(Path.Web.LOGIN, LoginController.serveLoginPage);
//            post(Path.Web.LOGIN, LoginController.handleLoginPost);
            get(Path.Web.POST, PostController.serveIndexPage);
            post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        });

        app.error(404, ViewUtil.notFound);
    }

}
