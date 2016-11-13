package app.db;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private Connection con;
    private PreparedStatement pst;

    private DBConnection(String url, String user, String password){
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DBConnection.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void executeUpdate(String statement, DBParams params) {
        Connection con = createConnection();
        try (PreparedStatement pst = con.prepareStatement(statement)) {
            for (int i = 1; i <= params.size(); i++) {
                pst.setString(i, params.get(i - 1));
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() {
        Connection con = null;
        try {
             con = DriverManager.getConnection("jdbc:postgresql://localhost/hotel",
                    "hotel_adm", "Zz20164209");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
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

    public void insertGuest(String mail, String name, String surename, String phone) throws SQLException {
        String stm = "INSERT INTO Guests(mail, name, surename, phone) VALUES(?, ?, ?, ?)";
        pst = con.prepareStatement(stm);
        pst.setString(1, mail);
        pst.setString(2, name);
        pst.setString(3, surename);
        pst.setString(4, phone);
        pst.executeUpdate();
    }

    public void insertRoom(int hotel_id, int number, int capacity) throws SQLException {
        String stm = "INSERT INTO Rooms(hotel_id, number, capacity) VALUES(?, ?, ?)";
        pst = con.prepareStatement(stm);
        pst.setInt(1, hotel_id);
        pst.setInt(2, number);
        pst.setInt(3, capacity);
        pst.executeUpdate();
    }

    public void insertReservation(String guest_mail, int room_number, int hotel_id,
                                  Timestamp st_date, Timestamp end_date, int key_id)
                                    throws SQLException {
        String stm = "INSERT INTO Reservations(guest_mail, room_number," +
                     " hotel_id, st_date, end_date, key_id) VALUES(?, ?, ?, ?, ?,?)";
        pst = con.prepareStatement(stm);
        pst.setString(1, guest_mail);
        pst.setInt(2, room_number);
        pst.setInt(3, hotel_id);
        pst.setTimestamp(4, st_date);
        pst.setTimestamp(5, end_date);
        pst.setInt(6, key_id);
        pst.executeUpdate();
    }

    public void insertRoomKey(int hotel_id, int room_number, int key_id) throws SQLException {
        String stm = "INSERT INTO RoomsKeys(hotel_id, room_number, key_id) VALUES(?,?,?)";
        pst = con.prepareStatement(stm);
        pst.setInt(1, hotel_id);
        pst.setInt(2, room_number);
        pst.setInt(3, key_id);
    }

}
