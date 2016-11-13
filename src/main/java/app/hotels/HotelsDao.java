package app.hotels;

import app.db.DBConnection;
import app.db.DBParams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelsDao {

    public static void insert(String city, String address) {
        String stm = "INSERT INTO Hotels(city, address) VALUES(?, ?)";
        DBParams params = new DBParams(city, address);
        DBConnection.executeUpdate(stm, params);
    }

    public static List<Hotel> selectAll() {
        String statement = "SELECT * FROM hotels";
        List<Hotel> hotels = new ArrayList<>();
        try (
                Connection con = DBConnection.createConnection();
                PreparedStatement stm = con.prepareStatement(statement);
                ResultSet rs = stm.executeQuery()
        ) {
            while (rs.next()) {
                hotels.add(new Hotel(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }



}
