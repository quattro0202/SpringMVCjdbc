package Spring.Models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Олександр on 21.05.2016.
 */

public class Employee {

    private Integer id;

    @NotNull(message = "Поле не має бути порожнім.")
    @Size(min = 3, max = 30, message = "Поле повинно містити більше 3 і менше 30 символів.")
    private String name;


    private Employee manager;

    public Employee() {

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