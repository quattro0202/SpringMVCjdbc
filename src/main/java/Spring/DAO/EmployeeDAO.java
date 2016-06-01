package Spring.DAO;

import Spring.Models.Employee;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Олександр on 30.04.2016.
 */
public interface EmployeeDAO {

    public void addEmployee(Employee employee) throws SQLException;
    public void deleteEmployee(int id) throws SQLException;
    public Employee getEmployee(int id) throws SQLException;
    public List<Employee> getEmployees() throws SQLException;
    public List<Employee> getEmployees(int limit, int offset) throws SQLException;
    public List<Employee> getEmployees(Employee employee) throws SQLException;
    public void editEmployee(Employee employee) throws SQLException;
    public int countEmployees() throws SQLException;

}
