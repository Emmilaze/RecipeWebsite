package app.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao {

    public List<User> users = new ArrayList<User>(Arrays.asList());

    public User getUserById(int id) {
        return users.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public Iterable<String> getAllUserNames() {
        return users.stream().map(user -> user.getUsername()).collect(Collectors.toList());
    }

    public String getHashedPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
