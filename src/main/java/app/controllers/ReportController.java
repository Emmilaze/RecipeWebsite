package app.controllers;

import app.util.Cleaner;
import app.util.Path;
import app.util.ViewUtil;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import io.sentry.Sentry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static app.Main.emailSenderTLS;
import static app.util.RequestUtil.getReport;

/**
 * Class controller of report page.
 */
public class ReportController {

    /**
     * Serve the page with reports.
     */
    @OpenApi(
            path = "/report",
            method = HttpMethod.GET,
            summary = "Send an email from users to administration with bugs and problems",
            description = "Reports",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler serveReportPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Path.Template.REPORT, model);
    };

    /**
     * Send the report to the administration by email.
     */
    @OpenApi(
            path = "/report",
            method = HttpMethod.POST,
            summary = "Send an email from users to administration with bugs and problems",
            description = "Reports",
            tags = "Cook Eat Repeat",
            responses = {
                    @OpenApiResponse(status = "500", description = "The error is not with you, but with us on the server. We apologize.",
                            content = @OpenApiContent(type = "application/json", from = ViewUtil.class)),
                    @OpenApiResponse(status = "404", description = "Nothing has matched your criterias.")
            }
    )
    public final static Handler makeReport = ctx -> {
        ArrayList<String> paths = new ArrayList<>();
        ctx.uploadedFiles("files").forEach(file -> {
            try {
                FileUtils.copyInputStreamToFile(file.getContent(),
                        new File("./filesReports/" + file.getFilename()));
            } catch (IOException e) {
                System.out.println(e);
                Sentry.capture(e);
            }
            if (file.getFilename().isEmpty()) {
                emailSenderTLS.send("Bag report", Cleaner.removeAllTags(getReport(ctx)),
                         "g3@tss2019.repositoryhosting.com");
                ctx.redirect("/report");
            } else {
                paths.add("filesReports/" + file.getFilename());
            }

        });
        emailSenderTLS.send("Bag report", Cleaner.removeAllTags(getReport(ctx)),
                 "g3@tss2019.repositoryhosting.com", paths);
        ctx.redirect("/report");
    };
}

