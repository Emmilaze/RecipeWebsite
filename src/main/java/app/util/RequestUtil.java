package app.util;

import app.user.User;
import io.javalin.http.Context;
import io.sentry.Sentry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Making request from pages.
 */
public class RequestUtil {

    /**
     * Getting id from page.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getParamId(Context ctx) {
        return ctx.pathParam("id");
    }

    /**
     * Getting field username from registration.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryUsername(Context ctx) {
        return ctx.formParam("username");
    }

    /**
     * Getting field password from registration.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryPassword(Context ctx) {
        return ctx.formParam("password");
    }

    /**
     * Getting email username from registration.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryEmail(Context ctx) {
        return ctx.formParam("email");
    }

    /**
     * Getting field name of recipe.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryName(Context ctx) {
        return ctx.formParam("name");
    }

    /**
     * Getting field name of recipe for search.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryTitle(Context ctx) {
        return ctx.req.getParameter("title");
    }

    /**
     * Getting field description of recipe.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryDescription(Context ctx) {
        return ctx.formParam("description");
    }

    /**
     * Getting fields with ingredients of recipe.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static List<String> getQueryIngredients(Context ctx){
        List<String> list = new ArrayList<>();
        list.addAll(ctx.formParams("add-ingr"));
        return list;
    }

    /**
     * Getting fields with ingredients for search.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryIngredientsForSearch(Context ctx){
        ArrayList<String> list = new ArrayList<>();
        try {
            list.addAll(Arrays.asList(ctx.req.getParameterValues("ingr")));
        }catch(NullPointerException e){
            System.out.println(e);
            Sentry.capture(e);
        }
        String str = "";
        for(int i = 0; i < list.size(); i++){
            if(i == (list.size() - 1))
                str += list.get(i);
            else
                str += list.get(i) + ",";
        }
        return str;
    }

    /**
     * Getting time to post the recipe.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryTime(Context ctx){ return ctx.formParam("time"); }

    /**
     * Getting the date to post the recipe.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryDate(Context ctx){ return ctx.formParam("date"); }

    /**
     * Getting the category for recipe.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryCategory(Context ctx) {
        return ctx.formParam("category");
    }

    /**
     * Getting current user.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static User getSessionCurrentUser(Context ctx) {
        return ctx.sessionAttribute("currentUser");
    }

    /**
     * Getting new category from page.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryNewCategory(Context ctx) {
        return ctx.req.getParameter("newCategory");
    }

    /**
     * Getting comment from page.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryComment(Context ctx) {
        return ctx.formParam("comment");
    }

    /**
     * Getting comment from page.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getQueryNewPassword(Context ctx) {
        return ctx.req.getParameter("newPassword");
    }

    /**
     * Getting comment from page.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getReport(Context ctx) {
        return ctx.formParam("description");
    }

    /**
     * Getting id from page.
     *
     * @param ctx - takes dates from page.
     *
     * @return dates from field.
     */
    public static String getParamCode(Context ctx) {
        return ctx.pathParam("code");
    }
}
