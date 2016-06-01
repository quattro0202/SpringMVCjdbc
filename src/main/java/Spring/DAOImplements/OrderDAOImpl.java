package Spring.DAOImplements;

import Spring.DAO.OrderDAO;
import Spring.Models.Employee;
import Spring.Models.Order;
import Spring.Models.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олександр on 29.05.2016.
 */
public class OrderDAOImpl implements OrderDAO{

    private DataSource dataSource;

    @Override
    public int countOrders() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS num FROM orders";
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

    @Override
    public void editOrder(Order order) throws SQLException {

    }

    @Override
    public List<Order> getOrders(Order order) throws Exception {
        return null;
    }

    @Override
    public List<Order> getOrders(int limit, int offset) throws Exception {
        List<Order> orders = null;
        String sql = "SELECT * FROM orders " +
                "join suppliers on orders.supp_id = suppliers.supplier_id " +
                "join employees on orders.empl_id = employees.employee_id " +
                "limit " + limit + " offset " + offset;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();){

            orders = new ArrayList<>();
            while(resultSet.next()){
                Order order = new Order();
                Supplier supplier = new Supplier();
                Employee employee = new Employee();

                supplier.setId(resultSet.getInt("supplier_id"));
                supplier.setName(resultSet.getString("supplier_name"));
                supplier.setAddress(resultSet.getString("supplier_address"));
                supplier.setPhone(resultSet.getString("supplier_phone"));
                supplier.setEmail(resultSet.getString("supplier_email"));

                employee.setId(resultSet.getInt("employee_id"));
                employee.setName(resultSet.getString("employee_name"));
                employee.setManager(new Employee(resultSet.getInt("employee_manager")));

                order.setId(resultSet.getInt("order_id"));
                order.setSupplier(supplier);
                order.setEmployee(employee);
                order.setDate(resultSet.getDate("order_date"));

                orders.add(order);
            }

        }catch(Exception e){
            throw e;
        }
        return orders;
    }

    @Override
    public List<Order> getOrders() throws Exception {
        return null;
    }

    @Override
    public Order getOrder(int id) throws Exception {
        return null;

    }

    @Override
    public void deleteOrder(int id) throws Exception {

    }

    @Override
    public void addOrder(Order order) throws Exception {

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
