package app.guests;

import app.db.AbstractDao;
import app.db.DBParams;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class GuestsDao extends AbstractDao<Guest>{

    private static final GuestsDao dao = new GuestsDao();

    public static void insert(String mail, String name, String surename, String phone) {
        String stm = "INSERT INTO Guests(mail, name, surename, phone) VALUES(?, ?, ?, ?)";
        DBParams params = new DBParams(mail, name, surename, phone);
        dao.executeUpdate(stm, params);
    }

    public static List<Guest> selectAll() {
        String statement = "SELECT * FROM guests";
        return dao.executeQuery(statement, null);
    }

    @Override
    protected Function<ResultSet, List<Guest>> mapToObject() {
        return (rs) -> {
            List<Guest> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Guest(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }
}
