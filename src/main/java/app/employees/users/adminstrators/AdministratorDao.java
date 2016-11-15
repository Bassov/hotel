package app.employees.users.adminstrators;

import app.db.AbstractDao;
import app.employees.users.User;
import app.employees.users.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AdministratorDao extends AbstractDao<Administrator> {

    private static final AdministratorDao dao = new AdministratorDao();

    public static Administrator findByLogin(String login) {
        String statement = "SELECT * FROM administrators WHERE user_login = " + login;
        return dao.findByKey(statement, null);
    }

    public static Administrator findByEmpId(String empId) {
        User u = UserDao.findByEmpId(empId);
        return u == null ? null : findByLogin(u.getLogin());
    }

    @Override
    protected Function<ResultSet,List<Administrator>> mapToObject() {
        return (rs) -> {
            List<Administrator> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Administrator(rs.getString(1), rs.getInt(2)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
