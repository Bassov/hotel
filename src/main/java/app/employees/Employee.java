package app.employees;

import app.employees.users.adminstrators.AdministratorDao;
import app.employees.users.managers.ManagerDao;
import app.hotels.HotelsDao;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Employee {

    int id;
    String name;
    String lastName;

    public String getStatus() {
        if (isAdmin()) {
            return "Administrator";
        }

        if (isStaffMember()) {
            return "Staff Member";
        }

        if (isManager()) {
            return "Manager of Hotel";
        }

        return "Has no role";
    }

    public boolean isAdmin() {
        String id = String.valueOf(this.id);
        return EmployeeDao.isAdministrator(id);
    }

    public boolean isStaffMember() {
        String id = String.valueOf(this.id);
        return EmployeeDao.isStaff(id);
    }

    public boolean isManager() {
        String id = String.valueOf(this.id);
        return EmployeeDao.isManager(id);
    }

    public String getHotelId() {
        if (isAdmin()) {
            return AdministratorDao.findByEmpId(id + "").getHotel_id() + "";
        }

        if (isManager()) {
            return HotelsDao.findIdByManager(ManagerDao.findByEmpId(id + "").getLogin());
        }

        return "";
    }

    public String getFullName() {
        return name + " " + lastName;
    }

}
