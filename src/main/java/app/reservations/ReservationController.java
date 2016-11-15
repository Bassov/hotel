package app.reservations;

import app.guests.Guest;
import app.guests.GuestsDao;
import app.rooms.Room;
import app.rooms.RoomsDao;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public class ReservationController {

    public static Route index = (Request request, Response response) -> {
        List<Reservation> reservations = ReservationsDao.selectAll();
        HashMap<String,Object> model = new HashMap<>();
        model.put("reservations", reservations);
        return ViewUtil.render(request, model, Path.Template.RESERVATION_INDEX);
    };

    public static Route newReservation = (Request request, Response response) -> {
        return ViewUtil.render(request, new HashMap<>(), Path.Template.RESERVATION_NEW);
    };

    public static Route create = (Request request, Response response) -> {
        String mail = request.queryMap("mail").value();
        String name = request.queryMap("name").value();
        String lastName = request.queryMap("lastName").value();
        String phone = request.queryMap("phone").value();

        Guest guest = GuestsDao.find(mail);

        if (guest == null) {
            GuestsDao.insert(mail, name, lastName, phone);
        }

        String hotel_id = request.queryMap("hotel_id").value();
        String start = request.queryMap("st_date").value();
        String end = request.queryMap("end_date").value();
        int number_of_rooms = Integer.parseInt(request.queryMap("number_of_rooms").value());
        for (int i = 0; i < number_of_rooms; i++) {
            List<Room> rooms = RoomsDao.selectByHotelAndDates(hotel_id, start, end);
            ReservationsDao.insert(mail,
                                   rooms.get(0).getNumber(),
                                   Integer.parseInt(hotel_id),
                                   Date.valueOf(start),
                                   Date.valueOf(end),
                                   false);
        }

        response.redirect(Path.Web.RESERVATIONS_INDEX);
        return null;
    };

    public static Route show = (Request request, Response response) -> {
        String reservation_id = request.params(":id");
        Reservation reservation = ReservationsDao.find(reservation_id);

        HashMap<String,Object> model = new HashMap<>();
        model.put("reservation", reservation);

        return ViewUtil.render(request, model, Path.Template.RESERVATION_SHOW);
    };

}