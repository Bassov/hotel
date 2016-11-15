package app.guests;

import lombok.Value;

@Value
public class Guest {

    String mail;
    String name;
    String lastName;
    String phone;

    public Guest(String mail, String name, String lastName, String phone) {
        this.mail = mail;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }
}
