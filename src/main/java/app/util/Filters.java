package app.util;

import spark.Filter;
import spark.Request;
import spark.Response;

public class Filters {

    // If a user manually manipulates paths and forgets to add
    // a trailing slash, redirect the user to the correct path
    public static final Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };

    // Enable GZIP for all responses
    public static final Filter addGzipHeader = (Request request, Response response) -> response.header("Content-Encoding", "gzip");

    public static final Filter removeMsg = (Request request, Response response) -> request.session().removeAttribute("alert");

}