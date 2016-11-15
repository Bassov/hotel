package app.employees;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Employee {

    int id;
    String name;
    String surname;

    public String getStatus() {
        String id = String.valueOf(this.id);
        if (EmployeeDao.isAdministrator(id)) {
            return "Administrator";
        }

        if (EmployeeDao.isStaff(id)) {
            return "Staff Member";
        }

        if (EmployeeDao.isOwner(id)) {
            return "Owner of Hotel";
        }

        return "Hui kakoi-to";
    }

}
