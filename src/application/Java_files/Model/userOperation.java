package application.Java_files.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class userOperation {

    private static final String DB_URL = "jdbc:sqlite:readingroom.db";

    // Authenticate user by username and password
    public static user authenticateUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                LocalDate dob = rs.getDate("dob").toLocalDate();
                return new user(username, password, firstName, lastName, dob);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if authentication fails
    }
    
  //Register a new user
  public static boolean registerUser(user user) {
      String query = "INSERT INTO Users (username, password, firstName, lastName, dob) VALUES (?, ?, ?, ?, ?)";

      try (Connection conn = DriverManager.getConnection(DB_URL);
           PreparedStatement pstmt = conn.prepareStatement(query)) {

          pstmt.setString(1, user.getUsername());
          pstmt.setString(2, user.getPassword());
          pstmt.setString(3, user.getFirstName());
          pstmt.setString(4, user.getLastName());
          pstmt.setDate(5, java.sql.Date.valueOf(user.getDob()));

          int rowsAffected = pstmt.executeUpdate();
          return rowsAffected > 0;

      } catch (SQLException e) {
          e.printStackTrace();
          return false;
      }
  }
  
  
public List<order> getUserOrders(int userID) {
List<order> orders = new ArrayList<>();
String query = "SELECT orderID, customerName, orderDate, totalItems, totalPrice, physicalCopiesOrdered, ebookCopiesOrdered " +
               "FROM Orders WHERE userID = ?";

try (Connection conn = DriverManager.getConnection(DB_URL);
     PreparedStatement pstmt = conn.prepareStatement(query)) {

    // Set the userID as a parameter in the SQL query
    pstmt.setInt(1, userID);

    // Execute the query and get the result set
    ResultSet rs = pstmt.executeQuery();

    // Loop through the result set and create Order objects
    while (rs.next()) {
        String orderID = rs.getString("orderID");
        String customerName = rs.getString("customerName");
        LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
        int totalItems = rs.getInt("totalItems");
        double totalPrice = rs.getDouble("totalPrice");
        int physicalCopiesOrdered = rs.getInt("physicalCopiesOrdered");
        int ebookCopiesOrdered = rs.getInt("ebookCopiesOrdered");

        // Create an Order object and add it to the list
        order order = new order(orderID, customerName, orderDate, totalItems, totalPrice, physicalCopiesOrdered, ebookCopiesOrdered);
        orders.add(order);
    }

} catch (SQLException e) {
    e.printStackTrace();
}

return orders; // Return the list of orders for the specific user
}

// Fetch top 5 popular books from the database
public static List<Book> getTopBooks() {
    List<Book> topBooks = new ArrayList<>();
    String query = "SELECT title, author, SUM(quantity) AS soldCopies FROM Orders " +
                   "GROUP BY bookID ORDER BY soldCopies DESC LIMIT 5";

    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            String title = rs.getString("title");
            String author = rs.getString("author");
            int soldCopies = rs.getInt("soldCopies");

            Book book = new Book(title, author, soldCopies);
            topBooks.add(book);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return topBooks;
}

//Updated ReadingRoom class with the correct update profile method
public static boolean updateUserProfile(int userID, String firstName, String lastName, String username, LocalDate dob) {
 String query = "UPDATE Users SET firstName = ?, lastName = ?, username = ?, dob = ? WHERE id = ?";

 try (Connection conn = DriverManager.getConnection(DB_URL);
      PreparedStatement pstmt = conn.prepareStatement(query)) {

     pstmt.setString(1, firstName);
     pstmt.setString(2, lastName);
     pstmt.setString(3, username);
     pstmt.setDate(4, java.sql.Date.valueOf(dob)); // Convert LocalDate to SQL Date
     pstmt.setInt(5, userID);

     int rowsAffected = pstmt.executeUpdate();
     return rowsAffected > 0;

 } catch (SQLException e) {
     e.printStackTrace();
     return false;
 }
 
}

}
 
 

