package app.reservations;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
public class Reservation {

    int id;
    String guest_mail;
    int room_number;
    int hotel_id;
    Date st_date;
    Date end_date;
    boolean approved;

    public static List<Reservation> all() {
        return ReservationsDao.selectAll().stream()
                .sorted(comparing(Reservation::getId))
                .collect(toList());
    }

}
