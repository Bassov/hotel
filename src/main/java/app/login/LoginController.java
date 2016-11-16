package app.login;

import app.employees.Employee;
import app.employees.EmployeeDao;
import app.employees.users.UserController;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static app.util.RequestUtil.*;

public class LoginController {

    public static Route login = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route create = (Request request, Response response) -> {
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

    public static Route logout = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.RESERVATIONS_NEW);
        return null;
    };

    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            response.redirect(Path.Web.RESERVATIONS_NEW);
        }
    }

    public static void allowOwners(Request request, Response response) {
        Employee emp = EmployeeDao.findByLogin(request.session().attribute("currentUser"));
        if (!emp.isOwner()) {
            request.session().attribute("alert", "You are not owner of hotel");
            response.redirect(Path.Web.DASHBOARD);
        }
    }

    public static Route dashboard = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        return ViewUtil.render(request, new HashMap<>(), Path.Template.DASHBOARD);
    };

}
