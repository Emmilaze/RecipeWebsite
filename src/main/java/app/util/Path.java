package app.util;

/**
 * Class path for pages.
 */
public class Path {

    /**
     * Class with paths.
     */
    public static class Web {
        public static final String INDEX = "/index/:id";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/registr";
        public static final String ONE_RECIPE = "/recipes/:id";
        public static final String POST = "/create_recipe";
        public static final String LOGOUT = "/logout";
        public static final String EDIT = "/recipes/:id/edit";
        public static final String PROFILE = "/profile_page";
        public static final String BLOCK = "/block/:id";
        public static final String LIKE = "/like/:id";
        public static final String DISLIKE = "/dislike/:id";
        public static final String DELETE_RECIPE = "/delete_recipe/:id";
        public static final String SORT_BY_NEW = "/sort_date/:id";
        public static final String SORT_BY_POPULAR = "/sort_rating/:id";
        public static final String SEARCH = "/search/:id";
        public static final String ABOUT = "/about";
        public static final String USER_RECIPE = "/my_recipes";
        public static final String UNCONFIRMED = "/unconfirmed_recipes";
        public static final String APPROVE = "/approve_recipe/:id";
        public static final String ADD_CATEGORY = "/addCategory";
        public static final String ACTIVATE = "/activate/:code";
        public static final String FOLLOWED = "/followed";
        public static final String CHANGE_PASSWORD = "/change_password";
        public static final String DOWNLOAD = "/print/:id";
        public static final String REPORT = "/report";
        public static final String ROBOTS = "/robots.txt";
        public static final String MESSAGE = "/message";
    }

    /**
     * Class with paths to files.
     */
    public static class Template {
        public static final String INDEX = "/velocity/index.vm";
        public static final String LOGIN = "/velocity/login.vm";
        public static final String REGISTER = "/velocity/registr.vm";
        public static final String RECIPES_ONE = "/velocity/recepie_page.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String POST = "/velocity/create_recipe.vm";
        public static final String EDIT = "/velocity/edit.vm";
        public static final String PROFILES = "/velocity/profile_page.vm";
        public static final String ABOUT = "/velocity/about.vm";
        public static final String USER_RECIPE = "/velocity/myRecipes.vm";
        public static final String UNCONFIRMED = "/velocity/pending.vm";
        public static final String SERVER_ERROR = "/velocity/serverError.vm";
        public static final String FOLLOWED = "/velocity/followed.vm";
        public static final String REPORT = "/velocity/report.vm";
        public static final String ROBOTS = "/velocity/robots.vm";
        public static final String MESSAGE = "/velocity/message.vm";
        public static final String SEARCH = "/velocity/search.vm";
    }
}
