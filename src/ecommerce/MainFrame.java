package ecommerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener {

    // Buttons
    JButton btnAddProduct, btnViewProducts, btnAddToCart;
    JButton btnUndoCart, btnPlaceOrder, btnViewOrders;
    JButton btnPayment, btnExit;

    // Label
    JLabel lblTitle, lblStatus;

    // Data structures
    Hashtable<Integer, Product> productTable;  // Hashtable for products
    CartManager cartManager;                    // Stack-based cart
    OrderManager orderManager;                  // Vector-based orders

    // Product ID counter
    int nextProductId = 1;

    // Constructor
    public MainFrame() {
        // Initialize data structures
        productTable = new Hashtable<>();
        cartManager = new CartManager();
        orderManager = new OrderManager();

        // Frame settings
        setTitle("E-Commerce Platform");
        setSize(500, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background color
        getContentPane().setBackground(new Color(240, 240, 250));

        // Title label
        lblTitle = new JLabel("E-Commerce Shopping System", JLabel.CENTER);
        lblTitle.setBounds(50, 15, 400, 35);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(50, 50, 150));
        add(lblTitle);

        // Create buttons
        btnAddProduct = new JButton("Add Product");
        btnAddProduct.setBounds(150, 70, 200, 40);

        btnViewProducts = new JButton("View Products");
        btnViewProducts.setBounds(150, 120, 200, 40);

        btnAddToCart = new JButton("Add to Cart");
        btnAddToCart.setBounds(150, 170, 200, 40);

        btnUndoCart = new JButton("Undo Last Add");
        btnUndoCart.setBounds(150, 220, 200, 40);

        btnPlaceOrder = new JButton("Place Order");
        btnPlaceOrder.setBounds(150, 270, 200, 40);

        btnViewOrders = new JButton("View Orders");
        btnViewOrders.setBounds(150, 320, 200, 40);

        btnPayment = new JButton("Process Payment");
        btnPayment.setBounds(150, 370, 200, 40);

        btnExit = new JButton("Exit");
        btnExit.setBounds(150, 420, 200, 40);
        btnExit.setBackground(new Color(220, 80, 80));
        btnExit.setForeground(Color.WHITE);

        // Status label
        lblStatus = new JLabel("Welcome! Total Products: 0", JLabel.CENTER);
        lblStatus.setBounds(50, 475, 400, 25);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        lblStatus.setForeground(Color.GRAY);
        add(lblStatus);

        // Style buttons
        JButton[] buttons = {btnAddProduct, btnViewProducts, btnAddToCart,
                btnUndoCart, btnPlaceOrder, btnViewOrders, btnPayment};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        // Add buttons to frame
        add(btnAddProduct);
        add(btnViewProducts);
        add(btnAddToCart);
        add(btnUndoCart);
        add(btnPlaceOrder);
        add(btnViewOrders);
        add(btnPayment);
        add(btnExit);

        // Add action listeners
        btnAddProduct.addActionListener(this);
        btnViewProducts.addActionListener(this);
        btnAddToCart.addActionListener(this);
        btnUndoCart.addActionListener(this);
        btnPlaceOrder.addActionListener(this);
        btnViewOrders.addActionListener(this);
        btnPayment.addActionListener(this);
        btnExit.addActionListener(this);

        // Load products from database into Hashtable
        loadProductsFromDB();

        setVisible(true);
    }

    // Load products from Oracle DB into Hashtable
    private void loadProductsFromDB() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Could not connect to database!",
                        "DB Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "SELECT productId, name, price, stock FROM PRODUCTS";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("productId");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                Product product = new Product(id, name, price, stock);
                productTable.put(id, product);

                if (id >= nextProductId) {
                    nextProductId = id + 1;
                }
            }

            rs.close();
            stmt.close();
            System.out.println("Products loaded from database: " + productTable.size());
            updateStatus();

        } catch (SQLException e) {
            System.out.println("Error loading products from database!");
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // Save a product to the database
    private boolean saveProductToDB(Product product) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) return false;

            String sql = "INSERT INTO PRODUCTS (productId, name, price, stock) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, product.getProductId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getStock());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Product saved to database: " + product.getName());
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving product to database!");
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // Save an order to the database
    private boolean saveOrderToDB(int customerId, String productName) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) return false;

            String sql = "INSERT INTO ORDERS (orderId, customerId, productName) VALUES (order_seq.NEXTVAL, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setString(2, productName);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Order saved to database: " + productName);
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving order to database!");
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {

        // --- ADD PRODUCT ---
        if (e.getSource() == btnAddProduct) {
            String name = JOptionPane.showInputDialog(this, "Enter Product Name:");
            if (name == null || name.trim().isEmpty()) return;

            String priceStr = JOptionPane.showInputDialog(this, "Enter Price:");
            if (priceStr == null) return;

            String stockStr = JOptionPane.showInputDialog(this, "Enter Stock Quantity:");
            if (stockStr == null) return;

            try {
                double price = Double.parseDouble(priceStr);
                int stock = Integer.parseInt(stockStr);

                Product product = new Product(nextProductId, name.trim(), price, stock);
                productTable.put(nextProductId, product);

                // Save to database
                saveProductToDB(product);

                nextProductId++;

                JOptionPane.showMessageDialog(this,
                        "Product added: " + name + "\nTotal Products: " + Product.totalProducts
                                + "\nSaved to database!");
                updateStatus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Please enter numbers.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // --- VIEW PRODUCTS ---
        else if (e.getSource() == btnViewProducts) {
            if (productTable.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No products available!");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-5s %-15s %-12s %-8s\n", "ID", "Name", "Price", "Stock"));
            sb.append("------------------------------------------\n");

            for (Integer key : productTable.keySet()) {
                Product p = productTable.get(key);
                sb.append(p.toString()).append("\n");
            }
            sb.append("\nTotal Products: ").append(Product.totalProducts);

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 250));

            JOptionPane.showMessageDialog(this, scrollPane, "Product List",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // --- ADD TO CART ---
        else if (e.getSource() == btnAddToCart) {
            if (productTable.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No products available!");
                return;
            }

            String idStr = JOptionPane.showInputDialog(this, "Enter Product ID to add to cart:");
            if (idStr == null) return;

            try {
                int id = Integer.parseInt(idStr);
                Product product = productTable.get(id);

                if (product == null) {
                    JOptionPane.showMessageDialog(this, "Product not found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else if (product.getStock() <= 0) {
                    JOptionPane.showMessageDialog(this, "Product out of stock!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    cartManager.addToCart(product);
                    JOptionPane.showMessageDialog(this,
                            product.getName() + " added to cart!\nCart size: " + cartManager.getCartSize());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // --- UNDO LAST ADD ---
        else if (e.getSource() == btnUndoCart) {
            Product removed = cartManager.undoLastAdd();
            if (removed != null) {
                JOptionPane.showMessageDialog(this,
                        removed.getName() + " removed from cart.\nCart size: " + cartManager.getCartSize());
            } else {
                JOptionPane.showMessageDialog(this, "Cart is empty! Nothing to undo.");
            }
        }

        // --- PLACE ORDER ---
        else if (e.getSource() == btnPlaceOrder) {
            if (cartManager.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Cart is empty! Add items first.");
                return;
            }

            String customerName = JOptionPane.showInputDialog(this, "Enter Customer Name:");
            if (customerName == null || customerName.trim().isEmpty()) return;

            String customerIdStr = JOptionPane.showInputDialog(this, "Enter Customer ID (number):");
            if (customerIdStr == null) return;

            try {
                int customerId = Integer.parseInt(customerIdStr);

                // Place order for each item in cart
                Stack<Product> items = cartManager.getCartItems();
                StringBuilder orderSummary = new StringBuilder("Order placed for:\n");

                for (Product p : items) {
                    orderManager.placeOrder(customerName.trim(), p.getName(), p.getPrice());
                    // Save order to database
                    saveOrderToDB(customerId, p.getName());
                    orderSummary.append("- ").append(p.getName())
                            .append(" (Rs.").append(String.format("%.2f", p.getPrice())).append(")\n");
                }
                orderSummary.append("\nTotal: Rs.").append(String.format("%.2f", cartManager.getTotal()));
                orderSummary.append("\nOrders saved to database!");

                cartManager.clearCart();
                JOptionPane.showMessageDialog(this, orderSummary.toString(),
                        "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Customer ID!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // --- VIEW ORDERS ---
        else if (e.getSource() == btnViewOrders) {
            Vector<String> orders = orderManager.viewOrders();
            if (orders.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No orders placed yet!");
                return;
            }

            StringBuilder sb = new StringBuilder("--- Order History ---\n\n");
            for (String order : orders) {
                sb.append(order).append("\n");
            }
            sb.append("\nTotal Orders: ").append(orderManager.getTotalOrders());

            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 250));

            JOptionPane.showMessageDialog(this, scrollPane, "Order History",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // --- PROCESS PAYMENT ---
        else if (e.getSource() == btnPayment) {
            String customerName = JOptionPane.showInputDialog(this, "Enter Customer Name:");
            if (customerName == null || customerName.trim().isEmpty()) return;

            String amountStr = JOptionPane.showInputDialog(this, "Enter Payment Amount:");
            if (amountStr == null) return;

            try {
                double amount = Double.parseDouble(amountStr);

                // Create and start payment thread
                PaymentThread payment = new PaymentThread(customerName.trim(), amount);
                Thread thread = new Thread(payment);
                thread.start();

                JOptionPane.showMessageDialog(this,
                        "Payment is being processed...\nPlease wait for confirmation.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // --- EXIT ---
        else if (e.getSource() == btnExit) {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Exit",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    // Update status bar
    private void updateStatus() {
        lblStatus.setText("Total Products: " + Product.totalProducts +
                " | Cart: " + cartManager.getCartSize() +
                " | Orders: " + orderManager.getTotalOrders());
    }
}
