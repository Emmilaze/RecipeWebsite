package app;

import app.controllers.*;
import app.recipe.RecipeController;
import app.recipe.RecipeDao;
import app.user.UserDao;
import app.util.HerokuUtil;
import app.util.Path;
import app.util.ViewUtil;
import app.vote.VoteDao;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;


import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;


public class Main {

    public static RecipeDao recipeDao;
    public static UserDao userDao;
    public static VoteDao voteDao;

    public static void main(String[] args) {

        recipeDao = new RecipeDao();
        userDao = new UserDao();
        voteDao = new VoteDao();

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(HerokuUtil.getHerokuAssignedPort());

        app.routes(() -> {

            get(Path.Web.INDEX, IndexController.serveIndexPage);
            get(Path.Web.LOGIN, LoginController.serveLoginPage);
            get(Path.Web.REGISTER, RegisterController.serveRegisterPage);
            get(Path.Web.POST, PostController.servePostPage);
            get(Path.Web.EDIT, PageOfEditingController.serveEditPage);
            get(Path.Web.PROFILE, ProfilesController.serveProfilesPage);
            post(Path.Web.POST, PostController.handleCreatePost);
            post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
            post(Path.Web.LOGIN, LoginController.handleLoginPost);
            post(Path.Web.REGISTER, RegisterController.handleRegisterPost);
            post(Path.Web.EDIT, PageOfEditingController.handleEditPost);
            get(Path.Web.ONE_RECIPE, RecipeController.fetchOneRecipe);
            get(Path.Web.LOGOUT, LoginController.handleLogoutPost);
            get(Path.Web.BLOCK, ProfilesController.blockUserPost);
            get(Path.Web.LIKE, RecipeController.likePost);
            get(Path.Web.DISLIKE, RecipeController.dislikePost);
            get(Path.Web.DELETE_RECIPE, RecipeController.deletePost);
            get(Path.Web.SORT_BY_NEW, IndexController.newRecipes);
            get(Path.Web.SORT_BY_POPULAR, IndexController.popularRecipes);
            get(Path.Web.SEARCH, IndexController.searchTheRecipes);
            get(Path.Web.ABOUT, AboutPageController.serveAboutPage);
        });

        app.error(404, ViewUtil.notFound);
    }

}
