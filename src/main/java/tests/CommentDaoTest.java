package tests;

import app.comment.Comment;
import app.comment.CommentDao;
import app.db.DataBaseController;
import app.db.TableCommentController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentDaoTest {

    private int id;
    private int userId;
    private int recipeId;
    private String text;
    private CommentDao commentDao = new CommentDao();

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
    void createComment() {
        commentDao.createComment(userId, recipeId, text);
        Comment comment = TableCommentController.getComment(id);
        assertNotNull(comment);
        assertEquals(userId, comment.getUserId());
        assertEquals(recipeId, comment.getRecipeId());
        assertEquals(id, comment.getId());
        assertEquals(text, comment.getText());
    }

    @Test
    void getCommentByRecipe() {
        commentDao.getCommentByRecipe(recipeId);
        assertNotNull(commentDao.comments);
        assertNotEquals(0, commentDao.comments.size());
    }
}