package app.hotels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Data @AllArgsConstructor
public class Hotel {

    int id;
    String city;
    String address;

    public static List<Hotel> all() {
        return HotelsDao.selectAll().stream()
                .sorted(comparing(Hotel::getCity))
                .collect(toList());
    }

}
