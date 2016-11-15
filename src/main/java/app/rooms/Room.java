package app.rooms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Room {

    int hotel_id;
    int number;
    int capacity = 2;

}
