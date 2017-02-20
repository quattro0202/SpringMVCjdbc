package Spring.DAO;

import Spring.Models.Supplier;

import java.sql.SQLException;
import java.util.List;


public interface SupplierDAO {

    public void addSupplier(Supplier supplier) throws Exception;
    public void deleteSupplier(int id) throws Exception;
    public Supplier getSupplier(int id) throws Exception;
    public List<Supplier> getSuppliers() throws Exception;
    public List<Supplier> getSuppliers(int limit, int offset) throws Exception;
    public List<Supplier> getSuppliers(Supplier supplier) throws Exception;
    public void editSupplier(Supplier supplier) throws SQLException;
    public int countSuppliers() throws SQLException;
}
