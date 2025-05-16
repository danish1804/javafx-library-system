package application.Java_files.Service;

import application.Java_files.Model.Order;
import application.Java_files.Model.OrderItem;
import application.Java_files.Model.Book;
import application.Java_files.DAO.OrderDAO;
import application.Java_files.Service.BookService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private OrderDAO orderDAO;
    private BookService bookService;

    public OrderService(OrderDAO orderDAO, BookService bookService) {
        this.orderDAO = orderDAO;
        this.bookService = bookService;
    }

    public Order createOrder(String customerName, String shippingAddress, String paymentMethod, List<OrderItem> orderItems) {
        String orderId = generateOrderId();
        LocalDateTime orderDate = LocalDateTime.now();
        double totalCost = calculateTotalCost(orderItems);

        Order newOrder = new Order(orderId, customerName, shippingAddress, paymentMethod, orderItems, orderDate);
        newOrder.setTotalCost(totalCost);
        
        if (updateInventory(orderItems)) {
            boolean saved = orderDAO.saveOrder(newOrder);
            if (saved) {
                return newOrder;
            } else {
                // Rollback inventory changes if order save fails
                revertInventoryChanges(orderItems);
            }
        }
        return null;
    }

    public Order getOrderById(String orderId) {
        return orderDAO.getOrderById(orderId);
    }

    public List<Order> getOrdersByCustomer(String customerName) {
        return orderDAO.getOrdersByCustomer(customerName);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public boolean updateOrderStatus(String orderId, String newStatus) {
        Order order = orderDAO.getOrderById(orderId);
        if (order != null) {
            order.setOrderStatus(newStatus);
            return orderDAO.updateOrder(order);
        }
        return false;
    }

    public boolean cancelOrder(String orderId) {
        Order order = orderDAO.getOrderById(orderId);
        if (order != null && order.getOrderStatus().equals("Pending")) {
            order.setOrderStatus("Cancelled");
            if (orderDAO.updateOrder(order)) {
                revertInventoryChanges(order.getOrderItems());
                return true;
            }
        }
        return false;
    }

    private String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private double calculateTotalCost(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(item -> (item.getPhysicalCopies() * item.getPhysicalPrice()) + 
                                     (item.getEbookCopies() * item.getEbookPrice()))
                .sum();
    }

    private boolean updateInventory(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Book book = bookService.getBookById(item.getBookId());
            if (book != null) {
                if (item.getPhysicalCopies() > 0) {
                    if (book.getNumberOfPhysicalCopies() < item.getPhysicalCopies()) {
                        return false; // Not enough physical copies
                    }
                    book.decreasePhysicalCopies(item.getPhysicalCopies());
                }
                // For e-books, we don't need to decrease inventory
                bookService.updateBook(book);
            } else {
                return false; // Book not found
            }
        }
        return true;
    }

    private void revertInventoryChanges(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Book book = bookService.getBookById(item.getBookId());
            if (book != null && item.getPhysicalCopies() > 0) {
                book.increasePhysicalCopies(item.getPhysicalCopies());
                bookService.updateBook(book);
            }
        }
    }
}