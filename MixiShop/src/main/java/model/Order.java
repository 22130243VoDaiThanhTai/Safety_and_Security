package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Order {
    private static int orderCount = 0;
    private int orderId;
    private int userId;
    private String email;
    private List<Item> items;
    private String nameUser;
    private String address;
    private String phoneNumber;
    private Date orderDate;
    private Date deliveryDate;
    private double total;
    private String status;
    private boolean isConfirmed;

    public Order(int orderId, int userId, String email, List<Item> items, String address, String phoneNumber, Date orderDate, double total, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.email = email;
        this.items = items;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.total = total;
        this.status = "Pending";
    }

    // Constructor
    public Order(Cart cart, String nameUser, String address, String phoneNumber) {
        this.orderId = ++orderCount;
        this.items = new ArrayList<>(cart.getItems());
        this.setNameUser(nameUser);
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderDate = new Date();
        this.deliveryDate = calculateDeliveryDate(orderDate);  // Tính ngày nhận là 3 ngày sau ngày order
        this.total = cart.getTotal();
        this.status = "Pending";
    }

    public Order() {

    }

    // Getter và Setter


    public static int getOrderCount() {
        return orderCount;
    }

    public static void setOrderCount(int orderCount) {
        Order.orderCount = orderCount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
	public void updateStatus() {
	    Date currentDate = new Date(); // Lấy ngày hiện tại
	    String preparingOrder = "Đang lên đơn hàng";
	    String shippingOrder = "Đang vận chuyển";
	    String pleaseReceiveOrder = "Vui lòng nhận hàng";
	    String receivedOrder = "Đã nhận hàng";
	    String cancelOrder = "Hủy đơn hàng";

	    // Nếu đã xác nhận (Đã nhận hàng)
	    if (isConfirmed) {
	        this.status = receivedOrder;
	    }
	    
	    ///// NGÀY ĐẶT HÀNG
	    else if (currentDate.equals(orderDate)) {
			this.status = preparingOrder;
		}
	    
	    
	    // Nếu ngày hiện tại trước ngày nhận hàng
	    else if (currentDate.after(orderDate) || currentDate.before(deliveryDate)) {
	        this.status = shippingOrder;
	    }
	    // Nếu ngày hiện tại là ngày nhận hàng mà chưa xác nhận (trạng thái vẫn là "Đang vận chuyển")
	    else if (currentDate.equals(deliveryDate) || currentDate.before(addDaysToDate(deliveryDate, 2))) {
	        // Nếu chưa xác nhận trong ngày nhận hàng hoặc hôm sau nhưng chưa xác nhận
	        this.status = pleaseReceiveOrder;
	    }
	    // Nếu đã qua 2 ngày kể từ ngày nhận hàng mà chưa xác nhận, hủy đơn hàng
	    else if (currentDate.after(addDaysToDate(deliveryDate, 1))) {
	        this.status = cancelOrder;
	    }
	}
    
    private Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

	// Phương thức tính toán ngày nhận (3 ngày sau ngày đặt hàng)
    private Date calculateDeliveryDate(Date orderDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(orderDate);
        calendar.add(Calendar.DATE, 3);  // Thêm 3 ngày vào ngày order
        return calendar.getTime();
    }

    // Phương thức định dạng orderDate theo định dạng mong muốn
    public String getFormattedOrderDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        return formatter.format(orderDate);
    }

    // Phương thức định dạng deliveryDate theo định dạng mong muốn
    public String getFormattedDeliveryDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        return formatter.format(deliveryDate);
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", items=" + items + ", address=" + address + ", phoneNumber=" + phoneNumber
                + ", orderDate=" + getFormattedOrderDate() + ", deliveryDate=" + getFormattedDeliveryDate() 
                + ", total=" + total + ", status=" + status + "]";
    }

	public String getNameUser() {
		return nameUser;
	}

	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
}
