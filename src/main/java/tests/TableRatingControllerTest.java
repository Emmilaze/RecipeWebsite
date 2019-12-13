package tests;

import app.db.DataBaseController;
import app.db.TableRatingController;
import app.vote.Vote;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableRatingControllerTest {

    private int id;
    private int userId = 1;
    private int recipeId = 1;

    @BeforeEach
    public void setUp(){
        id = DataBaseController.getNextId("voted");
        TableRatingController.insertVote(userId, recipeId);
    }

    @AfterEach
    public void cleanUp(){
        DataBaseController.deleteRecord("voted", id);
    }

    @Test
    void insertVote() {
        Vote vote = TableRatingController.getVote(id);
        assertNotNull(vote);
        assertEquals(id, vote.getId());
        assertEquals(userId, vote.getUserId());
        assertEquals(recipeId, vote.getRecipeId());
    }

    @Test
    void getVotes() {
        assertNotNull(TableRatingController.getVotes(userId));
        assertNotEquals(0, TableRatingController.getVotes(userId).size());
    }

    @Test
    void deleteVote() {
        TableRatingController.insertVote(2, 2);
        TableRatingController.deleteVote(2, 2);
    }

    @Test
    void getVote() {
        Vote vote = TableRatingController.getVote(id);
        assertNotNull(vote);
        assertEquals(id, vote.getId());
        assertEquals(userId, vote.getUserId());
        assertEquals(recipeId, vote.getRecipeId());
    }
}