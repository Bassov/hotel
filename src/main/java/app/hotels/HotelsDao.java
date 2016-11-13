package app.hotels;

import app.db.DBConnection;
import app.db.DBParams;

public class HotelsDao {

    public static void insert(String city, String address) {
        String stm = "INSERT INTO Hotels(city, address) VALUES(?, ?)";
        DBParams params = new DBParams(city, address);
        DBConnection.executeUpdate(stm, params);
    }

}
