package ecommerce;

import java.util.Vector;

public class OrderManager {

    // Vector to store order history
    private Vector<String> orderHistory;
    private int orderCounter;

    // Constructor
    public OrderManager() {
        orderHistory = new Vector<>();
        orderCounter = 0;
    }

    // Place an order
    public void placeOrder(String customerName, String productName, double price) {
        orderCounter++;
        String order = String.format("Order #%d | Customer: %s | Product: %s | Price: Rs.%.2f",
                orderCounter, customerName, productName, price);
        orderHistory.add(order);
        System.out.println("Order placed: " + order);
    }

    // View all orders
    public Vector<String> viewOrders() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders placed yet.");
        } else {
            System.out.println("--- Order History ---");
            for (String order : orderHistory) {
                System.out.println(order);
            }
        }
        return orderHistory;
    }

    // Get total number of orders
    public int getTotalOrders() {
        return orderHistory.size();
    }
}
