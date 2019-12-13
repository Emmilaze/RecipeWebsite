package tests;

import app.util.Cleaner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleanerTest {

    @Test
    void removeAllTags() {
        assertEquals("Hello", Cleaner.removeAllTags("<h1>Hello<h1>"));
    }

    @Test
    void removeBadTags() {
        assertEquals("Hello", Cleaner.removeBadTags("<h1>Hello<h1>"));
    }
}