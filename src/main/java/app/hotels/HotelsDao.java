package app.hotels;

import app.db.AbstractDao;
import app.db.DBParams;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HotelsDao extends AbstractDao<Hotel> {

    private static final HotelsDao dao = new HotelsDao();

    public static void insert(String city, String address) {
        String stm = "INSERT INTO Hotels(city, address) VALUES(?, ?)";
        DBParams params = new DBParams(city, address);
        dao.executeUpdate(stm, params);
    }

    public static List<Hotel> selectAll() {
        String statement = "SELECT * FROM hotels";
        return dao.executeQuery(statement, null);
    }

    public static Hotel find(String id) {
        String statement = "SELECT * FROM hotels WHERE id = " + id;
        return dao.findByKey(statement, null);
    }

    @Override
    protected Function<ResultSet, List<Hotel>> mapToObject() {
        return (rs) -> {
            List<Hotel> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Hotel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
