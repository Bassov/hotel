package app.employees;

import lombok.Value;

@Value
public class Employee {

    int id;
    String name;
    String surname;

    public Employee(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

}
