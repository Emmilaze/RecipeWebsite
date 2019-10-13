package app.util;

public class Path {

    public static class Web {
        public static final String INDEX = "/index";
        public static final String LOGIN = "/login";
        public static final String RECIPES = "/recipesAll";
        public static final String ONE_RECIPE = "/oneRecipe";
        public static final String REGISTRATION = "/registration";
        public static final String POST = "/post";
        public static final String LOGOUT = "/logout";
    }

    public static class Template {
        public static final String INDEX = "/velocity/index/index.vm";
        public static final String LOGIN = "/velocity/login/login.vm";
        public static final String RECIPES_ALL = "/velocity/recipesAll.vm";
        public static final String RECIPES_ONE = "/velocity/recipe/one.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String REGISTRATION = "/velocity/registration.vm";
        public static final String POST = "/velocity/post.vm";
    }

}
