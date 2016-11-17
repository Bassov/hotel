package app.reservations;

import app.guests.Guest;
import app.guests.GuestsDao;
import app.hotels.Hotel;
import app.login.LoginController;
import app.rooms.Room;
import app.rooms.RoomsDao;
import app.util.Path;
import app.util.SqlUtil;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import static app.util.RequestUtil.queryValue;

public class ReservationController {

    public static Route index = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        List<Reservation> reservations = ReservationsDao.selectAll();
        HashMap<String, Object> model = new HashMap<>();
        model.put("reservations", reservations);
        return ViewUtil.render(request, model, Path.Template.RESERVATION_INDEX);
    };

    public static Route newReservation = (Request request, Response response) -> {
        HashMap<String,Object> model = new HashMap<>();
        model.put("hotels", Hotel.all());
        return ViewUtil.render(request, model, Path.Template.RESERVATION_NEW);
    };

    public static Route create = (Request request, Response response) -> {
        String mail = URLDecoder.decode(queryValue(request, "email"),  "UTF-8");
        String name = queryValue(request, "name");
        String lastName = queryValue(request, "lastName");

        Guest guest = GuestsDao.find(mail);

        if (guest == null) {
            GuestsDao.insert(mail, name, lastName);
        }

        String hotel_id = queryValue(request, "hotelId");
        String start = URLDecoder.decode(queryValue(request, "stDate"), "UTF-8");
        String end = URLDecoder.decode(queryValue(request, "endDate"), "UTF-8");

        int number_of_rooms = Integer.parseInt(queryValue(request, "number"));

        List<Room> rooms;
        for (int i = 0; i < number_of_rooms; i++) {
            rooms = RoomsDao.selectByHotelAndDates(hotel_id, start, end);
            if (rooms.isEmpty()){
            } else {
                ReservationsDao.insert(mail,
                        rooms.get(0).getNumber(),
                        Integer.parseInt(hotel_id),
                        SqlUtil.parseDate(start),
                        SqlUtil.parseDate(end),
                        false);
            }
        }

        response.redirect(Path.Web.RESERVATIONS_INDEX);
        return null;
    };

    public static Route show = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        String reservation_id = request.params(":id");
        Reservation reservation = ReservationsDao.find(reservation_id);

        HashMap<String, Object> model = new HashMap<>();
        model.put("reservation", reservation);

        return ViewUtil.render(request, model, Path.Template.RESERVATION_SHOW);
    };

}