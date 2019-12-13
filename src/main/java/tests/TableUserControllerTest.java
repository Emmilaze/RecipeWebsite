package tests;

import app.db.DataBaseController;
import app.db.TableUserController;
import app.user.User;
import app.user.UserDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableUserControllerTest {

    private int id;
    private String email = "email";
    private String username = "username";
    private String salt = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";
    private String password = "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO";
    private UserDao userDao = new UserDao();

    @BeforeEach
    public void setUp(){
        id = DataBaseController.getNextId("users");
        TableUserController.insertUser(email, username, salt, password);
    }

    @AfterEach
    public void clean(){
        DataBaseController.deleteRecord("users", id);
    }

    @Test
    void insertUser() {
        User user = TableUserController.getUser(id);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(salt, user.getSalt());
        assertEquals(password, user.getHashedPassword());
    }

    @Test
    void getUser() {
        User user = TableUserController.getUser(id);
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
        assertEquals(salt, user.getSalt());
        assertEquals(password, user.getHashedPassword());
    }

    @Test
    void updateUser() {
        TableUserController.updateUser( 1, id);
        User user = TableUserController.getUser(id);
        assertNotNull(user);
        assertEquals("test", user.getEmail());
    }

    @Test
    void updatePassword() {
        User user = TableUserController.getUser(id);
        String newPass = userDao.getHashedPassword("pass", user.getSalt());
        TableUserController.updatePassword(user.getSalt(), newPass, id);
        user = TableUserController.getUser(id);
        assertNotNull(user);
        assertEquals(newPass, user.getHashedPassword());
    }

    @Test
    void getUsers() {
        assertNotNull(TableUserController.getUsers().size());
        assertNotEquals(0, TableUserController.getUsers().size());
    }

    @Test
    void getUsername() {
        assertNotEquals("", TableUserController.getUsername(id));
        assertEquals("username", TableUserController.getUsername(id));
    }

    @Test
    void getAmountOfUsers() {
        assertNotEquals(0, TableUserController.getAmountOfUsers());
    }
}