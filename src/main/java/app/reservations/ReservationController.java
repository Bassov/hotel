package app.reservations;

import app.guests.Guest;
import app.guests.GuestsDao;
import app.hotels.Hotel;
import app.rooms.Room;
import app.rooms.RoomsDao;
import app.util.Path;
import app.util.SqlUtil;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.URLDecoder;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import static app.login.LoginController.ensureUserIsLoggedIn;
import static app.util.RequestUtil.queryValue;

public class ReservationController {

    public static final Route newReservation = (Request request, Response response) -> {
        HashMap<String,Object> model = new HashMap<>();
        model.put("hotels", Hotel.all());
        return ViewUtil.render(request, model, Path.Template.RESERVATION_NEW);
    };

    public static final Route create = (Request request, Response response) -> {
        try {
            String mail = URLDecoder.decode(queryValue(request, "email"), "UTF-8");
            String name = queryValue(request, "name");
            String lastName = queryValue(request, "lastName");
            String hotel_id = queryValue(request, "hotelId");
            Date start = SqlUtil.parseDate(queryValue(request, "stDate"));
            Date end = SqlUtil.parseDate(queryValue(request, "endDate"));
            int number_of_rooms = Integer.parseInt(queryValue(request, "number"));

            Guest guest = GuestsDao.find(mail);

            if (guest == null) {
                GuestsDao.insert(mail, name, lastName);
            }

            List<Room> rooms = RoomsDao.selectByHotelAndDates(hotel_id, start, end);

            if (rooms.isEmpty() || rooms.size() < number_of_rooms || number_of_rooms <= 0) {
                response.redirect(Path.Web.RESERVATIONS_ERROR);
                return null;
            }

            for (int i = 0; i < number_of_rooms; i++) {
                rooms = RoomsDao.selectByHotelAndDates(hotel_id, start, end);

                ReservationsDao.insert(
                        mail,
                        rooms.get(0).getNumber(),
                        Integer.parseInt(hotel_id),
                        start,
                        end
                );

            }

            response.redirect(Path.Web.RESERVATIONS_SUCCESS);
        } catch (Exception e) {
            response.redirect(Path.Web.RESERVATIONS_ERROR);
        }
        return null;
    };

    public static Route show = (Request request, Response response) -> {
        ensureUserIsLoggedIn(request, response);
        String reservation_id = request.params(":id");
        Reservation reservation = ReservationsDao.find(reservation_id);
        Guest guest = GuestsDao.find(reservation.getGuest_mail());
        HashMap<String, Object> model = new HashMap<>();
        model.put("reservation", reservation);
        model.put("guest", guest);

        return ViewUtil.render(request, model, Path.Template.RESERVATION_SHOW);
    };

    public static final Route approve = (Request request, Response response) -> {
        ensureUserIsLoggedIn(request, response);
        String reservation_id = request.params(":id");
        ReservationsDao.setApproved(reservation_id);
        response.redirect(Path.Web.DASHBOARD);
        return null;
    };

    public static final Route delete = (Request request, Response response) -> {
        ensureUserIsLoggedIn(request, response);
        String reservation_id = request.params(":id");
        ReservationsDao.deleteById(reservation_id);
        response.redirect(Path.Web.DASHBOARD);
        return null;
    };

    public static final Route success = (Request request, Response response) -> {
        return ViewUtil.render(request, new HashMap<>(), Path.Template.RESERVATION_SUCCESS);
    };

    public static final Route error = (Request request, Response response) -> {
        return ViewUtil.render(request, new HashMap<>(), Path.Template.RESERVATION_ERROR);
    };

}