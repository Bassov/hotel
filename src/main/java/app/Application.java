package app;

import spark.route.RouteOverview;

import static spark.Spark.get;

public class Application {

    public static void main(String[] args) {
        get("/hello", (request, response) -> "Hello World");
        RouteOverview.enableRouteOverview();
    }

}
