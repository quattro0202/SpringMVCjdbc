package Spring.DAOImplements;

import Spring.DAO.EmployeeDAO;
import Spring.Models.Employee;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олександр on 25.05.2016.
 */
public class EmployeeDAOImpl implements EmployeeDAO{

    private DataSource dataSource;



    @Override
    public void addEmployee(Employee employee) throws SQLException{

        Connection connection = null;
        String manager1;
        String manager2;
        if(employee.getManager().getId() == 0) {
            manager1 = "";
            manager2 = "";
        }else{
            manager1 = ", employee_manager";
            manager2 = ", " + employee.getManager();
        }

        String sql = "INSERT INTO employees (employee_name" + manager1 + ") " +
                "VALUES (\"" + employee.getName() +"\""+ manager2 + ")";
        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw e;

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new SQLException(e);
                }
            }
        }
    }

    @Override
    public void deleteEmployee(int id) throws SQLException{
        String sql = "delete from employees where employee_id=" + id;
        String sql2 = "update employees set employee_manager = null where employee_manager = " + id;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement2 = connection.prepareStatement(sql2);

            preparedStatement2.executeUpdate();
            preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                preparedStatement2.close();
                preparedStatement.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public Employee getEmployee(int id) throws SQLException{
        String sql = "SELECT * FROM employees WHERE employee_id=" + id;

        Employee employee = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();) {
            if(resultSet.next()){
                employee = new Employee();
                employee.setId(resultSet.getInt("employee_id"));
                employee.setName(resultSet.getString("employee_name"));
                employee.setManager(new Employee(resultSet.getInt("employee_manager")));
            }
        }catch (Exception e){
            throw e;
        }
        return employee;
    }

    @Override
    public List<Employee> getEmployees() throws SQLException{
        return null;
    }

    @Override
    public List<Employee> getEmployees(int limit, int offset) throws SQLException{
        List<Employee> employees = null;
        String sql = "SELECT * FROM employees LIMIT " + limit + " OFFSET " + offset;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();){

            employees = new ArrayList<>();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("employee_id"));
                employee.setName(resultSet.getString("employee_name"));
                employee.setManager(new Employee(resultSet.getInt("employee_manager")));
                employees.add(employee);
            }
        }catch(Exception e){
            throw e;
        }
        return employees;
    }

    @Override
    public List<Employee> getEmployees(Employee employee) throws SQLException{
        List<Employee> employees = null;

        String employee_manager = "";
        if(employee.getManager().getId() == 0)
            employee_manager = "employee_manager is NULL";
        else
            employee_manager = "employee_manager = " + employee.getManager();

        String employee_name;
        if(employee.getName().equals(""))
            employee_name = "\"\"";
        else
            employee_name = "\"%" + employee.getName() + "%\"";

        String sql = "select * from employees where " +
                "employee_id = " + employee.getId() +
                " or employee_name like " + employee_name +
                " or " + employee_manager;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            employees = new ArrayList<>();
            while(resultSet.next()){
                Employee resultEmployee = new Employee();
                resultEmployee.setId(resultSet.getInt("employee_id"));
                resultEmployee.setName(resultSet.getString("employee_name"));
                resultEmployee.setManager(new Employee(resultSet.getInt("employee_manager")));
                employees.add(resultEmployee);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (CommunicationsException communicationsException){
            throw communicationsException;
        }catch(Exception e){
            throw e;
        }finally {
            try{
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }catch (Throwable t){
                t.printStackTrace();
            }
        }
        return employees;

    }

    @Override
    public void editEmployee(Employee employee) throws SQLException {
        String employee_manager = "";
        if(employee.getManager().getId() == 0)
            employee_manager = "NULL";
        else
            employee_manager = String.valueOf(employee.getManager());

        String sql = "update employees set employee_id = " + employee.getId() +
                ", employee_name = \"" + employee.getName() +
                "\", employee_manager = " + employee_manager + " where employee_id = " + employee.getId();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            preparedStatement.executeUpdate();

        }catch(Exception e){
            throw e;
        }
    }

    @Override
    public int countEmployees() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS num FROM employees";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();){


            if(resultSet.next()) {
                count = resultSet.getInt("num");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return count;
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
