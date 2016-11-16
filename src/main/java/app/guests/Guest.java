package app.guests;

import lombok.Value;

@Value
public class Guest {

    String mail;
    String name;
    String lastName;

    public Guest(String mail, String name, String lastName) {
        this.mail = mail;
        this.name = name;
        this.lastName = lastName;
    }
}
