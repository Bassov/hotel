package app;

import app.hotels.HotelController;
import app.util.Path;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {
        get("/hello", (request, response) -> "Hello World");

        // Hotels
        get(Path.Web.HOTELS_NEW, HotelController.newHotel);
        get(Path.Web.HOTELS_INDEX, HotelController.indexHotels);
        post(Path.Web.HOTELS_CREATE, HotelController.createHotel);
    }

}
