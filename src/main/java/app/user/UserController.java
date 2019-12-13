package app.user;

import io.javalin.http.Handler;
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
