package app.user;

import java.util.List;

import app.db.DataBaseController;
import app.db.TableUserController;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Class DAO for methods with user's object.
 */
public class UserDao {

    /**
     * List of users from Data Base.
     */
    public List<User> users = null;

    /**
     * Method call function to get users from data base.
     *
     * @return list of users.
     */
    public Iterable<User> getAllUsers() {
        users = TableUserController.getUsers();
        return users;
    }

    /**
     * Method get user by his username.
     *
     * @param username - user's username.
     *
     * @return user.
     */
    public User getUserByUsername(String username) {
        users = TableUserController.getUsers();
        return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
    }

    /**
     * Method encrypts the password.
     *
     * @param salt - unique salt.
     * @param password - not hashed password yet.
     *
     * @return hashed password.
     */
    public String getHashedPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    /**
     * Method change user's password.
     *
     * @param newPassword - new user's password.
     * @param id - user's id.
     */
    public void changePassword(String newPassword, int id, UserDao userDao) {
        String salt = BCrypt.gensalt();
        String password = userDao.getHashedPassword(newPassword, salt);
        TableUserController.updatePassword(salt, password, id);
    }

    /**
     * Method to block user.
     *
     * @param id - user's id.
     */
    public void blockUser(int id) {
        TableUserController.updateUser( -1, id);
    }

    /**
     * Method change user's password.
     *
     * @param email - user's email.
     * @param login - user's username.
     * @param password - user's password.
     *
     * @return new user.
     */
    public User createUser(String email, String login, String password, UserDao userDao) {
        String salt = BCrypt.gensalt();
        String hashedPassword = userDao.getHashedPassword(password, salt);
        TableUserController.insertUser(email, login, salt, hashedPassword);
        User user = new User(DataBaseController.getNextId("users") - 1, email, login, salt, hashedPassword, 1);
        return user;
    }

    /**
     * Method check that email didn`t have register.
     *
     * @param email - user's email.
     *
     * @return the result of a mail busy check.
     */
    public boolean haveRegistered(String email){
        users = TableUserController.getUsers();
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getEmail().equals(email))
                return true;
            else
                continue;
        }
        return false;
    }

    /**
     * Method activate the user's account by special page.
     *
     * @param id - user's unique id.
     */
    public void activateUser(int id){
        TableUserController.updateUser(1, id);
    }

    /**
     * Method return the amount of users.
     *
     * @return the amount of users.
     */
    public int getUsersAmount(){
        return TableUserController.getAmountOfUsers();
    }
}
