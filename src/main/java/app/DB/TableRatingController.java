package app.DB;

import static app.DB.DataBaseController.c;
import static app.DB.DataBaseController.connect;
import static app.DB.DataBaseController.stmt;

public class TableRatingController {
    public static void updateRating(int id, String name){
        try {
            connect();
            stmt = c.createStatement();
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) "
                    + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}
