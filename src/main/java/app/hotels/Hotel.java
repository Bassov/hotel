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
    String owner_login;

    public static List<Hotel> all() {
        return HotelsDao.selectAll();
    }

    public String getOwnerFullName() {
        Employee emp = EmployeeDao.findByLogin(owner_login);
        return emp == null ? "Has no owner" : emp.getFullName();
    }

    public String toString() {
        return String.format("%s %s", city, address);
    }

}
