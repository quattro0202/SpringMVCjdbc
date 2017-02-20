package Spring.Models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
@Table(name = "suppliers")
public class Supplier {
/*
    @Size(min=3, max=25)
    @NotNull
    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    @DateTimeFormat(style="S-")
    @Future
    @Past
    @Email
    @Min(value = "10")
    @Max(value = "10")
*/

    private int id;

    @NotNull(message = "Поле не має бути порожнім.")
    @Size(min = 3, max = 30, message = "Поле повинно містити більше 3 і менше 30 символів.")
    private String name;

    @NotNull(message = "Поле не має бути порожнім.")
    @Size(min = 3, max = 50, message = "Поле повинно містити більше 3 і менше 50 символів.")
    private String address;

    @NotNull(message = "Поле не має бути порожнім.")
    @Size(min = 5, max = 15, message = "Поле повинно містити більше 5 і менше 15 символів.")
    private String phone;

    @NotNull(message = "Поле не має бути порожнім.")
    @Email(message = "Рядок має відповідати формату електронної адреси.")
    private String email;

    public Supplier(){

    }

    public Supplier(int id, String name, String address, String phone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    @Id
    @Column(name = "supplier_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "supplier_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "supplier_address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "supplier_phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Supplier(int id) {
        this.id = id;
    }

    @Column(name = "supplier_email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Supplier(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

}
