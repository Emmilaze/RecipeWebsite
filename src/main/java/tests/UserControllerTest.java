package tests;

import app.user.UserController;
import app.user.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private String username;
    private String password;
    private UserDao userDao = new UserDao();

    @BeforeEach
    public void setUp(){
        username = "admin";
        password = "password";
    }

    @Test
    void authenticate() {
        assertTrue(UserController.authenticate(username, password, userDao));
        assertFalse(UserController.authenticate(null, null, userDao));
        assertFalse(UserController.authenticate(null, password, userDao));
        assertFalse(UserController.authenticate(username, null, userDao));
    }
}