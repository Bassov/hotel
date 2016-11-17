package app.employees;

import app.employees.staff.StaffMemberDao;
import app.employees.users.UserDao;
import app.employees.users.adminstrators.AdministratorDao;
import app.employees.users.managers.ManagerDao;
import app.hotels.Hotel;
import app.hotels.HotelsDao;
import app.login.LoginController;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;

import static app.util.RequestUtil.queryValue;

public class EmployeeController {

    public static final Route index = (Request request, Response response) -> {
        HashMap<String,Object> model = new HashMap<>();
        try {
            LoginController.ensureUserIsLoggedIn(request, response);
            String hotel_id = request.params(":id");
            List<Employee> employees = EmployeeDao.selectByHotel(hotel_id);
            model.put("employees", employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ViewUtil.render(request, model, Path.Template.EMPLOYEES_INDEX);
    };

    public static final Route newEmployee = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        HashMap<String,Object> model = new HashMap<>();
        model.put("hotels", Hotel.all());
        return ViewUtil.render(request, model, Path.Template.EMPLOYEES_NEW);
    };

    public static final Route create = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        String name = queryValue(request, "name");
        String lastName = queryValue(request, "lastName");
        String hotelId = queryValue(request, "hotelId");
        String role = queryValue(request, "role");
        String login = queryValue(request, "login");
        String password = queryValue(request, "password");

        if (UserDao.loginExist(login)) {
            HashMap<String,Object> model = new HashMap<>();
            model.put("hotels", Hotel.all());
            model.put("alert", "Login already exist!");
            return ViewUtil.render(request, model, Path.Template.EMPLOYEES_NEW);
        }

        String emp_id = String.valueOf(EmployeeDao.insert(name, lastName));

        switch (role) {
            case "staff":
                StaffMemberDao.insert(emp_id, hotelId);
                break;
            case "admin":
                UserDao.insert(emp_id, login, password);
                AdministratorDao.insert(login, hotelId);
                break;
            case "manager":
                UserDao.insert(emp_id, login, password);
                ManagerDao.insert(login);
                HotelsDao.setManager(hotelId, login);
                break;
        }

        response.redirect(Path.Web.HOTELS_INDEX);
        return null;
    };

}
