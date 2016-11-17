package app.employees.users.managers;

import app.db.AbstractDao;
import app.employees.users.User;
import app.employees.users.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ManagerDao extends AbstractDao<Manager> {

    private static final ManagerDao dao = new ManagerDao();

    public static void insert(String user_login) {
        String statement = String.format("INSERT INTO managers (user_login) VALUES ('%s')", user_login);
        dao.executeUpdate(statement, null);
    }

    public static Manager findByLogin(String login) {
        String statement = String.format("SELECT * FROM managers WHERE user_login = '%s'", login);
        return dao.findByKey(statement, null);
    }

    public static Manager findByEmpId(String empId) {
        User u = UserDao.findByEmpId(empId);
        return u == null ? null : findByLogin(u.getLogin());
    }

    @Override
    protected Function<ResultSet,List<Manager>> mapToObject() {
        return (rs) -> {
            List<Manager> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Manager(rs.getString(1)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}

