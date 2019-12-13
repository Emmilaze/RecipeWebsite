package app.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Class to clean tags.
 */
public class Cleaner {

    /**
     * Method clean the all tags from the string.
     */
    public static String removeAllTags(String string){
        try {
            return Jsoup.clean(string, Whitelist.none());
        }catch (Exception e){
            System.out.println(e);
            return "BadTag";
        }
    }

    /**
     * Method clean the all tags that don't match the allowed tags from the string.
     */
    public static String removeBadTags(String string){
        try {
            return Jsoup.clean(string, Whitelist.basic());
        }catch (Exception e){
            System.out.println(e);
            return "BadTag";
        }
    }
}
