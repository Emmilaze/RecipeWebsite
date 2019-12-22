package app.user;

import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import org.mindrot.jbcrypt.BCrypt;

import static app.Main.userDao;
import static app.util.RequestUtil.getParamCode;

/**
 * Class controller for user's object.
 */
public class UserController {

    /**
     * User authorization.
     *
     * @param username - user's username.
     * @param password - user's not hashed password.
     *
     * @return the result of checking.
     */
    public static boolean authenticate(String username, String password, UserDao userDao) {
        if (username == null || password == null) {
            return false;
        }
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        return hashedPassword.equals(user.getHashedPassword());
    }

    /**
     * Method-post to activate all functional site to user. Call function to make change in Data Base.
     */
    @OpenApi(
            path = "/activate/{code}",
            method = HttpMethod.GET,
            summary = "Activate the user's page",
            description = "Activate the user",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler activateUser = ctx -> {
        String code = getParamCode(ctx);
        String[] words = code.split("CER");
        for(String word : words){
            System.out.println(word);
        }
        int id = Integer.parseInt(words[1]);
        userDao.activateUser(id);
        ctx.redirect("/index/1");
    };
}
