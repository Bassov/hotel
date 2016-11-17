package app.hotels;

import app.employees.Employee;
import app.employees.EmployeeDao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class Hotel {

    int id;
    String city;
    String address;
    String manager_login;

    public static List<Hotel> all() {
        return HotelsDao.selectAll();
    }

    public String getManagerFullName() {
        Employee emp = EmployeeDao.findByLogin(manager_login);
        return emp == null ? "Has no manager" : emp.getFullName();
    }

    public String toString() {
        return String.format("%s %s", city, address);
    }

}
