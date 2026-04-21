package ecommerce;

import javax.swing.JOptionPane;

public class PaymentThread implements Runnable {

    private String customerName;
    private double amount;

    // Constructor
    public PaymentThread(String customerName, double amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    // Run method - simulates payment processing
    @Override
    public void run() {
        try {
            System.out.println("Processing payment for " + customerName + "...");

            // Simulate payment processing with Thread.sleep()
            Thread.sleep(3000); // Wait 3 seconds

            String message = String.format(
                    "Payment Successful!\nCustomer: %s\nAmount: Rs.%.2f\nTransaction Complete!",
                    customerName, amount);

            System.out.println(message);

            // Show success message
            JOptionPane.showMessageDialog(null, message,
                    "Payment Status", JOptionPane.INFORMATION_MESSAGE);

        } catch (InterruptedException e) {
            System.out.println("Payment interrupted!");
            e.printStackTrace();
        }
    }
}
