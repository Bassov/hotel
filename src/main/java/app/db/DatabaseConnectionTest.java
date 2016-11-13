package app.db;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnectionTest {
    public static void main(String[] argv) {
        System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/hotel", "hotel_adm","Zz20164209");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
            DBConnection db = new DBConnection("jdbc:postgresql://localhost/hotel",
                                               "hotel_adm", "Zz20164209");
            try {
                db.insertHotel("Innopolis", "Universitetskaya 1, 4");
                db.insertEKey();
                db.insertEmployee("Nikita", "Bogomazov", 1);
                db.insertUser(1, "admin", "admin");
                db.insertOwner("admin");
                db.insertAdministrator("admin", 1);
                db.insertStaff(1,1);
                db.insertGuest("innopolis@mail.ru", "Alexandr", "Basov", "89132983322");
                db.insertRoom(1, 209, 5);
                long millis = Date.valueOf(LocalDate.now()).getTime();
                db.insertReservation("innopolis@mail.ru", 209, 1, new Timestamp(millis), new Timestamp(millis + 1), 1);
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DatabaseConnectionTest.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            System.out.println("Failed to make connection!");
        }
    }
}
