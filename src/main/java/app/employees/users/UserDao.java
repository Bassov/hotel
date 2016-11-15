package app.employees.users;

import app.db.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UserDao extends AbstractDao<User> {

    private static final UserDao dao = new UserDao();

    public static User findByEmpId(String emp_id) {
        String statement = "SELECT * FROM users WHERE emp_id = " + emp_id;
        return dao.findByKey(statement, null);
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
