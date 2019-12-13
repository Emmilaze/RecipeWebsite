package tests;

import app.util.HerokuUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HerokuUtilTest {

    @Test
    void getHerokuAssignedPort() {
        assertEquals(7000, HerokuUtil.getHerokuAssignedPort());
    }
}