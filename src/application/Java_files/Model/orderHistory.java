package application.Java_files.Model;

import java.util.ArrayList;
import java.util.List;

public class orderHistory {
    private user user;                  // The user associated with the order history
    private List<order> orders;         // List of past orders for this user

    // Constructor to initialize the order history for a user
    public orderHistory(user user) {
        this.user = user;
        this.orders = new ArrayList<>();
    }

    // Method to add a new order to the user's order history
    public void addOrder(order order) {
        orders.add(order);
    }

    // Method to display all orders for the user
    public void displayOrderHistory() {
        System.out.println("Order History for " + user.getName() + ":");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (order order : orders) {
                System.out.println("-----------------------------------------");
                order.printOrderSummary(); // Display order summary
            }
        }
    }

    // Method to retrieve a specific order by its ID
    public order getOrderById(String orderId) {
        for (order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null; // If no matching order is found
    }

    // Method to display a specific order by its ID
    public void displayOrderById(String orderId) {
        order order = getOrderById(orderId);
        if (order != null) {
            System.out.println("Order details for ID: " + orderId);
            order.printOrderSummary();
        } else {
            System.out.println("No order found with ID: " + orderId);
        }
    }
}
