package app.controllers;

import app.db.TableRecipeController;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static app.Main.recipeDao;
import static app.Main.userDao;
import static app.util.RequestUtil.*;

public class PostController {

    public final static Handler servePostPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if(userDao.u.getPrivilege() != -1) {
            model.put("currentUser", userDao.getU());
            ctx.render(Path.Template.POST, model);
        }else{
            ctx.render("/index");
        }
    };

    public final static Handler handleCreatePost = ctx -> {
        ctx.uploadedFiles("files").forEach(file -> {
            try {
                FileUtils.copyInputStreamToFile(file.getContent(),
                        new File("./src/main/resources/public/img/" + file.getFilename()));
                ctx.html("Upload successful");
            } catch (IOException e) {
                ctx.html("Upload failed");
            }

            String str = "";
            List<String> list = getQueryIngredients(ctx);
            for (int i = 0; i < list.size(); i++) {
                if (i == 0)
                    str += list.get(i);
                else
                    str += ", " + list.get(i);
            }
            String string = getQueryDate(ctx) + " " + getQueryTime(ctx);
            DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date;

            try {
                date = format1.parse(string);
                if(file.getFilename().isEmpty())
                    recipeDao.createRecipe(getQueryName(ctx), "logo.png", str, getQueryDescription(ctx),
                            date, userDao.u.getId());
                else
                recipeDao.createRecipe(getQueryName(ctx), file.getFilename(), str, getQueryDescription(ctx),
                        date, userDao.u.getId());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        ctx.redirect("/index");
    };
}
