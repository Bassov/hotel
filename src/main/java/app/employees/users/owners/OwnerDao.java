package app.employees.users.owners;

import app.db.AbstractDao;
import app.employees.users.User;
import app.employees.users.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OwnerDao extends AbstractDao<Owner> {

    private static final OwnerDao dao = new OwnerDao();

    public static Owner findByLogin(String login) {
        String statement = "SELECT * FROM owners WHERE user_login = " + login;
        return dao.findByKey(statement, null);
    }

    public static Owner findByEmpId(String empId) {
        User u = UserDao.findByEmpId(empId);
        return u == null ? null : findByLogin(u.getLogin());
    }

    @Override
    protected Function<ResultSet,List<Owner>> mapToObject() {
        return (rs) -> {
            List<Owner> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Owner(rs.getString(1), rs.getInt(2)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}

