package app.util;

public class Path {

    public static class Web {
        public static final String INDEX = "/index";
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
        public static final String SORT_BY_NEW = "/sort_date";
        public static final String SORT_BY_POPULAR = "/sort_rating";
        public static final String SEARCH = "/search";
        public static final String ABOUT = "/about";
    }

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
    }

}
