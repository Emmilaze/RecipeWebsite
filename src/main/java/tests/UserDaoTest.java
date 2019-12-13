package tests;

import app.db.DataBaseController;
import app.db.TableUserController;
import app.user.User;
import app.user.UserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private int id;
    private String email;
    private String username;
    private String salt;
    private String password;
    private UserDao userDao = new UserDao();

    @BeforeEach
    public void setUp(){
        id = DataBaseController.getNextId("users");
        email = "email";
        username = "username";
        salt = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";
        password = "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO";
        TableUserController.insertUser(email, username, salt, password);
    }

    @AfterEach
    public void clean(){
        DataBaseController.deleteRecord("users", id);
    }

    @Test
    void getAllUsers() {
        assertNotNull(userDao.getAllUsers());
    }

    @Test
    void getUserByUsername() {
        User u1 = TableUserController.getUser(id);
        User u2 = userDao.getUserByUsername(username);
        assertNotNull(u2);
        assertEquals(u1.getId(), u2.getId());
        assertEquals(u1.getEmail(), u2.getEmail());
        assertEquals(u1.getUsername(), u2.getUsername());
        assertEquals(u1.getSalt(), u2.getSalt());
        assertEquals(u1.getHashedPassword(), u2.getHashedPassword());
        assertEquals(u1.getPrivilege(), u2.getPrivilege());
    }

    @Test
    void getHashedPassword() {
        assertEquals(password, userDao.getHashedPassword("password", salt));
    }

    @Test
    void changePassword() {
        userDao.changePassword("pass", id, userDao);
        User user = TableUserController.getUser(id);
        String hP = userDao.getHashedPassword("pass", user.getSalt());
        assertEquals(hP, user.getHashedPassword());
    }

    @Test
    void blockUser() {
        userDao.blockUser(id);
        User user = TableUserController.getUser(id);
        assertNotNull(user);
        assertEquals(-1, user.getPrivilege());
    }

    @Test
    void createUser() {
        int id = DataBaseController.getNextId("users");
        String email = "e";
        String username = "u";
        String password = "pass";
        userDao.createUser(email, username, password, userDao);

        User user = TableUserController.getUser(id);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        String hP = userDao.getHashedPassword(password, user.getSalt());
        assertEquals(hP, user.getHashedPassword());
        DataBaseController.deleteRecord("users", id);
    }

    @Test
    void haveRegistered() {
        assertTrue(userDao.haveRegistered(email));
        assertFalse(userDao.haveRegistered("123"));
    }

    @Test
    void activateUser() {
        userDao.activateUser(id);
        User user = TableUserController.getUser(id);
        assertNotNull(user);
        assertEquals(1, user.getPrivilege());
    }

    @Test
    void getUsersAmount() {
        int amount = userDao.getUsersAmount();
        assertNotEquals(0, amount);
    }
}