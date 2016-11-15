package app.employees;

import app.db.AbstractDao;
import app.db.DBParams;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EmployeeDao extends AbstractDao<Employee> {

    private static final EmployeeDao dao = new EmployeeDao();

    public static void insert(String name, String surname) {
        String stm = "INSERT INTO employees(name, surname) VALUES(?, ?)";
        DBParams params = new DBParams(name, surname);
        dao.executeUpdate(stm, params);
    }

    public static List<Employee> selectAll() {
        String statement = "SELECT * FROM employees";
        return dao.executeQuery(statement, null);
    }

    public static Employee find(String id) {
        String statement = "SELECT * FROM employees WHERE id = ?";
        DBParams params = new DBParams(id);
        return dao.findByKey(statement, params);
    }

    public static boolean isStaff(String empId) {
        String statement = "SELECT * FROM staff WHERE emp_id = ?";
        DBParams params = new DBParams(empId);
        return !dao.executeQuery(statement, params).isEmpty();
    }

    public static boolean isAdministrator(String empId) {
        String statement = "SELECT * FROM users WHERE emp_id = ?";
        DBParams params = new DBParams(empId);
        return !dao.executeQuery(statement, params).isEmpty();
    }

    @Override
    protected Function<ResultSet,List<Employee>> mapToObject() {
        return (rs) -> {
            List<Employee> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        };
    }

}
