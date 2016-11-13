package app.hotels;

import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;

public class HotelController {

    public static Route indexHotels = (Request request, Response response) -> {
        List<Hotel> hotels = HotelsDao.selectAll();
        HashMap<String,Object> model = new HashMap<>();
        model.put("hotels", hotels);
        return ViewUtil.render(request, model, Path.Template.HOTEL_INDEX);
    };

    public static Route newHotel = (Request request, Response response) -> {
        return ViewUtil.render(request, new HashMap<>(), Path.Template.HOTEL_NEW);
    };

    public static Route createHotel = (Request request, Response response) -> {
        String city = request.queryMap("city").value();
        String address = request.queryMap("address").value();
        HotelsDao.insert(city, address);
        return ViewUtil.render(request, new HashMap<>(), Path.Template.HOTEL_NEW);
    };

}
