package app.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data @AllArgsConstructor
public class Reservation {

    int id;
    String guest_mail;
    int room_number;
    int hotel_id;
    Date st_date;
    Date end_date;
    int key_id;
    boolean approved;

}
