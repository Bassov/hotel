package app.hotels;

import app.login.LoginController;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;

public class HotelController {

    public static final Route index = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        List<Hotel> hotels = HotelsDao.selectAll();
        HashMap<String,Object> model = new HashMap<>();
        model.put("hotels", hotels);
        return ViewUtil.render(request, model, Path.Template.HOTEL_INDEX);
    };

    public static final Route newHotel = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        LoginController.allowManagers(request, response);
        return ViewUtil.render(request, new HashMap<>(), Path.Template.HOTEL_NEW);
    };

    public static final Route create = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        String city = request.queryMap("city").value();
        String address = request.queryMap("address").value();
        HotelsDao.insert(city, address);

        response.redirect(Path.Web.HOTELS_INDEX);
        return null;
    };

    public static final Route show = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        String hotel_id = request.params(":id");
        Hotel hotel = HotelsDao.find(hotel_id);

        HashMap<String,Object> model = new HashMap<>();
        model.put("hotel", hotel);

        return ViewUtil.render(request, model, Path.Template.HOTEL_SHOW);
    };

}
