# E-Commerce Platform System

A simple Java-based shopping system built as a beginner-level academic project. This project demonstrates the use of core Java concepts like data structures, multithreading, Swing GUI, and JDBC database connectivity.

---

## 📋 Description

This is a basic e-commerce application where:
- Products are stored and managed using **Hashtable**
- Shopping cart supports **undo** functionality using **Stack**
- Order history is maintained using **Vector**
- Payment processing is simulated using **Multithreading** (Runnable + Thread.sleep)
- All data is connected to an **Oracle Database** via JDBC

The project is designed to be easy to understand, document, and explain in a viva/lab exam.

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Java Swing | GUI (JFrame, JButton, setBounds layout) |
| Oracle JDBC | Database connectivity |
| Hashtable | Product storage |
| Stack | Cart with undo feature |
| Vector | Order history |
| Multithreading | Payment simulation (Runnable) |

---

## ✨ Features

- **Product Management** — Add and view products (stored in Hashtable + Oracle DB)
- **Shopping Cart with Undo** — Add items to cart, undo last add using Stack
- **Order History** — Place orders and view history using Vector
- **Payment Simulation** — Process payments using a separate Thread
- **Database Integration** — Products and orders are saved to Oracle Database
- **Static Variable** — Tracks total number of products created

---

## 📁 Project Structure

```
ECommercePlatform/
├── src/
│   └── ecommerce/
│       ├── MainApp.java          ← Entry point (main method)
│       ├── MainFrame.java        ← GUI with all buttons
│       ├── Product.java          ← Product model + static variable
│       ├── DBConnection.java     ← JDBC Oracle connection
│       ├── CartManager.java      ← Stack-based cart
│       ├── OrderManager.java     ← Vector-based order history
│       └── PaymentThread.java    ← Runnable payment simulation
├── sql/
│   └── schema.sql                ← Oracle DB table creation script
└── bin/                          ← Compiled .class files (not in repo)
```

---

## 🗄️ Database Setup

1. Open **Oracle SQL Developer**
2. Connect to your Oracle Database
3. Run `sql/schema.sql` to create tables and sample data
4. Update connection details in `DBConnection.java` if needed:
   - URL: `jdbc:oracle:thin:@localhost:1521/orclpdb`
   - Username: `ecommerce`
   - Password: `ecommerce123`

---

## 🚀 How to Run

### Compile
```bash
javac -cp path/to/ojdbc11.jar -d bin src/ecommerce/*.java
```

### Run
```bash
java -cp "bin;path/to/ojdbc11.jar" ecommerce.MainApp
```

---

## 📸 GUI Buttons

| Button | Action |
|---|---|
| Add Product | Add a new product to the system |
| View Products | Display all products |
| Add to Cart | Add a product to the shopping cart |
| Undo Last Add | Remove last added item from cart |
| Place Order | Place order for all cart items |
| View Orders | View order history |
| Process Payment | Simulate payment processing |
| Exit | Close the application |

---

## 👨‍🎓 Java Concepts Demonstrated

- **Hashtable** → Product storage and retrieval
- **Stack** → Cart with push/pop (undo feature)
- **Vector** → Order history storage
- **Multithreading** → Payment processing using Runnable interface
- **Static Variable** → `totalProducts` counter in Product class
- **String Methods** → `String.format()` for formatted output
- **JDBC** → Oracle database connectivity
- **Swing** → GUI with JFrame, JButton, ActionListener

---

## 📝 License

This project is created for academic/educational purposes.
