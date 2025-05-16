# 📚 JavaFX Library Management System

This project is a fully-functional desktop-based library and bookstore management system built using JavaFX. It follows the Model-View-Controller (MVC) design pattern and is structured to handle both customer-facing and administrative tasks within a unified graphical user interface.

The system simulates the key operations of a digital library or bookstore — from browsing and purchasing books to managing stock and monitoring customer activity. It includes a persistent backend implemented via JDBC and a relational database (SQLite or MySQL), allowing for secure storage and retrieval of data.

---

## 🎯 Project Objectives

- Implement a real-world JavaFX application demonstrating clean architecture and modular design
- Practice object-oriented programming, encapsulation, inheritance, and the Singleton design pattern
- Build a responsive, event-driven UI using JavaFX and FXML
- Establish data persistence using JDBC for database integration
- Simulate user authentication and role-based access control

---

## 💡 Key Functionalities

### 👤 **User Roles**
- **Customer**
  - Register and log in to their account
  - Browse available books (by title, author, genre, or availability)
  - Add books to their shopping cart
  - Checkout and place orders
  - View current and past orders

- **Admin**
  - Log in with admin credentials
  - Add new books to the library
  - Edit or remove existing book records
  - View all customer orders
  - Track inventory status

---

### 🗃️ **Data Persistence**

- Uses **JDBC** to connect to a **relational database**
- Supports both **SQLite** (file-based) and **MySQL** (server-based) with minimal configuration changes
- Handles book data, user credentials, cart items, and order history
- Implements DAO (Data Access Object) pattern for clean and reusable database access logic

---

### 🧱 **System Architecture**

- **Model Layer**: Classes like `Book`, `CartItem`, `Order`, and `User` encapsulate core data and logic
- **View Layer**: Built using FXML with styled UI components and scenes for login, book browsing, cart, checkout, and admin dashboard
- **Controller Layer**: Handles user input, UI events, and navigation between views (e.g., `LoginController`, `AdminController`, `CartController`)

- **Design Patterns Used**:
  - **Singleton**: For managing database connections
  - **MVC**: For separation of concerns
  - **DAO**: For interacting with the database

---

## 🛠️ Technologies Used

- **Java 17+**
- **JavaFX 19+**
- **FXML** (for GUI layout)
- **JDBC** (for database access)
- **SQLite** or **MySQL** (for data storage)
- **Scene Builder** (optional for visual FXML editing)

---

## ▶️ How to Run the Project

### 🧩 Prerequisites

- Java 17+ installed and configured
- JavaFX SDK installed (JavaFX 17+ or 19 recommended)
- An IDE like **IntelliJ IDEA** or **VS Code**
- (Optional) Scene Builder for FXML design
- Database file (`library.db`) or MySQL schema

### ⚙️ Run Instructions

1. Open the project in IntelliJ or VS Code.
2. Configure VM options for JavaFX
3. Ensure your database (e.g., `library.db`) is located in the correct directory or configure the DB URL accordingly in your DAO/DBConnector class.
4. Run the `Main.java` class to launch the application.
5. Use default test accounts (e.g., `admin/admin123`) or register a new user.

---

## 📸 Optional Features & Extensions

- Search bar with live filtering
- PDF invoice generation on checkout
- Image upload for book covers
- Logging system for tracking admin actions
- Role-based redirection after login


---

## 📜 License

This project is licensed under the [MIT License](./LICENSE).

You are free to use, modify, and distribute this project with attribution.
