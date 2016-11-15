package app.employees.users;

import app.db.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UserDao extends AbstractDao<User> {

    private static final UserDao dao = new UserDao();

    public static void insert(String emp_id, String login, String password) {
        String statement = String.format("INSERT INTO users (emp_id, login, password) VALUES ('%s', '%s', '%s')",
                emp_id, login, password);
        dao.executeUpdate(statement, null);
    }

    public static User findByEmpId(String emp_id) {
        String statement = "SELECT * FROM users WHERE emp_id = " + emp_id;
        return dao.findByKey(statement, null);
    }

    public static User findByLogin(String login) {
        String statement = String.format("SELECT * FROM users WHERE login = '%s'", login);
        return dao.findByKey(statement, null);
    }

    public static boolean loginExist(String login) {
        User u = findByLogin(login);
        return u != null;
    }

    @Override
    protected Function<ResultSet,List<User>> mapToObject() {
        return (rs) -> {
            List<User> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
