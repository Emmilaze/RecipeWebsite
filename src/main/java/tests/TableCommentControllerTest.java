package tests;

import app.comment.Comment;
import app.db.DataBaseController;
import app.db.TableCommentController;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TableCommentControllerTest {

    private int id;
    private int userId;
    private int recipeId;
    private String text;

    @BeforeEach
    public void setUp() {
        id = DataBaseController.getNextId("comments");
        userId = 1;
        recipeId = 1;
        text = "123";
        TableCommentController.insertComment(userId, recipeId, text);
    }

    @AfterEach
    public void cleanUp() {
        DataBaseController.deleteRecord("comments", id);
    }

    @Test
    void getAllCommentsByRecipe() {
        assertNotNull(TableCommentController.getAllCommentsByRecipe(1));
        assertNotEquals(0, TableCommentController.getAllCommentsByRecipe(1).size());
    }

    @Test
    void insertComment() {
        Comment comment = TableCommentController.getComment(id);
        assertNotNull(comment);
        assertEquals(userId, comment.getUserId());
        assertEquals(recipeId, comment.getRecipeId());
        assertEquals(id, comment.getId());
        assertEquals(text, comment.getText());
    }

    @Test
    void getComment() {
        Comment comment = TableCommentController.getComment(id);
        assertNotNull(comment);
        assertEquals(userId, comment.getUserId());
        assertEquals(recipeId, comment.getRecipeId());
        assertEquals(id, comment.getId());
        assertEquals(text, comment.getText());
    }
}