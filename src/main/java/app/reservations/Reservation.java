package app.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data @AllArgsConstructor
public class Reservation {

    int id;
    String guest_mail;
    int room_number;
    int hotel_id;
    Timestamp st_date;
    Timestamp end_date;
    int key_id;
    boolean approved;

}
