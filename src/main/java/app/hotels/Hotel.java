package app.hotels;

import app.employees.Employee;
import app.employees.EmployeeDao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Data @AllArgsConstructor
public class Hotel {

    int id;
    String city;
    String address;
    String owner_login;

    public static List<Hotel> all() {
        return HotelsDao.selectAll().stream()
                .sorted(comparing(Hotel::getCity))
                .collect(toList());
    }

    public String getOwnerFullName() {
        Employee emp = EmployeeDao.findByLogin(owner_login);
        return emp == null ? "Has no owner" : emp.getFullName();
    }

    public String toString() {
        return String.format("%s %s", city, address);
    }

}
