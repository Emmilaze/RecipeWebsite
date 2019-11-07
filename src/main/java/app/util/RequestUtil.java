package app.util;

import app.user.User;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestUtil {

    public static String getParamId(Context ctx) {
        return ctx.pathParam("id");
    }

    public static String getQueryUsername(Context ctx) {
        return ctx.formParam("username");
    }

    public static String getQueryPassword(Context ctx) {
        return ctx.formParam("password");
    }

    public static String getQueryEmail(Context ctx) {
        return ctx.formParam("email");
    }

    public static String getQueryName(Context ctx) {
        return ctx.formParam("name");
    }

    public static String getQueryTitle(Context ctx) {
        return ctx.req.getParameter("title");
    }

    public static String getQueryDescription(Context ctx) {
        return ctx.formParam("description");
    }

    public static List<String> getQueryIngredients(Context ctx){
        List<String> list = new ArrayList<>();
        list.addAll(ctx.formParams("add-ingr"));
        return list;
    }

    public static String getQueryIngredientsForSearch(Context ctx){
        ArrayList<String> list = new ArrayList<>();
        try {
            list.addAll(Arrays.asList(ctx.req.getParameterValues("ingr")));
        }catch(NullPointerException e){
            System.out.println(e);
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

    public static String getQueryTime(Context ctx){ return ctx.formParam("time"); }

    public static String getQueryDate(Context ctx){ return ctx.formParam("date"); }

//    public static String getQueryCategory(Context ctx) {
//        return ctx.formParam("category");
//    }

    public static String getQueryLoginRedirect(Context ctx) {
        return ctx.queryParam("loginRedirect");
    }

    public static User getSessionCurrentUser(Context ctx) {
        return (User) ctx.sessionAttribute("currentUser");
    }
}
