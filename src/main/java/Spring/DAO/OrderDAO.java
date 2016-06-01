package Spring.DAO;

import Spring.Models.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Олександр on 30.04.2016.
 */
public interface OrderDAO {

    public void addOrder(Order order) throws Exception;
    public void deleteOrder(int id) throws Exception;
    public Order getOrder(int id) throws Exception;
    public List<Order> getOrders() throws Exception;
    public List<Order> getOrders(int limit, int offset) throws Exception;
    public List<Order> getOrders(Order order) throws Exception;
    public void editOrder(Order order) throws SQLException;
    public int countOrders() throws SQLException;

}
