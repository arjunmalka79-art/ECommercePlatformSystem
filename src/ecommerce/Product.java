package ecommerce;

public class Product {
    // Instance variables
    private int productId;
    private String name;
    private double price;
    private int stock;

    // Static variable to track total products
    static int totalProducts = 0;

    // Constructor
    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        totalProducts++;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Display product info using String methods
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %-15s | Price: Rs.%.2f | Stock: %d",
                productId, name, price, stock);
    }
}
