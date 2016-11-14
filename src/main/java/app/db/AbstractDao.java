package app.db;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractDao<T> {

    private static final String LINK = "jdbc:postgresql://localhost/hotel";
    private static final String LOGIN = "hotel_adm";
    private static final String PASSWORD = "Zz20164209";

    public void executeUpdate(String statement, DBParams params) {
        try (
                Connection con = createConnection();
                PreparedStatement pst = con.prepareStatement(statement)
        ) {
            for (int i = 1; i <= params.size(); i++) {
                pst.setString(i, params.get(i - 1));
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> executeQuery(String statement, DBParams params) {
        List<T> toReturn = null;
        try (
                Connection con = createConnection();
                PreparedStatement pst = con.prepareStatement(statement)
        ) {

            if (params != null) {
                for (int i = 1; i <= params.size(); i++) {
                    pst.setString(i, params.get(i - 1));
                }
            }

            ResultSet rst = pst.executeQuery();
            toReturn = mapToObject().apply(rst);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn;
    }
//
//    public List<T> selectAll() {
//        String statement = "SELECT * FROM hotels";
//        return executeQuery(statement, new DBParams());
//    }

    protected abstract Function<ResultSet, List<T>> mapToObject();
//
//    private String className() {
//        return
//    }

    private static Connection createConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(LINK, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

}
