package app.employees;

import app.employees.users.adminstrators.AdministratorDao;
import app.employees.users.owners.OwnerDao;
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

        if (isOwner()) {
            return "Owner of Hotel";
        }

        return "Hui kakoi-to";
    }

    public boolean isAdmin() {
        String id = String.valueOf(this.id);
        return EmployeeDao.isAdministrator(id);
    }

    public boolean isStaffMember() {
        String id = String.valueOf(this.id);
        return EmployeeDao.isStaff(id);
    }

    public boolean isOwner() {
        String id = String.valueOf(this.id);
        return EmployeeDao.isOwner(id);
    }

    public String getHotelId() {
        if (isAdmin()) {
            return AdministratorDao.findByEmpId(id + "").getHotel_id() + "";
        }

        if (isOwner()) {
            return HotelsDao.findIdByOwner(OwnerDao.findByEmpId(id + "").getLogin());
        }

        return "";
    }

    public String getFullName() {
        return name + " " + lastName;
    }

}
