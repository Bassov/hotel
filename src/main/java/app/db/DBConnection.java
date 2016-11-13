package app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    public Connection con;
    public PreparedStatement pst;

    public DBConnection(String url, String user, String password){
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void insertHotel(String city, String address) throws SQLException {
        String stm = "INSERT INTO Hotels(city, address) VALUES(?, ?)";
        pst = con.prepareStatement(stm);
        pst.setString(1, city);
        pst.setString(2, address);
        pst.executeUpdate();
    }

    public void insertEKey() throws SQLException {
        String stm = "INSERT INTO EKeys(id) VALUES (DEFAULT);";
        pst = con.prepareStatement(stm);
        pst.executeUpdate();
    }

    public void insertEmployee(String name, String lastname, int keyID) throws SQLException {
        String stm = "INSERT INTO Employees(name, surename, key_id) VALUES(?, ?, ?)";
        pst = con.prepareStatement(stm);
        pst.setString(1, name);
        pst.setString(2, lastname);
        pst.setInt(3, keyID);
        pst.executeUpdate();
    }

    public void insertUser(int empID, String login, String password) throws SQLException {
        String stm = "INSERT INTO Users(emp_id, login, password) VALUES(?, ?, ?)";
        pst = con.prepareStatement(stm);
        pst.setInt(1, empID);
        pst.setString(2, login);
        pst.setString(3, password);
        pst.executeUpdate();
    }

    public void insertOwner(String login) throws SQLException {
        String stm = "INSERT INTO Owners(user_login) VALUES(?)";
        pst = con.prepareStatement(stm);
        pst.setString(1, login);
        pst.executeUpdate();
    }

    public void insertAdministrator(String login, int hotelID) throws SQLException {
        String stm = "INSERT INTO Administrators(user_login, hotel_id) VALUES(?, ?)";
        pst = con.prepareStatement(stm);
        pst.setString(1, login);
        pst.setInt(2, hotelID);
        pst.executeUpdate();
    }

    public void insertStaff(int empID, int hotelID) throws SQLException {
        String stm = "INSERT INTO Staff(emp_id, hotel_id) VALUES(?, ?)";
        pst = con.prepareStatement(stm);
        pst.setInt(1, empID);
        pst.setInt(2, hotelID);
        pst.executeUpdate();
    }
}
