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


public class EmployeeDAOImpl implements EmployeeDAO{

    private DataSource dataSource;



    @Override
    public void addEmployee(Employee employee) throws SQLException{

        Connection connection = null;
        String manager;
        String managerValue;

        if(employee.getManager().getId() == 0) {
            manager = "";
            managerValue = "";
        }else{
            manager = ", employee_manager";
            managerValue = ", " + employee.getManager().getId();
        }

        String sql = "INSERT INTO employees (employee_name" + manager + ") " +
                "VALUES (\"" + employee.getName() +"\""+ managerValue + ")";
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

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ) {

            preparedStatement.executeUpdate();
        }catch (Exception e){
            throw e;
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
        String sql = "select t1.employee_id as _id, t1.employee_name as _name, " +
                "t1.employee_manager as _manager_id, t2.employee_name as _manager_name, " +
                "t2.employee_manager as _manager_manager_id " +
                "from employees t1 " +
                "left join employees t2 on t1.employee_manager=t2.employee_id " +
                "LIMIT " + limit + " OFFSET " + offset;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();){

            employees = new ArrayList<>();
            while(resultSet.next()){
                Employee employee = new Employee();
                Employee manager = new Employee();

                manager.setId(resultSet.getInt("_manager_id"));
                manager.setName(resultSet.getString("_manager_name"));
                manager.setManager(new Employee(resultSet.getInt("_manager_manager_id")));

                employee.setId(resultSet.getInt("_id"));
                employee.setName(resultSet.getString("_name"));



                employee.setManager(manager);


                employees.add(employee);
            }
        }catch(Exception e){
            throw e;
        }
        return employees;
    }

    @Override
    public List<Employee> findEmployees(Employee employee) throws SQLException{
        List<Employee> employees = null;

        String employee_manager = "";
        if(employee.getManager().getId() == 0)
            employee_manager = "t1.employee_manager is NULL";
        else
            employee_manager = "t1.employee_manager = " + employee.getManager().getId();

        String employee_name;
        if(employee.getName().equals(""))
            employee_name = "\"\"";
        else
            employee_name = "\"%" + employee.getName() + "%\"";

        String sql =
                "select t1.employee_id as _id, t1.employee_name as _name, " +
                    "t1.employee_manager as _manager_id, t2.employee_name as _manager_name, " +
                    "t2.employee_manager as _manager_manager_id " +
                "from employees t1 " +
                "left join employees t2 on t1.employee_manager=t2.employee_id " +
                "where t1.employee_id = " + employee.getId() +
                    " or t1.employee_name like " + employee_name +
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
                Employee manager = new Employee();

                manager.setId(resultSet.getInt("_manager_id"));
                manager.setName(resultSet.getString("_manager_name"));
                manager.setManager(new Employee(resultSet.getInt("_manager_manager_id")));

                resultEmployee.setId(resultSet.getInt("_id"));
                resultEmployee.setName(resultSet.getString("_name"));
                resultEmployee.setManager(manager);

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
            employee_manager = String.valueOf(employee.getManager().getId());

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
