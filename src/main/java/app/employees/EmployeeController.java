package app.employees;

import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;

public class EmployeeController {

    public static Route indexEmployees = (Request request, Response response) -> {
        List<Employee> employees = EmployeeDao.selectAll();
        HashMap<String,Object> model = new HashMap<>();
        model.put("employees", employees);
        return ViewUtil.render(request, model, Path.Template.EMPLOYEES_INDEX);
    };

}
