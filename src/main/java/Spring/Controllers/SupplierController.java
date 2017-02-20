package Spring.Controllers;

import Spring.DAO.SupplierDAO;
import Spring.Models.Supplier;
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
import java.util.List;


@Controller
public class SupplierController {



    @RequestMapping("/suppliers")
    public ModelAndView showSuppliers() throws SQLDataException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("suppliers");
        List<Supplier> suppliers = null;
        SupplierDAO supplierDAO = getSupplierDAO();
        try{
            suppliers = supplierDAO.getSuppliers();
            modelAndView.addObject("suppliers", suppliers);
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/suppliers/delete/{idSupplier}", method = RequestMethod.GET)
    public ModelAndView deleteSupplier(@PathVariable("idSupplier") int id) throws SQLDataException {
        ModelAndView modelAndView = new ModelAndView();
        SupplierDAO supplierDAO = getSupplierDAO();
        Supplier supplier = null;
        modelAndView.setViewName("blank");
        modelAndView.addObject("title", "Постачальники");
        try{
            supplier = supplierDAO.getSupplier(id);
            if(supplier != null){
                supplierDAO.deleteSupplier(id);
                modelAndView.addObject("successMessage", "Постачальника з id = " + id + " успішно видалено.");
                return modelAndView;
            }else{
                modelAndView.addObject("failMessage", "Постачальника з id = " + id + " немає.");
                return modelAndView;
            }
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }
        return modelAndView;
    }


    @RequestMapping(value = "/suppliers/edit/{idSupplier}", method=RequestMethod.GET)
    public ModelAndView supplierEditGet(@PathVariable("idSupplier") int idSupplier) {
        Supplier supplier = null;
        SupplierDAO supplierDAO = getSupplierDAO();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("supplierEdit");
        try {
            supplier = supplierDAO.getSupplier(idSupplier);
            modelAndView.addObject("supplier", supplier);
            if(supplier == null){
                modelAndView.addObject("supplier", new Supplier());
                modelAndView.addObject("failMessage", "Постачальника з id = " + idSupplier + " немає.");
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/suppliers/edit/{idSupplier}", method=RequestMethod.POST)
    public ModelAndView supplierEditPost(@Valid Supplier supplier, BindingResult result, ModelAndView modelAndView){


        if(result.hasErrors()) {
            modelAndView.setViewName("supplierEdit");
            modelAndView.addObject("supplier", supplier);
            return modelAndView;
        }
        SupplierDAO supplierDAO = getSupplierDAO();
        try {
            supplierDAO.editSupplier(supplier);
            modelAndView.setViewName("blank");
            modelAndView.addObject("successMessage", "Постачальника з id = " + supplier.getId() + " успішно змінено.");
            modelAndView.addObject("title", "Постачальники");
            return modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
        }
        modelAndView.setViewName("supplierEdit");
        return modelAndView;
    }


    @RequestMapping("/suppliers/list")
    public String showSuppliersList() throws SQLDataException {

        return "redirect:/suppliers/list/1";
    }

    @RequestMapping("/suppliers/list/{num}")
    public ModelAndView showSuppliersListLimitOffset(@PathVariable("num") int num) throws SQLDataException {
        ModelAndView modelAndView = new ModelAndView();

        final int limit = 10;
        final int offset = (num - 1) * 10;
        int count = 0;


        List<Supplier> suppliers = null;
        SupplierDAO supplierDAO = getSupplierDAO();
        try{
            count = supplierDAO.countSuppliers();
            suppliers = supplierDAO.getSuppliers(limit, offset);
        }catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("suppliersList");
            modelAndView.addObject("failMessage", e.getMessage());
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
            if(num < 5){
                for (int i = 1; i < 11; i++) {
                    arr[i-1] = i;
                }
            }else{
                if(count - num >= 5){
                    for (int i = num - 4, j = 0; i < num + 6; i++, j++) {
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

        modelAndView.addObject("suppliers", suppliers);
        modelAndView.addObject("suppliersPagesList", arr);
        modelAndView.addObject("suppliersCurrentPage", num);
        modelAndView.setViewName("suppliersList");
        return modelAndView;
    }



    @RequestMapping(value = "/suppliers/search", method = RequestMethod.POST)
    public ModelAndView searchPost(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Supplier supplier = new Supplier();
        String id = request.getParameter("id");
        try{
            if(id.equals("")){
                supplier.setId(-1);
            }else{
                supplier.setId(Integer.valueOf(id));
            }
        }catch (Exception e){
            modelAndView.addObject("id", request.getParameter("id"));
            modelAndView.addObject("name", request.getParameter("name"));
            modelAndView.addObject("address", request.getParameter("address"));
            modelAndView.addObject("phone", request.getParameter("phone"));
            modelAndView.addObject("email", request.getParameter("email"));
            modelAndView.addObject("badId", "Значення повинно бути числом.");
            modelAndView.setViewName("suppliersSearch");
            return modelAndView;
        }


        supplier.setName(request.getParameter("name"));
        supplier.setAddress(request.getParameter("address"));
        supplier.setPhone(request.getParameter("phone"));
        supplier.setEmail(request.getParameter("email"));
        List<Supplier> suppliers = null;

        try {
            suppliers = getSupplierDAO().getSuppliers(supplier);
        } catch (Exception e) {
            modelAndView.addObject("failMessage", e.getMessage());
            e.printStackTrace();
        }


        modelAndView.addObject("suppliers", suppliers);
        modelAndView.setViewName("suppliersSearch");
        return modelAndView;
    }


    @RequestMapping(value = "/suppliers/search", method = RequestMethod.GET)
    public String searchGet() {
        return "suppliersSearch";
    }


    @RequestMapping(value = "/suppliers/add", method = RequestMethod.GET)
    public ModelAndView addSupplierGet() {
        Supplier supplier = new Supplier();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("supplier", supplier);
        modelAndView.setViewName("suppliersAdd");
        return modelAndView;
    }

    @RequestMapping(value = "/suppliers/add", method = RequestMethod.POST)
    public ModelAndView addSupplierPost(@Valid Supplier supplier, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if(result.hasErrors()) {
            modelAndView.setViewName("suppliersAdd");
            modelAndView.addObject("supplier", supplier);
            return modelAndView;
        }else{
            SupplierDAO supplierDAO = getSupplierDAO();
            modelAndView.setViewName("blank");
            try {
                supplierDAO.addSupplier(supplier);
                modelAndView.addObject("successMessage", "Постачальника успішно додано.");
                modelAndView.addObject("title", "Постачальники");
                return  modelAndView;

            } catch (Exception e) {
                modelAndView.setViewName("suppliersAdd");
                modelAndView.addObject("failMessage", e.getMessage());
                modelAndView.addObject("title", "Постачальники");
                return  modelAndView;
            }
        }
    }





    private SupplierDAO getSupplierDAO(){
        ApplicationContext context = new ClassPathXmlApplicationContext("xmlConfigs/main-application-context.xml");
        return (SupplierDAO) context.getBean("supplierDAOImpl");
    }
}
