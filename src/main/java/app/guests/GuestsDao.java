package app.guests;

import app.db.AbstractDao;
import app.db.DBParams;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class GuestsDao extends AbstractDao<Guest> {

    private static final GuestsDao dao = new GuestsDao();

    public static void insert(String mail, String name, String lastName) {
        String stm = "INSERT INTO Guests(mail, name, lastName) VALUES(?, ?, ?)";
        DBParams params = new DBParams(mail, name, lastName);
        dao.executeUpdate(stm, params);
    }

    public static List<Guest> selectAll() {
        String statement = "SELECT * FROM guests";
        return dao.executeQuery(statement, null);
    }

    public static Guest find(String mail) {
        String statement = String.format("SELECT * FROM guests WHERE mail = '%s'", mail);
        return dao.findByKey(statement, null);
    }

    @Override
    protected Function<ResultSet, List<Guest>> mapToObject() {
        return (rs) -> {
            List<Guest> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Guest(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }
}
