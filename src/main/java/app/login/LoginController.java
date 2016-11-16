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

        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", login);
        response.redirect(Path.Web.DASHBOARD);
        return null;
    };

    public static Route dashboard = (Request request, Response response) -> {
        return ViewUtil.render(request, new HashMap<>(), Path.Template.DASHBOARD);
    };

}
