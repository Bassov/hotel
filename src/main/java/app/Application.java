package app;

import app.employees.EmployeeController;
import app.hotels.HotelController;
import app.util.Path;

import static spark.Spark.*;

public class Application {

    public static void main(String[] args) {
        // Configure Spark
        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);

        // Hotels
        get(Path.Web.HOTELS_NEW, HotelController.newHotel);
        get(Path.Web.HOTELS_INDEX, HotelController.index);
        get(Path.Web.HOTELS_SHOW, HotelController.show);
        post(Path.Web.HOTELS_CREATE, HotelController.create);

        // Employees
        get(Path.Web.EMPLOYEES_INDEX, EmployeeController.index);
        get(Path.Web.EMPLOYEES_NEW, EmployeeController.newEmployee);
    }

}
