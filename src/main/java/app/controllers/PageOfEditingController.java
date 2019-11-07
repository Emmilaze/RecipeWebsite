package app.controllers;

import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static app.Main.recipeDao;
import static app.Main.userDao;
import static app.util.RequestUtil.*;
import static app.util.RequestUtil.getQueryDescription;
import static app.util.RequestUtil.getQueryName;

public class PageOfEditingController {
    public final static Handler serveEditPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("currentUser", userDao.getU());
        model.put("recipe", recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx))));
        model.put("str", "");
        ctx.render(Path.Template.EDIT, model);
    };

    public final static Handler handleEditPost = ctx -> {
        ctx.uploadedFiles("files").forEach(file -> {
            try {
                FileUtils.copyInputStreamToFile(file.getContent(),
                        new File("./src/main/resources/public/img/" + file.getFilename()));

            } catch (IOException e) {

            }

            String str = "";
            List<String> list = getQueryIngredients(ctx);
            for (int i = 0; i < list.size(); i++) {
                if (i == 0)
                    str += list.get(i);
                else
                    str += ", " + list.get(i);
            }
            if (file.getFilename().isEmpty()) {
                recipeDao.updateRecipe(getQueryName(ctx),
                        recipeDao.getRecipeById(Integer.parseInt(getParamId(ctx))).getImage(),
                        getQueryDescription(ctx), str, Integer.parseInt(getParamId(ctx)));
            } else {
                recipeDao.updateRecipe(getQueryName(ctx), file.getFilename(), getQueryDescription(ctx), str,
                        Integer.parseInt(getParamId(ctx)));
            }
        });
        ctx.redirect("/index");
    };
}
