package application.Java_files.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerName;
    private String shippingAddress;
    private String paymentMethod;
    private List<OrderItem> orderItems;
    private double totalCost;
    private LocalDateTime orderDate;
    private String orderStatus;

    public Order(String orderId, String customerName, String shippingAddress, String paymentMethod,
                 shoppingCart cart, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.orderItems = new ArrayList<>(cart.getItems());
        this.orderDate = orderDate;
        this.totalCost = cart.getTotalCost();
        this.orderStatus = "Pending"; // Default status
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    // Setters
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    // Utility methods
    public int getTotalItems() {
        return orderItems.stream().mapToInt(OrderItem::getTotalQuantity).sum();
    }

    public int getTotalPhysicalCopies() {
        return orderItems.stream().mapToInt(OrderItem::getPhysicalCopies).sum();
    }

    public int getTotalEbookCopies() {
        return orderItems.stream().mapToInt(OrderItem::getEbookCopies).sum();
    }

    private double calculateTotalCost() {
        return orderItems.stream().mapToDouble(OrderItem::getTotalCost).sum();
    }

    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        totalCost += item.getTotalCost();
    }

    public void removeOrderItem(OrderItem item) {
        if (orderItems.remove(item)) {
            totalCost -= item.getTotalCost();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", orderItems=" + orderItems +
                ", totalCost=" + totalCost +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}