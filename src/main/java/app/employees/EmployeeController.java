package app.employees;

import app.employees.staff.StaffMemberDao;
import app.employees.users.UserDao;
import app.employees.users.adminstrators.AdministratorDao;
import app.employees.users.owners.OwnerDao;
import app.hotels.Hotel;
import app.hotels.HotelsDao;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;

import static app.util.RequestUtil.queryValue;

public class EmployeeController {

    public static Route index = (Request request, Response response) -> {
        List<Employee> employees = EmployeeDao.selectAll();
        HashMap<String,Object> model = new HashMap<>();
        model.put("employees", employees);
        return ViewUtil.render(request, model, Path.Template.EMPLOYEES_INDEX);
    };

    public static Route newEmployee = (Request request, Response response) -> {
        HashMap<String,Object> model = new HashMap<>();
        model.put("hotels", Hotel.all());
        return ViewUtil.render(request, model, Path.Template.EMPLOYEES_NEW);
    };

    public static Route create = (Request request, Response response) -> {
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
            case "owner":
                UserDao.insert(emp_id, login, password);
                OwnerDao.insert(login);
                HotelsDao.setOwner(hotelId, login);
                break;
        }

        response.redirect(Path.Web.HOTELS_INDEX);
        return null;
    };

}
