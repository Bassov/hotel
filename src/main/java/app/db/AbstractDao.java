package app.db;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractDao<T> {

    private static final String LINK = "jdbc:postgresql://localhost/hotel";
    private static final String LOGIN = "hotel_adm";
    private static final String PASSWORD = "Zz20164209";

    public int executeUpdate(String statement, DBParams params) {
        try (
                Connection con = createConnection();
                PreparedStatement pst = con.prepareStatement(statement);
        ) {
            if (params != null) {
                for (int i = 1; i <= params.size(); i++) {
                    pst.setString(i, params.get(i - 1));
                }
            }

            if (statement.toUpperCase().contains("RETURNING")) {
                ResultSet rst = pst.executeQuery();
                rst.next();
                return rst.getInt(1);
            }
            System.out.println(statement);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public T findByKey(String statement, DBParams primaryKey) {
        List<T> entities = executeQuery(statement, primaryKey);
        if (entities.isEmpty()) {
            return null;
        } else {
            return entities.get(0);
        }
    }

    protected abstract Function<ResultSet, List<T>> mapToObject();

    protected static Connection createConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(LINK, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

}
