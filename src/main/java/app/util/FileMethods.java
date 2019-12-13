package app.util;

import app.db.DataBaseController;

import java.io.File;

public class FileMethods {
    public static String renameFile(String path){
        int id = DataBaseController.getNextId("recipes");
        File oldFile = new File("./image/" + path);
        String[] words = path.split("\\.");
        for(String word : words){
            System.out.println(word);
        }
        String name = words[0] + id + "." + words[1];
        File newFile = new File("./image/" + name);
        if(!oldFile.renameTo(newFile))
            System.out.println("Can't rename the file.");
        return name;
    }
}
