package tests;

import app.db.DataBaseController;
import app.db.TableDownloadController;
import app.downloads.DownloadsDao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DownloadsDaoTest {
    DownloadsDao downloadsDao = new DownloadsDao();
    int id = DataBaseController.getNextId("down");

    @Test
    void allDownloads() {
        DataBaseController.updateDataBase("INSERT into down values(default, 1, 1, 1) " +
                "ON CONFLICT ON CONSTRAINT person_down do update set downloaded = down.downloaded + 1 " +
                "WHERE down.user_id = 1 and down.recipe_id = 1;");
        TableDownloadController.downloadRecipe(1, 1);
        assertNotNull(downloadsDao.allDownloads());
        assertNotEquals(0, downloadsDao.allDownloads().size());
        DataBaseController.updateDataBase("DELETE FROM down WHERE id = " + id);
    }

    @Test
    void downloadTheRecipe() {
        DataBaseController.updateDataBase("INSERT into down values(default, 1, 1, 1) " +
                "ON CONFLICT ON CONSTRAINT person_down do update set downloaded = down.downloaded + 1 " +
                "WHERE down.user_id = 1 and down.recipe_id = 1;");
        downloadsDao.downloadTheRecipe(1, 1);
        assertNotNull(TableDownloadController.getTheDownloads());
        assertNotEquals(0, TableDownloadController.getTheDownloads());
        DataBaseController.updateDataBase("DELETE FROM down WHERE id = " + id);
    }
}