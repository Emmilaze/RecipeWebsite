package app.downloads;

import app.db.TableDownloadController;

import java.util.List;

public class DownloadsDao {

    /**
     * Method return list with downloads.
     */
    public List<Downloads> allDownloads() {
        return TableDownloadController.getTheDownloads();
    }

    /**
     * Method make/update record about downloading.
     */
    public void downloadTheRecipe(int userId, int recipeId) {
        TableDownloadController.downloadRecipe(userId, recipeId);
    }
}
