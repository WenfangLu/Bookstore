package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private CustomerDao customerDao;
	private OrderDao orderDao;
	private LineItemDao lineItemDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
    public void setCustomerDao(CustomerDao customerDao){this.customerDao = customerDao;}
	public void  setOrderDao(OrderDao orderDao){this.orderDao = orderDao;}
	public void setLineItemDao(LineItemDao lineItemDao){this.lineItemDao = lineItemDao;}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	private int generateConfirmationNumber(){
		Random random = new Random();
		return random.nextInt(999999999);
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(connection,name,address,phone,email,ccNumber,date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}

	}
	private Date getDate(String monthString, String yearString) {
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(yearString), Integer.parseInt(monthString)-1, 1);  //January 30th 2000
		Date date = c1.getTime();
		return c1.getTime();
	}

	private void validateCustomer(CustomerForm customerForm) {

		// check name
		if (!nameIsValid(customerForm.getName())) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}
		// check phone
		if(!phoneIsValid(customerForm.getPhone())){
			throw new ApiException.InvalidParameter("Invalid phone number");
		}
		// check expiryDate
		if (!expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");
		}

		// check address
		if(!addressIsValid(customerForm.getAddress())){
			throw new ApiException.InvalidParameter("Invalid address");
		}
		// check email
		if(!emailIsValid(customerForm.getEmail())){
			throw new ApiException.InvalidParameter("Invalid email");
		}
		//check credit card number
		if(!creditCardNumberIsInvalid(customerForm.getCcNumber())){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}




	}

	private boolean nameIsValid(String name){
		if (name == null || name.equals("") || name.length() > 45 || name.length() < 4) {
			return false;
		}
		return true;
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		Date date= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int currentMonth = cal.get(Calendar.MONTH);
		int currentYear = cal.get(Calendar.YEAR);
		if( Integer.parseInt(ccExpiryYear) < currentYear && Integer.parseInt(ccExpiryMonth) < currentMonth )
			return false;

		return true;

	}
	private boolean creditCardNumberIsInvalid(String creditCardNumber) {

		if(creditCardNumber == null) return false;
		if (creditCardNumber.equals("")) return false;
		// get rid of  parens, space and dashes
		creditCardNumber = creditCardNumber.replaceAll(" ", "");
		creditCardNumber = creditCardNumber.replaceAll("-", "");
		if(creditCardNumber.length() < 14 || creditCardNumber.length() > 16){
			return false;
		}
		return true;

	}


	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 0 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());

			if(item.getBookForm().getPrice() != databaseBook.getPrice()){
				throw new ApiException.InvalidParameter("Invalid price");
			}
			if(item.getBookForm().getCategoryId() != databaseBook.getCategoryId()){
				throw new ApiException.InvalidParameter("Invalid category");
			}

		});
	}
	private boolean addressIsValid(String address){
		if (address == null) return false;
		if (address.equals("")) return false;
		if (address.length() > 45 || address.length() < 4) return false;
		return true;
	}

	private boolean phoneIsValid(String phone){
		if (phone == null) return false;
		if (phone.equals("")) return false;

		// get rid of  parens, space and dashes
		phone = phone.replaceAll(" ", "");
		phone = phone.replaceAll("-", "");
		phone = phone.replaceAll("\\(","");
		phone = phone.replaceAll("\\)","");
		// should have a number (no letters) with exactly 10 digits
		if(phone.length() != 10) return false;
		if(phone.matches("[\\d]+") == false) return false;


		return true;
	}

	public  boolean emailIsValid (String email){

		String lastChar = String.valueOf(email.charAt(email.length()-1));
		if( email.equals("")) return false;
		if( email.contains(" ")) return false;
		if( email == null) return false;
		if( email.indexOf("@") == -1 ) return false;
		if( lastChar == ".") return false;

		return true;
	}

}
