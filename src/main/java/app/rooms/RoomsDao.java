package app.rooms;

import app.db.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RoomsDao extends AbstractDao<Room> {

    private static final RoomsDao dao = new RoomsDao();

    public static void insert(int hotel_id, int number) {
        String stm = "INSERT INTO Rooms(hotel_id, number) VALUES(" + hotel_id + "," + number + "," + 2 + ")";
        dao.executeUpdate(stm, null);
    }

    public static List<Room> selectByHotelAndDates(String hotel_id, String start, String end) {
        String statement = String.format(
                "SELECT * FROM Rooms WHERE room_number " +
                        "NOT IN " +
                        "(SELECT room_number FROM Reservations " +
                        "WHERE Reservations.hotel_id = %s " +
                        "and ((Reservations.end_date > %s and Reservations.end_date < %s) " +
                        "or (Reservations.st_date < %sand Reservations.st_date > %s))"
                , hotel_id, start, end, end, start
        );

        return dao.executeQuery(statement, null);
    }

    public static List<Room> selectAll() {
        String statement = "SELECT * FROM Rooms";
        return dao.executeQuery(statement, null);
    }

    public static Room find(String hotel_id, String number) {
        String statement = "SELECT * FROM Rooms WHERE hotel_id = " + hotel_id + " and number = " + number;
        return dao.findByKey(statement, null);
    }

    @Override
    protected Function<ResultSet, List<Room>> mapToObject() {
        return (rs) -> {
            List<Room> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Room(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
