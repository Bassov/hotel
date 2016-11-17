package app.rooms;

import app.db.AbstractDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class RoomsDao extends AbstractDao<Room> {

    private static final RoomsDao dao = new RoomsDao();

    public static void insert(int hotel_id, int number) {
        String stm = "INSERT INTO Rooms(hotel_id, number) VALUES(" + hotel_id + "," + number + "," + 2 + ")";
        dao.executeUpdate(stm, null);
    }

    public static List<Room> selectByHotel(String hotel_id){
        String statement = "SELECT * FROM Rooms WHERE hotel_id = " + hotel_id;
        return dao.executeQuery(statement, null);
    }

    public static List<Room> selectByHotelAndDates(String hotel_id, Date start, Date end) {

        String statement =
                "SELECT * FROM Rooms WHERE hotel_id = " + hotel_id + " AND number " +
                        "NOT IN " +
                        "(SELECT Reservations.room_number FROM Reservations " +
                        "WHERE hotel_id = '" +hotel_id+"' " +
                        "and ((end_date > ? and end_date < ?) " +
                        "or (st_date < ? and st_date > ?)" +
                        "or (st_date < ? and end_date > ?)" +
                        "or st_date = ? or st_date = ? " +
                        "or end_date = ? or end_date = ?))";

        try (
                Connection con = createConnection();
                PreparedStatement pst = con.prepareStatement(statement)
        ){

            pst.setDate(1, start);
            pst.setDate(4, start);

            pst.setDate(2, end);
            pst.setDate(3, end);

            pst.setDate(5, start);
            pst.setDate(6, end);

            pst.setDate(7, start);
            pst.setDate(8, end);

            pst.setDate(9, start);
            pst.setDate(10, end);

            ResultSet rst = pst.executeQuery();
            return dao.mapToObject().apply(rst);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
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
