package app.util;

import io.javalin.plugin.openapi.OpenApiOptions;
import io.swagger.v3.oas.models.info.Info;

public class OpenAPI {
    public static OpenApiOptions getOpenApiOptions() {
        Info applicationInfo = new Info()
                .version("1.0")
                .description("Cook Eat Repeat Documentation");
        return new OpenApiOptions(applicationInfo).path("/swagger-docs");
    }
}
