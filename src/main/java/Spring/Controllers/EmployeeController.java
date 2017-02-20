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
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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
        String reqParamId = request.getParameter("id");
        String reqParamName = request.getParameter("name");
        String reqParamManager = request.getParameter("manager");

        boolean parametersValid = true;

        String idFieldError = "";
        String nameFieldError = "";
        String managerFieldError = "";


        if(!checkWithRegExp(reqParamId, 4)){
            idFieldError += " Поле повинно бути числом " + "\u2265" + " 0 (0-null), в межах Int.";
            parametersValid = false;
        }else {
            if (Long.valueOf(reqParamId) > (long) Integer.MAX_VALUE) {
                managerFieldError += " Поле повинно бути числом " + "\u2265" + " 0 (0-null), в межах Int.";
                parametersValid = false;
            }
        }

        if (!checkWithRegExp(reqParamName, 3)) {
            nameFieldError += " Поле повинно скаладатися з літер та цифр.";
            parametersValid = false;
        }

        if (!checkWithRegExp(reqParamManager, 2)) {
            managerFieldError += " Поле повинно бути числом " + "\u2265" + " 0 (0-null), в межах Int.";
            parametersValid = false;
        } else {
            if (Long.valueOf(reqParamManager) > (long) Integer.MAX_VALUE) {
                managerFieldError += " Поле повинно бути числом " + "\u2265" + " 0 (0-null), в межах Int.";
                parametersValid = false;
            }
        }

        if (!parametersValid) {
            modelAndView.addObject("idFieldError", idFieldError);
            modelAndView.addObject("nameFieldError", nameFieldError);
            modelAndView.addObject("managerFieldError", managerFieldError);
            modelAndView.addObject("id", reqParamId);
            modelAndView.addObject("name", reqParamName);
            modelAndView.addObject("manager", reqParamManager);
            modelAndView.setViewName("employeesSearch");
            return modelAndView;
        }

        Employee employee = new Employee(Integer.valueOf(reqParamId), );

        employee.setName(request.getParameter("name"));

        List<Employee> employees = null;
        try {
            employees = getEmployeeDAO().findEmployees(employee);
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
            if(employee == null){
                modelAndView.addObject("failMessage", "Співробітника з id = " + idEmployee + " немає.");
                return modelAndView;
            }else {
                modelAndView.addObject("id", employee.getId());
                modelAndView.addObject("name", employee.getName());
                modelAndView.addObject("manager", employee.getManager().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/employees/edit/{idEmployee}", method=RequestMethod.POST)
    public ModelAndView employeeEditPost(@PathVariable("idEmployee") int idEmployee, HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();

        String reqParamName = request.getParameter("name");
        String reqParamManager = request.getParameter("manager");

        boolean parametersValid = true;

        String nameFieldError = "";
        String managerFieldError = "";

        if(!checkWithRegExp(reqParamName, 1)){
            nameFieldError += " Поле повинно мати від 2 до 30 літер чи цифр.";
            parametersValid = false;
        }
        if(!checkWithRegExp(reqParamManager, 2)){
            managerFieldError += " Поле повинно бути числом " + "\u2265" + " 0 (0-null), в межах Int.";
            parametersValid = false;
        }else {
            if(Long.valueOf(reqParamManager) > (long)Integer.MAX_VALUE){
                managerFieldError += " Поле повинно бути числом " + "\u2265" + " 0 (0-null), в межах Int.";
                parametersValid = false;
            }else{
                if(Integer.valueOf(reqParamManager) == idEmployee){
                    managerFieldError += " Співробітник не може бути сам собі менеджером.";
                    parametersValid = false;
                }else{
                    EmployeeDAO employeeDAO = getEmployeeDAO();
                    try {
                        if(employeeDAO.getEmployee(Integer.valueOf(reqParamManager)) == null){
                            parametersValid = false;
                            managerFieldError += " Співробітника з id=" + Integer.valueOf(reqParamManager)
                                    + " немає.";
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        modelAndView.addObject("failMessage", e.getMessage());
                    }
                }
            }
        }
        if(!parametersValid){
            modelAndView.addObject("nameFieldError", nameFieldError);
            modelAndView.addObject("managerFieldError", managerFieldError);
            modelAndView.addObject("id", idEmployee);
            modelAndView.addObject("name", reqParamName);
            modelAndView.addObject("manager", reqParamManager);
            modelAndView.setViewName("employeesEdit");
            return modelAndView;
        }

        Employee employee = new Employee(idEmployee, reqParamName, new Employee(Integer.valueOf(reqParamManager)));
        EmployeeDAO employeeDAO = getEmployeeDAO();
        try {
            employeeDAO.editEmployee(employee);
            modelAndView.setViewName("blank");
            modelAndView.addObject("title", "Співробітники");
            modelAndView.addObject("successMessage", "Співробітника з id=" + employee.getId() + " успішно змінено.");
            return modelAndView;

        } catch (SQLException e) {
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }

        modelAndView.setViewName("employeesEdit");
        return modelAndView;
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

    public static boolean checkWithRegExp(String str, int regExNum){
        Pattern p = null;
        switch (regExNum){
            case 1 : p = Pattern.compile("[\\p{L}0-9]{3,30}$"); break;
            case 2 : p = Pattern.compile("([1-9][0-9]{0,9})|(0+)$"); break;
            case 3 : p = Pattern.compile("[\\p{L}0-9]{0,30}$"); break;
            case 4 : p = Pattern.compile("[1-9]{0}[0-9]{0,9}$|( )"); break;
        }

        Matcher m = p.matcher(str);
        return m.matches();
    }

    private EmployeeDAO getEmployeeDAO(){
        ApplicationContext context = new ClassPathXmlApplicationContext("xmlConfigs/main-application-context.xml");
        return (EmployeeDAO) context.getBean("employeeDAOImpl");
    }
}
