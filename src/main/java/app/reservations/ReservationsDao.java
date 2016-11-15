package app.reservations;

import app.db.AbstractDao;
import app.db.DBParams;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                                                "end_date, key_id, approved) VALUES(?, "+room_number+","+
                                                hotel_id+","+st_date.toString()+","+end_date.toString()+
                                                ","+approved+")";
        DBParams params = new DBParams(guest_mail);
        dao.executeUpdate(stm, params);
    }

    public static List<Reservation> selectAll() {
        String statement = "SELECT * FROM reservations";
        return dao.executeQuery(statement, null);
    }

    public static List<Reservation> selectUnapproved() {
        String statement = "SELECT * FROM reservations WHERE approved = false";
        return dao.executeQuery(statement, null);
    }

    public static List<Reservation> selectApproved() {
        String statement = "SELECT * FROM reservations WHERE approved = true";
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
                                               rs.getInt(7),
                                               rs.getBoolean(8)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
