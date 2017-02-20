package Spring.Models;



public class Employee {

    private Integer id;

    //@NotNull(message = "Поле не має бути порожнім.")
    //@Size(min = 3, max = 30, message = "Поле повинно містити більше 3 і менше 30 символів.")
    private String name;


    private Employee manager;

    public Employee() {

    }

    public Employee(Integer id, String name, Employee manager) {
        this.id = id;
        this.name = name;
        this.manager = manager;
    }

    public Employee(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}