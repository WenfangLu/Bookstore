
package business;

import business.book.BookDao;
import business.book.BookDaoJdbc;
import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.order.*;
import business.order.DefaultOrderService;

import java.sql.Connection;
import java.util.List;

public class ApplicationContext {

    private final BookDao bookDao;

    private final CategoryDao categoryDao;

    private final OrderService orderService;

    private final OrderDao orderDao;

    private final LineItemDao lineItemDao;

    private final CustomerDao customerDao;

    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {

        categoryDao = new CategoryDaoJdbc();
        bookDao = new BookDaoJdbc();
        orderService = new DefaultOrderService();
        orderDao = new OrderDaoJdbc();
        lineItemDao = new LineItemDaoJdbc();
        customerDao = new CustomerDaoJdbc();

        ((DefaultOrderService)orderService).setBookDao(bookDao);
        ((DefaultOrderService)orderService).setOrderDao(orderDao);
        ((DefaultOrderService)orderService).setCustomerDao(customerDao);
        ((DefaultOrderService)orderService).setLineItemDao(lineItemDao);

    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public BookDao getBookDao() { return bookDao; }

    public OrderService getOrderService(){
        return orderService;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }
    public LineItemDao getLineItemDao() {
        return lineItemDao;
    }
    public CustomerDao getCustomerDao() {
        return customerDao;
    }
}
