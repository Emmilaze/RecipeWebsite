package app.user;

import app.DB.DataBaseController;
import app.DB.TableUserController;
import org.mindrot.jbcrypt.BCrypt;

import static app.Main.*;

public class UserController {

    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(int id, String password) {
        if (id == 0 || password == null) {
            return false;
        }
        User user = userDao.getUserById(id);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.getUsername());
        return hashedPassword.equals(user.getHashedPassword());
    }

    //TODO: достать айди, логин и пароль с формы
    public void changePassword(String newPassword){
        String password = userDao.getHashedPassword("login", newPassword);
        TableUserController.updateUser("password", password, 1);
    }

    public void changeUsername(String username){
        TableUserController.updateUser("username", username, 1);
    }

    public void changeEmail(String email){
        TableUserController.updateUser("email", email, 1);
    }

    public void blockUser(){
        TableUserController.updateUser("privilege", "-1", 1);
    }

    public void createUser(String email, String login, String password){
        String hashedPassword = userDao.getHashedPassword(login, password);
        TableUserController.insertUser(email, login, hashedPassword);
        User user = new User(DataBaseController.getNextId("users")-1, email, login, hashedPassword, 1);
        //TODO: добавление юзера в список юзеров
    }

}
