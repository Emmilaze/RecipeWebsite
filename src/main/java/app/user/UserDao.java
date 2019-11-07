package app.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.db.DataBaseController;
import app.db.TableUserController;
import org.mindrot.jbcrypt.BCrypt;

import static app.Main.userDao;

public class UserDao {

    public List<User> users = new ArrayList<User>(TableUserController.getUsers());

    public Iterable<User> getAllUsers() {
        users = TableUserController.getUsers();
        return users;
    }

    public User u = null;

    public User getUserById(int id) {
        return users.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public User getUserByUsername(String username) {
        return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
    }

    public Iterable<String> getAllUserNames() {
        return users.stream().map(user -> user.getUsername()).collect(Collectors.toList());
    }

    public String getHashedPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public void changePassword(String newPassword, int id) {
        String salt = BCrypt.gensalt();
        String password = userDao.getHashedPassword(newPassword, salt);
        TableUserController.updatePassword(salt, password, id);
    }

    public void changeUsername(String username, int id) {
        TableUserController.updateUser("username", username, id);
    }

    public void changeEmail(String email, int id) {
        TableUserController.updateUser("email", email, id);
    }

    public void blockUser(int id) {
        TableUserController.updateUser("privilege", "-1", id);
    }

    public User createUser(String email, String login, String password) {
        String salt = BCrypt.gensalt();
        String hashedPassword = userDao.getHashedPassword(password, salt);
        TableUserController.insertUser(email, login, salt, hashedPassword);
        User user = new User(DataBaseController.getNextId("users") - 1, email, login, salt, hashedPassword, 1);
        users.add(user);
        return user;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public boolean haveRegistered(String email){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getEmail().equals(email))
                return true;
            else
                continue;
        }
        return false;
    }
}
