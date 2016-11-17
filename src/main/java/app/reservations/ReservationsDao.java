package app.reservations;

import app.db.AbstractDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ReservationsDao extends AbstractDao<Reservation> {

    private static final ReservationsDao dao = new ReservationsDao();

    public static void insert(String guest_mail,
                              int room_number,
                              int hotel_id,
                              Date st_date,
                              Date end_date,
                              boolean approved) {
        String stm = "INSERT INTO Reservations( guest_mail, room_number, hotel_id, st_date," +
                "end_date, approved) VALUES('"+guest_mail+"', '" + room_number + "','" +
                hotel_id + "', ?, ? ,'" + approved + "')";
        try (
                Connection con = createConnection();
                PreparedStatement pst = con.prepareStatement(stm);
        ) {
            pst.setDate(1, st_date);
            pst.setDate(2, end_date);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Reservation> selectAll() {
        String statement = "SELECT * FROM reservations";
        return dao.executeQuery(statement, null);
    }

    public static List<Reservation> selectByHotelId(String id) {
        String statement = "SELECT * FROM reservations WHERE hotel_id = "+id;
        return dao.executeQuery(statement, null);
    }

    public static List<Reservation> selectUnapproved() {
        String statement = "SELECT * FROM reservations WHERE approved = f";
        return dao.executeQuery(statement, null);
    }

    public static List<Reservation> selectApproved() {
        String statement = "SELECT * FROM reservations WHERE approved = t";
        return dao.executeQuery(statement, null);
    }

    public static Reservation find(String id) {
        String statement = "SELECT * FROM reservations WHERE id = " + id;
        return dao.findByKey(statement, null);
    }

    @Override
    protected Function<ResultSet, List<Reservation>> mapToObject() {
        return (rs) -> {
            List<Reservation> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Reservation(rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getDate(5),
                            rs.getDate(6),
                            rs.getBoolean(7)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
