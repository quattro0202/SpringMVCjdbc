package Spring.DAOImplements;

import Spring.DAO.SupplierDAO;
import Spring.Models.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





public class SupplierDAOImpl implements SupplierDAO {

    private DataSource dataSource;



    @Override
    public void addSupplier(Supplier supplier) throws Exception {
        String sql = "INSERT INTO suppliers (supplier_name, supplier_address, supplier_phone, supplier_email)" +
                "VALUES (?, ?, ?, ?)";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getAddress());
            preparedStatement.setString(3, supplier.getPhone());
            preparedStatement.setString(4, supplier.getEmail());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new SQLException(e);

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
    public void deleteSupplier(int id) throws SQLException {
        String sql = "delete from suppliers where supplier_id=?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();

        }catch(Exception e){
            throw e;
        }
    }

    @Override
    public Supplier getSupplier(int id) throws Exception {
        String sql = "SELECT * FROM suppliers WHERE supplier_id=" + id;
        Supplier supplier = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();) {
            if(resultSet.next()){
                supplier = new Supplier();
                supplier.setId(resultSet.getInt("supplier_id"));
                supplier.setName(resultSet.getString("supplier_name"));
                supplier.setAddress(resultSet.getString("supplier_address"));
                supplier.setPhone(resultSet.getString("supplier_phone"));
                supplier.setEmail(resultSet.getString("supplier_email"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return supplier;
    }




    @Override
    public List<Supplier> getSuppliers() throws Exception {
        List<Supplier> suppliers = null;
        String sql = "SELECT * FROM suppliers";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();){

            suppliers = new ArrayList<>();
            while(resultSet.next()){
                Supplier supplier = new Supplier();
                supplier.setId(resultSet.getInt("supplier_id"));
                supplier.setName(resultSet.getString("supplier_name"));
                supplier.setAddress(resultSet.getString("supplier_address"));
                supplier.setPhone(resultSet.getString("supplier_phone"));
                supplier.setEmail(resultSet.getString("supplier_email"));
                suppliers.add(supplier);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public void editSupplier(Supplier supplier) throws SQLException {
        String sql = "update suppliers set supplier_id = ?," +
                "supplier_name = ?," +
                "supplier_address = ?," +
                "supplier_phone = ?," +
                "supplier_email = ?  where supplier_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            preparedStatement.setString(1, String.valueOf(supplier.getId()));
            preparedStatement.setString(2, supplier.getName());
            preparedStatement.setString(3, supplier.getAddress());
            preparedStatement.setString(4, supplier.getPhone());
            preparedStatement.setString(5, supplier.getEmail());
            preparedStatement.setString(6, String.valueOf(supplier.getId()));
            preparedStatement.executeUpdate();

        }catch(Exception e){
            throw e;
        }
    }

    @Override
    public List<Supplier> getSuppliers(int limit, int offset) throws Exception {
        List<Supplier> suppliers = null;
        String sql = "SELECT * FROM suppliers LIMIT " + limit + " OFFSET " + offset;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();){

            suppliers = new ArrayList<>();
            while(resultSet.next()){
                Supplier supplier = new Supplier();
                supplier.setId(resultSet.getInt("supplier_id"));
                supplier.setName(resultSet.getString("supplier_name"));
                supplier.setAddress(resultSet.getString("supplier_address"));
                supplier.setPhone(resultSet.getString("supplier_phone"));
                supplier.setEmail(resultSet.getString("supplier_email"));
                suppliers.add(supplier);
            }
        }catch(Exception e){
            throw e;
        }
        return suppliers;

    }

    @Override
    public List<Supplier> getSuppliers(Supplier supplier) throws Exception {
        List<Supplier> suppliers = null;
        String sql = "select * from suppliers where " +
                "supplier_id = ? or " +
                "supplier_name like ? or " +
                "supplier_address like ? or " +
                "supplier_phone like ? or " +
                "supplier_email like ?";


        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);){

            String str2; if(supplier.getName().equals(""))str2 = ""; else str2 = "%" + supplier.getName() + "%";
            String str3; if(supplier.getAddress().equals(""))str3 = ""; else str3 = "%" + supplier.getAddress() + "%";
            String str4; if(supplier.getPhone().equals(""))str4 = ""; else str4 = "%" + supplier.getPhone() + "%";
            String str5; if(supplier.getEmail().equals(""))str5 = ""; else str5 = "%" + supplier.getEmail() + "%";

            preparedStatement.setString(1, String.valueOf(supplier.getId()));
            preparedStatement.setString(2, str2);
            preparedStatement.setString(3, str3);
            preparedStatement.setString(4, str4);
            preparedStatement.setString(5, str5);

            ResultSet resultSet = preparedStatement.executeQuery();
            suppliers = new ArrayList<>();
            while(resultSet.next()){
                Supplier resultSupplier = new Supplier();
                resultSupplier.setId(resultSet.getInt("supplier_id"));
                resultSupplier.setName(resultSet.getString("supplier_name"));
                resultSupplier.setAddress(resultSet.getString("supplier_address"));
                resultSupplier.setPhone(resultSet.getString("supplier_phone"));
                resultSupplier.setEmail(resultSet.getString("supplier_email"));
                suppliers.add(resultSupplier);
            }

            resultSet.close();
        }catch(Exception e){
            throw e;
        }
        return suppliers;
    }


    @Override
    public int countSuppliers() throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS num FROM suppliers";
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
