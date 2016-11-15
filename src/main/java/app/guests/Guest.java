package app.guests;

import lombok.Value;

@Value
public class Guest {

    String mail;
    String name;
    String surename;
    String phone;

    public Guest(String mail, String name, String surename, String phone) {
        this.mail = mail;
        this.name = name;
        this.surename = surename;
        this.phone = phone;
    }
}
