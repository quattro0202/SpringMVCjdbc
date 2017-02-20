package Spring.Controllers;

import Spring.DAO.OrderDAO;
import Spring.Models.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class OrderController {

    @RequestMapping(value = "/orders/list")
    public String listRedirect(){
        return "redirect:/orders/list/1";
    }

    @RequestMapping(value = "/orders/list/{pageNumber}")
    public ModelAndView list(@PathVariable("pageNumber") int pageNumber){
        ModelAndView modelAndView = new ModelAndView();
        final int limit = 10;
        final int offset = (pageNumber - 1) * 10;
        int count = 0;


        List<Order> employees = null;
        OrderDAO orderDAO = getOrderDAO();

        try{
            count = orderDAO.countOrders();
            employees = orderDAO.getOrders(limit, offset);
        }catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("failMessage", e.getMessage());
            modelAndView.setViewName("ordersList");
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

        modelAndView.addObject("orders", employees);
        modelAndView.addObject("ordersPagesList", arr);
        modelAndView.addObject("ordersCurrentPage", pageNumber);
        modelAndView.setViewName("ordersList");
        return modelAndView;

    }


    private OrderDAO getOrderDAO(){
        ApplicationContext context = new ClassPathXmlApplicationContext("xmlConfigs/main-application-context.xml");
        return (OrderDAO) context.getBean("orderDAOImpl");
    }

}
