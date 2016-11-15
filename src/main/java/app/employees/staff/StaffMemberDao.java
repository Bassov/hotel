package app.employees.staff;

import app.db.AbstractDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StaffMemberDao extends AbstractDao<StaffMember> {

    private static final StaffMemberDao dao = new StaffMemberDao();

    public static StaffMember findByEmpId(String id) {
        String statement = "SELECT * FROM staff WHERE emp_id = " + id;
        return dao.findByKey(statement, null);
    }

    @Override
    protected Function<ResultSet,List<StaffMember>> mapToObject() {
        return (rs) -> {
            List<StaffMember> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new StaffMember(rs.getInt(1), rs.getInt(2)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
