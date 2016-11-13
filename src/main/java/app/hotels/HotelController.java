package app.hotels;

import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

public class HotelController {

    public static Route indexHotels = (Request request, Response response) -> {
        return null;
    };

    public static Route newHotel = (Request request, Response response) -> {
        return ViewUtil.render(request, new HashMap<>(), Path.Template.HOTEL_NEW);
    };

    public static Route createHotel = (Request request, Response response) -> {
        System.out.println(request.queryMap().get("city").value());
        return ViewUtil.render(request, new HashMap<>(), Path.Template.HOTEL_NEW);
    };

}
