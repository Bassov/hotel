package app.employees.staff;

import app.employees.Employee;

public class StaffMember extends Employee {

    int emp_id;
    int hotel_id;

    public StaffMember(int id, String name, String surname) {
        super(id, name, surname);
    }
}
