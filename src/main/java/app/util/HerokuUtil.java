package app.util;

/**
 * Class to open port.
 */
public class HerokuUtil {

    /**
     * Method that open port, if it is not open.
     *
     * @return number of port.
     */
    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000;
    }
}
