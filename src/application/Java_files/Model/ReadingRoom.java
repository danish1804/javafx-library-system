package application.Java_files.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// The ReadingRoom class manages the list of books and provides functionality for listing and searching books.
public class ReadingRoom {

    private static List<Book> books = new ArrayList<>();

    // Static initializer block to add sample books when the class is loaded
    static {
        initializeBooks();
    }

    // Add some sample books to the list
    private static void initializeBooks() {
        System.out.println("Initializing the Reading Room with sample books...");
        books.add(new Book("Absolute Java", "Savitch", 5, true, 50, 8));
        books.add(new Book("JAVA: How to Program", "Deitel and Deitel", 0, true, 50, 8));
        books.add(new Book("Computing Concepts with JAVA 8 Essentials", "Horstman", 5, false,50 , 8));
        books.add(new Book("Java Software Solutions", "Lewis and Loftus", 5, false, 50, 8));
        books.add(new Book("Java Program Design", "Cohoon and Davidson", 1, true, 50, 8));
    }

    // Method to list all books available in the Reading Room
    public static void listAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the Reading Room.");
            return;
        }
        System.out.println("===============================================");
        System.out.println("List of books available in the Reading Room....");
        System.out.println("===============================================");
        System.out.printf("%-20s %-45s %-20s %-15s %-10s%n", "BookID", "Title", "Author", "Physical copies", "Ebook");
        System.out.println("================================================================================");

        // Loop through each book in the list and print its details
        for (Book book : books) {
            book.printBook();
        }
        System.out.println(); // Print a blank line for better readability
    }

    // Method to get all books
    public static List<Book> getAllBooks() {
        return new ArrayList<>(books); // Return a copy of the books list to prevent external modifications
    }

    // Method to find a book by its ID
    public static Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getBookID().equals(bookId)) {
                return book;
            }
        }
        return null; // Return null if no book with the given ID is found
    }

    // Method to display the book details by its ID
    public static void displayBookById(String bookId) {
        Book book = findBookById(bookId);
        if (book != null) {
            System.out.println("Book found:");
            System.out.printf("%-20s %-45s %-20s %-15s %-10s%n", "BookID", "Title", "Author", "Physical copies", "Ebook");
            System.out.println("================================================================================");
            book.printBook();
        } else {
            System.out.println("No book found with ID: " + bookId);
        }
    }

    // Method to search for books by a keyword entered by the user
    public static void searchForBooks() {
       
        if (books.isEmpty()) {
            System.out.println("No books available in the Reading Room to search.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please Enter a Keyword: ");
        String keyword = scanner.nextLine().toLowerCase(); // Convert the keyword to lowercase for case-insensitive search

        System.out.println("Searching for books with keyword: " + keyword);
        boolean found = false; // Flag to check if any books were found
        System.out.printf("%-20s %-45s %-20s %-15s %-10s%n", "BookID", "Title", "Author", "Physical copies", "Ebook");
        System.out.println("================================================================================");

        // Loop through each book to check if the title contains the keyword
        for (Book book : books) {
            if (book.getBookTitle().toLowerCase().contains(keyword)) {
                book.printBook(); // Print details of the found book
                found = true; // Set the flag to true if a book is found
            }
        }

        // If no books were found, prompt the user for another search
        if (!found) {
            System.out.println("No books found with the keyword: " + keyword);
        }

        System.out.print("Do you want to search again? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            searchForBooks(); // Call the method again for another search
        }
        scanner.close();
    }

    // Method to add a new book to the list
    public void addBook(String title, String author, int physicalCopies, boolean hasEbook, double physicalPrice, double ebookPrice) {
        Book newBook = new Book(title, author, physicalCopies, hasEbook, physicalPrice, ebookPrice);
        books.add(newBook);
        System.out.println("Book '" + title + "' by " + author + " has been added to the Reading Room.");
    }

    // Main method for testing
    public static void main(String[] args) {
        listAllBooks(); // List all available books

        searchForBooks(); // Allow user to search for books
    }
}

//package application.Java_files.Model;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReadingRoom {
//
//    private static final String DB_URL = "jdbc:sqlite:readingroom.db";
//
//    // Fetch all books from the database
//    public static List<Book> getAllBooks() {
//        List<Book> books = new ArrayList<>();
//        String query = "SELECT * FROM Books";
//        
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//             
//            while (rs.next()) {
//                String bookID = rs.getString("bookID");
//                String title = rs.getString("title");
//                String author = rs.getString("author");
//                int physicalCopies = rs.getInt("physicalCopies");
//                double physicalPrice = rs.getDouble("physicalPrice");
//                double ebookPrice = rs.getDouble("ebookPrice");
//
//                Book book = new Book(bookID, title, author, physicalCopies, physicalPrice, ebookPrice);
//                books.add(book);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        
//        return books;
//    }
// Fetch all orders from the database
//public static List<Order> getAllOrders() {
//    List<Order> orders = new ArrayList<>();
//    String query = "SELECT orderID, customerName, orderDate, totalItems, totalPrice, physicalCopiesOrdered, ebookCopiesOrdered FROM Orders";
//
//    try (Connection conn = DriverManager.getConnection(DB_URL);
//         Statement stmt = conn.createStatement();
//         ResultSet rs = stmt.executeQuery(query)) {
//
//        while (rs.next()) {
//            String orderID = rs.getString("orderID");
//            String customerName = rs.getString("customerName");
//            LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
//            int totalItems = rs.getInt("totalItems");
//            double totalPrice = rs.getDouble("totalPrice");
//            int physicalCopiesOrdered = rs.getInt("physicalCopiesOrdered");
//            int ebookCopiesOrdered = rs.getInt("ebookCopiesOrdered");
//
//            Order order = new Order(orderID, customerName, orderDate, totalItems, totalPrice, physicalCopiesOrdered, ebookCopiesOrdered);
//            orders.add(order);
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return orders;
//}
//// Method to get all orders for a specific user based on userID

//}


//    // Add a new book to the stock
//    public static void addNewBook(String title, String author, int stock, double price) {
//        String query = "INSERT INTO Books (title, author, physicalCopies, physicalPrice) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setString(1, title);
//            pstmt.setString(2, author);
//            pstmt.setInt(3, stock);
//            pstmt.setDouble(4, price);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Update the stock of a book in the database
//    public static void updateBookStock(String bookID, int newStock) {
//        String query = "UPDATE Books SET physicalCopies = ? WHERE bookID = ?";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setInt(1, newStock);
//            pstmt.setString(2, bookID);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Update the price of a book in the database
//    public static void updateBookPrice(String bookID, double newPrice) {
//        String query = "UPDATE Books SET physicalPrice = ? WHERE bookID = ?";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setDouble(1, newPrice);
//            pstmt.setString(2, bookID);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Remove a book from the database
//    public static void removeBook(String bookID) {
//        String query = "DELETE FROM Books WHERE bookID = ?";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            pstmt.setString(1, bookID);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    
// // Add a new book to the stock
//    public static void addNewBook(String title, String author, int stock, double physicalPrice, double ebookPrice) {
//        // Create a new Book object, which generates the bookID automatically
//        Book newBook = new Book(title, author, stock, true, physicalPrice, ebookPrice);
//        String query = "INSERT INTO Books (bookID, title, author, physicalCopies, physicalPrice, ebookPrice) VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            // Set the values from the Book object
//            pstmt.setString(1, newBook.getBookID());  // Generated bookID
//            pstmt.setString(2, title);
//            pstmt.setString(3, author);
//            pstmt.setInt(4, stock);
//            pstmt.setDouble(5, physicalPrice);
//            pstmt.setDouble(6, ebookPrice);
//            pstmt.executeUpdate();
//
//            System.out.println("New book added with ID: " + newBook.getBookID());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//Fetch sales by date range with copy type filter
// Sales by Date Range
//public static List<SalesReport> getSalesByDateRange(LocalDate startDate, LocalDate endDate, String copyType) {
//    List<SalesReport> salesReports = new ArrayList<>();
//    String query = "SELECT bookID, title, SUM(quantity) AS totalSold, SUM(price) AS totalRevenue " +
//                   "FROM Sales WHERE saleDate BETWEEN ? AND ?";
//
//    if (!"All".equals(copyType)) {
//        query += " AND copyType = ?";
//    }
//
//    query += " GROUP BY bookID";
//
//    try (Connection conn = DriverManager.getConnection(DB_URL);
//         PreparedStatement pstmt = conn.prepareStatement(query)) {
//        pstmt.setDate(1, Date.valueOf(startDate));
//        pstmt.setDate(2, Date.valueOf(endDate));
//
//        if (!"All".equals(copyType)) {
//            pstmt.setString(3, copyType.equals("Physical Copies") ? "physical" : "ebook");
//        }
//
//        ResultSet rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            String bookID = rs.getString("bookID");
//            String title = rs.getString("title");
//            int totalSold = rs.getInt("totalSold");
//            double totalRevenue = rs.getDouble("totalRevenue");
//
//            salesReports.add(new SalesReport(bookID, title, totalSold, totalRevenue));
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return salesReports;
//}
//
//// Sales by Book
//public static List<SalesReport> getSalesByBook(String filter, String copyType) {
//    List<SalesReport> salesReports = new ArrayList<>();
//    String query = "SELECT bookID, title, SUM(quantity) AS totalSold, SUM(price) AS totalRevenue " +
//                   "FROM Sales WHERE title LIKE ?";
//
//    if (!"All".equals(copyType)) {
//        query += " AND copyType = ?";
//    }
//
//    query += " GROUP BY bookID";
//
//    try (Connection conn = DriverManager.getConnection(DB_URL);
//         PreparedStatement pstmt = conn.prepareStatement(query)) {
//        pstmt.setString(1, "%" + filter + "%");
//
//        if (!"All".equals(copyType)) {
//            pstmt.setString(2, copyType.equals("Physical Copies") ? "physical" : "ebook");
//        }
//
//        ResultSet rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            String bookID = rs.getString("bookID");
//            String title = rs.getString("title");
//            int totalSold = rs.getInt("totalSold");
//            double totalRevenue = rs.getDouble("totalRevenue");
//
//            salesReports.add(new SalesReport(bookID, title, totalSold, totalRevenue));
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return salesReports;
//}
//
//// Sales by Author
//public static List<SalesReport> getSalesByAuthor(String filter, String copyType) {
//    List<SalesReport> salesReports = new ArrayList<>();
//    String query = "SELECT bookID, title, SUM(quantity) AS totalSold, SUM(price) AS totalRevenue " +
//                   "FROM Sales WHERE author LIKE ?";
//
//    if (!"All".equals(copyType)) {
//        query += " AND copyType = ?";
//    }
//
//    query += " GROUP BY bookID";
//
//    try (Connection conn = DriverManager.getConnection(DB_URL);
//         PreparedStatement pstmt = conn.prepareStatement(query)) {
//        pstmt.setString(1, "%" + filter + "%");
//
//        if (!"All".equals(copyType)) {
//            pstmt.setString(2, copyType.equals("Physical Copies") ? "physical" : "ebook");
//        }
//
//        ResultSet rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            String bookID = rs.getString("bookID");
//            String title = rs.getString("title");
//            int totalSold = rs.getInt("totalSold");
//            double totalRevenue = rs.getDouble("totalRevenue");
//
//            salesReports.add(new SalesReport(bookID, title, totalSold, totalRevenue));
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return salesReports;
//}
//
//// Sales by Category
//public static List<SalesReport> getSalesByCategory(String filter, String copyType) {
//    List<SalesReport> salesReports = new ArrayList<>();
//    String query = "SELECT bookID, title, SUM(quantity) AS totalSold, SUM(price) AS totalRevenue " +
//                   "FROM Sales WHERE category LIKE ?";
//
//    if (!"All".equals(copyType)) {
//        query += " AND copyType = ?";
//    }
//
//    query += " GROUP BY bookID";
//
//    try (Connection conn = DriverManager.getConnection(DB_URL);
//         PreparedStatement pstmt = conn.prepareStatement(query)) {
//        pstmt.setString(1, "%" + filter + "%");
//
//        if (!"All".equals(copyType)) {
//            pstmt.setString(2, copyType.equals("Physical Copies") ? "physical" : "ebook");
//        }
//
//        ResultSet rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            String bookID = rs.getString("bookID");
//            String title = rs.getString("title");
//            int totalSold = rs.getInt("totalSold");
//            double totalRevenue = rs.getDouble("totalRevenue");
//
//            salesReports.add(new SalesReport(bookID, title, totalSold, totalRevenue));
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return salesReports;
//}
//
//// Top-Selling Books
//public static List<SalesReport> getTopSellingBooks(String copyType) {
//    List<SalesReport> salesReports = new ArrayList<>();
//    String query = "SELECT bookID, title, SUM(quantity) AS totalSold, SUM(price) AS totalRevenue " +
//                   "FROM Sales";
//
//    if (!"All".equals(copyType)) {
//        query += " WHERE copyType = ?";
//    }
//
//    query += " GROUP BY bookID ORDER BY totalSold DESC LIMIT 10";
//
//    try (Connection conn = DriverManager.getConnection(DB_URL);
//         PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//        if (!"All".equals(copyType)) {
//            pstmt.setString(1, copyType.equals("Physical Copies") ? "physical" : "ebook");
//        }
//
//        ResultSet rs = pstmt.executeQuery();
//
//        while (rs.next()) {
//            String bookID = rs.getString("bookID");
//            String title = rs.getString("title");
//            int totalSold = rs.getInt("totalSold");
//            double totalRevenue = rs.getDouble("totalRevenue");
//
//            salesReports.add(new SalesReport(bookID, title, totalSold, totalRevenue));
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return salesReports;
//}
//
//}
