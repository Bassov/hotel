package app;

import app.employees.EmployeeController;
import app.hotels.HotelController;
import app.login.LoginController;
import app.reservations.ReservationController;
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
        post(Path.Web.EMPLOYEES_CREATE, EmployeeController.create);

        //Reservations
        get(Path.Web.RESERVATIONS_INDEX, ReservationController.index);
        get(Path.Web.RESERVATIONS_NEW, ReservationController.newReservation);
        post(Path.Web.RESERVATIONS_CREATE, ReservationController.create);

        //Login
        get(Path.Web.LOGIN, LoginController.login);
        post(Path.Web.LOGIN_POST, LoginController.create);

        //Dashboard
        get(Path.Web.DASHBOARD, LoginController.dashboard);
    }

}
