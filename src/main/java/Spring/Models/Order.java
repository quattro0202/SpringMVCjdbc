package Spring.Models;


import java.sql.Date;


public class Order {

    private Integer id;
    private Supplier supplier;
    private Employee employee;
    private Date date;

    public Order() {

    }

    public Integer getId() {
        return id;
    }

    public Order(Integer id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
