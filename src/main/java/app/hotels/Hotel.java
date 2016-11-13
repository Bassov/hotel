package app.hotels;

import lombok.Value;

@Value
public class Hotel {

    int id;
    String city;
    String address;

    public Hotel(int id, String city, String address) {
        this.id = id;
        this.city = city;
        this.address = address;
    }

}
