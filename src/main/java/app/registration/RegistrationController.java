package app.registration;

import app.util.Path;
import io.javalin.http.Handler;

public class RegistrationController {
    public static Handler serveRegistrationPage = ctx -> {
        //add registration
        ctx.render(Path.Template.REGISTRATION);
    };
}
