package app.util;

import app.db.DataBaseController;

import java.io.File;

public class FileMethods {
    public static String renameFile(String path){
        int id = DataBaseController.getNextId("recipes");
        File oldFile = new File("./image/" + path);
        String[] words = path.split("\\.");
        String name = words[0] + id + "." + words[1];
        File newFile = new File("./image/" + name);
        if(!oldFile.renameTo(newFile))
            System.out.println("Can't rename the file.");
        return name;
    }

    public static long getMemory(){
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
