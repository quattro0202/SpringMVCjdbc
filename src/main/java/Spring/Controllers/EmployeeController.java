package Spring.Controllers;

import Spring.DAO.EmployeeDAO;
import Spring.Models.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Олександр on 25.05.2016.
 */

@Controller
public class EmployeeController {

    @RequestMapping(value = "/employees/delete/{idEmployee}", method = RequestMethod.GET)
    public ModelAndView deleteSupplier(@PathVariable("idEmployee") int id) throws SQLDataException {
        ModelAndView modelAndView = new ModelAndView();
        EmployeeDAO employeeDAO = getEmployeeDAO();
        Employee employee = null;
        modelAndView.setViewName("blank");
        modelAndView.addObject("title", "Співробітники");
        try{
            employee = employeeDAO.getEmployee(id);
            if(employee != null){
                employeeDAO.deleteEmployee(id);
                modelAndView.addObject("successMessage", "Співробітника з id = " + id + " успішно видалено.");
                return modelAndView;
            }else{
                modelAndView.addObject("failMessage", "Співробітника з id = " + id + " немає.");
                return modelAndView;
            }
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }
        return modelAndView;
    }


    @RequestMapping(value = "/employees/list", method = RequestMethod.GET)
    public String employeesListGet(){
        return "redirect:/employees/list/1";
    }


    @RequestMapping(value = "/employees/list/{pageNumber}", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView employeesListPost(@PathVariable("pageNumber") int pageNumber){
        ModelAndView modelAndView = new ModelAndView();
        final int limit = 10;
        final int offset = (pageNumber - 1) * 10;
        int count = 0;


        List<Employee> employees = null;
        EmployeeDAO employeeDAO = getEmployeeDAO();

        try{
            count = employeeDAO.countEmployees();
            employees = employeeDAO.getEmployees(limit, offset);
        }catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
            modelAndView.setViewName("employeesList");
            return modelAndView;
        }

        if(count % limit == 0){
            count = count / limit;
        }else{
            count = count / limit + 1;
        }

        int[] arr;


        if(count > 10){
            arr = new int[10];
            if(pageNumber < 5){
                for (int i = 1; i < 11; i++) {
                    arr[i-1] = i;
                }
            }else{
                if(count - pageNumber >= 5){
                    for (int i = pageNumber - 4, j = 0; i < pageNumber + 6; i++, j++) {
                        arr[j] = i;
                    }
                }else{
                    for (int i = 0; i < 10; i++) {
                        arr[i] = count - (9 - i);
                    }
                }

            }

        }else{
            arr = new int[count];
            for (int i = 1; i < count + 1; i++) {
                arr[i-1] = i;
            }
        }

        modelAndView.addObject("employees", employees);
        modelAndView.addObject("employeesPagesList", arr);
        modelAndView.addObject("employeesCurrentPage", pageNumber);
        modelAndView.setViewName("employeesList");
        return modelAndView;
    }

    @RequestMapping(value = "/employees/search", method = RequestMethod.GET)
    public String searchGet() {
        return "employeesSearch";
    }

    @RequestMapping(value = "/employees/search", method = RequestMethod.POST)
    public ModelAndView searchPost(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        Employee employee = new Employee();
        modelAndView.setViewName("employeesSearch");
        String id = request.getParameter("id");

        String manager = request.getParameter("manager");
        boolean isValid = true;
        try{
            if(id.equals("")){
                employee.setId(-1);
            }else{
                employee.setId(Integer.valueOf(id));
            }
        }catch (Exception e){
            modelAndView.addObject("badId", "Значення повинно бути числом.");
            isValid = false;
        }
        try{
            if(manager.equals("")){
                employee.setManager(new Employee(-1));
            }else{
                employee.setManager(new Employee(Integer.valueOf(manager)));
            }
        }catch (Exception e){
            modelAndView.addObject("badManager", "Значення повинно бути числом.");
            isValid = false;
        }
        if(!isValid){
            modelAndView.addObject("id", request.getParameter("id"));
            modelAndView.addObject("name", request.getParameter("name"));
            modelAndView.addObject("manager", request.getParameter("manager"));
            return modelAndView;
        }

        employee.setName(request.getParameter("name"));

        List<Employee> employees = null;
        try {
            employees = getEmployeeDAO().getEmployees(employee);
        } catch (Exception e) {
            modelAndView.addObject("failMessage", e.getMessage());
            e.printStackTrace();
        }

        modelAndView.addObject("employees", employees);
        modelAndView.setViewName("employeesSearch");
        return modelAndView;
    }

    @RequestMapping(value = "/employees/edit/{idEmployee}", method=RequestMethod.GET)
    public ModelAndView employeeEditGet(@PathVariable("idEmployee") int idEmployee) {
        Employee employee = null;
        EmployeeDAO employeeDAO = getEmployeeDAO();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employeesEdit");
        try {
            employee = employeeDAO.getEmployee(idEmployee);
            modelAndView.addObject("employee", employee);
            if(employee == null){
                modelAndView.addObject("employee", new Employee());
                modelAndView.addObject("failMessage", "Співробітника з id = " + idEmployee + " немає.");
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/employees/edit/{idEmployee}", method=RequestMethod.POST)
    public ModelAndView employeeEditPost(@Valid Employee employee, BindingResult result, HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        EmployeeDAO employeeDAO = getEmployeeDAO();
        modelAndView.setViewName("employeesEdit");

        boolean jspFormIsValid = true;
        String managerError = "";


        if(result.hasErrors()){
            modelAndView.addObject("employee", employee);
            jspFormIsValid = false;
        }

        try {
            Integer manager = Integer.parseInt(request.getParameter("manager"));
        }catch(NumberFormatException e) {
            managerError += " Поле повинно бути числом.";
            jspFormIsValid = false;
        }

        if(employee.getManager() != null){
            try {
                employeeDAO.editEmployee(employee);
            } catch (SQLException e) {
                modelAndView.addObject("failMessage", e.getMessage());
                return modelAndView;
            }
            modelAndView.setViewName("blank");
            modelAndView.addObject("successMessage", "Співробітника з id = " + employee.getId() + " успішно змінено.");
            modelAndView.addObject("title", "Співробітники");
            return modelAndView;
        }else {
            modelAndView.addObject("employee", employee);
            modelAndView.addObject("managerNotFound", "Співробітника з id=" + employee.getManager().getId() + " немає.");
            modelAndView.setViewName("employeesEdit");
            return modelAndView;
        }

    }

    @RequestMapping(value = "/employees/add", method = RequestMethod.GET)
    public ModelAndView addEmployeeGet() {
        Employee employee = new Employee();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employee", employee);
        modelAndView.setViewName("employeesAdd");
        return modelAndView;
    }

    @RequestMapping(value = "/employees/add", method = RequestMethod.POST)
    public ModelAndView addEmployeePost(@Valid Employee employee, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if(result.hasErrors()) {
            modelAndView.setViewName("employeesAdd");
            modelAndView.addObject("employee", employee);
            return modelAndView;
        }else{
            EmployeeDAO employeeDAO = getEmployeeDAO();
            try {
                if(employeeDAO.getEmployee(employee.getManager().getId()) != null || employee.getManager().getId() == 0){
                    employeeDAO.addEmployee(employee);
                    modelAndView.setViewName("blank");
                    modelAndView.addObject("successMessage", "Співробітника успішно додано.");
                    modelAndView.addObject("title", "Співробітники");
                    return modelAndView;
                }else{

                    modelAndView.addObject("employee", employee);
                    modelAndView.addObject("managerNotFound", "Співробітника з id=" + employee.getManager() + " немає.");
                    return modelAndView;
                }
            } catch (Exception e) {
                modelAndView.setViewName("employeesAdd");
                modelAndView.addObject("failMessage", e.getMessage());
                return  modelAndView;
            }
        }
    }

    private EmployeeDAO getEmployeeDAO(){
        ApplicationContext context = new ClassPathXmlApplicationContext("xmlConfigs/main-application-context.xml");
        return (EmployeeDAO) context.getBean("employeeDAOImpl");
    }
}
