package ecommerce;

import java.util.Stack;

public class CartManager {

    // Stack to store cart items (supports undo)
    private Stack<Product> cart;

    // Constructor
    public CartManager() {
        cart = new Stack<>();
    }

    // Add product to cart
    public void addToCart(Product product) {
        cart.push(product);
        System.out.println(product.getName() + " added to cart.");
    }

    // Undo last add (remove last item from cart)
    public Product undoLastAdd() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty! Nothing to undo.");
            return null;
        }
        Product removed = cart.pop();
        System.out.println(removed.getName() + " removed from cart.");
        return removed;
    }

    // Get all items in cart
    public Stack<Product> getCartItems() {
        return cart;
    }

    // Check if cart is empty
    public boolean isEmpty() {
        return cart.isEmpty();
    }

    // Get cart size
    public int getCartSize() {
        return cart.size();
    }

    // Clear cart
    public void clearCart() {
        cart.clear();
    }

    // Get total price of cart
    public double getTotal() {
        double total = 0;
        for (Product p : cart) {
            total += p.getPrice();
        }
        return total;
    }
}
