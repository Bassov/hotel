package app.hotels;

import app.db.DBConnection;
import app.db.DBParams;

import java.sql.SQLException;

public class HotelsDao {

    public static void insert(String city, String address) throws SQLException {
        String stm = "INSERT INTO Hotels(city, address) VALUES(?, ?)";
        DBParams params = new DBParams(city, address);
        DBConnection.execute(stm, params);
    }

}
