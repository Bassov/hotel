package app.login;

import app.employees.Employee;
import app.employees.EmployeeDao;
import app.employees.users.UserController;
import app.hotels.Hotel;
import app.hotels.HotelsDao;
import app.reservations.Reservation;
import app.reservations.ReservationsDao;
import app.util.Path;
import app.util.RequestUtil;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static app.util.RequestUtil.*;

public class LoginController {

    public static final Route login = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static final Route create = (Request request, Response response) -> {
        String login = queryValue(request, "login");
        String password = queryValue(request, "password");

        Map<String, Object> model = new HashMap<>();
        if (!UserController.authenticate(login, password)) {
            model.put("alert", "Wrong login or password");
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }

        Employee emp = EmployeeDao.findByLogin(login);
        model.put("success", "You successfully logged in " + emp.getFullName());
        request.session().attribute("currentUser", login);
        response.redirect(Path.Web.DASHBOARD);
        return null;
    };

    public static final Route logout = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.RESERVATIONS_NEW);
        return null;
    };

    public static boolean ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            response.redirect(Path.Web.LOGIN);
            return false;
        }
        return true;
    }

    public static void allowManagers(Request request, Response response) {
        Employee emp = RequestUtil.getSessionCurrentUser(request);
        if (!emp.isManager()) {
            request.session().attribute("alert", "You are not manager of hotel");
            response.redirect(Path.Web.DASHBOARD);
        }
    }

    public static final Route dashboard = (Request request, Response response) -> {
        if (!ensureUserIsLoggedIn(request, response)) {
            return null;
        }
        Map<String,Object> model = new HashMap<>();
        Employee emp = RequestUtil.getSessionCurrentUser(request);
        String hotelId = emp.getHotelId();
        List<Reservation> all = ReservationsDao.selectByHotelId(hotelId);

        List<Reservation> approved = all.stream()
                .filter(Reservation::isApproved)
                .collect(Collectors.toList());
        List<Reservation> notApproved = all.stream()
                .filter(r -> !r.isApproved())
                .collect(Collectors.toList());

        Hotel hotel = HotelsDao.find(hotelId);
        Employee manager = EmployeeDao.findByLogin(hotel.getManager_login());

        model.put("approved", approved);
        model.put("notApproved", notApproved);
        model.put("hotel", hotel);
        model.put("manager", manager);

        return ViewUtil.render(request, model, Path.Template.DASHBOARD);
    };

}
