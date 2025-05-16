package application.Java_files.Model;
import java.util.List;
import java.time.LocalDate;

public class admin extends user {

    // Constructor to initialize Admin with userId, name, email, and password
    public admin(String username, String password, String firstName, String lastName, LocalDate dob) {
        super(username, password, firstName, lastName, dob);  // Call the User class constructor
    }

//    / Admin-specific functionality: Adding a new book to the stock
    public void addNewBookToStock(String title, String author, int physicalCopies, boolean hasEbook, double physicalPrice, double ebookPrice) {
        Book newBook = new Book(title, author, physicalCopies, hasEbook, physicalPrice, ebookPrice);
        System.out.println("Admin " + getFirstName() + " added a new book: " + title + " by " + author);
    }

 // Admin-specific functionality: Updating stock of an existing book
    public void updateBookStock(Book book, int newStock) {
        System.out.println("Admin " + getFirstName() + " is updating stock for book: " + book.getBookTitle());
        book.increasePhysicalCopies(newStock);
        System.out.println("New stock for " + book.getBookTitle() + " is now " + book.getNumberOfPhysicalCopies() + " copies.");
    }

 // Admin-specific functionality: Viewing book stock
    public void viewBookStock(List<Book> books) {
        System.out.println("Admin " + getFirstName() + " is viewing book stock:");
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

 // Admin-specific functionality: Viewing past orders
    public void viewPastOrders(List<Order> orders) {
        System.out.println("Admin " + getFirstName() + " is viewing past orders:");
        for (Order order : orders) {
            System.out.println(order.toString());
        }
    }

 // Admin-specific functionality: Generating reports
    public void generateReports(List<Order> orders) {
        System.out.println("Admin " + getFirstName() + " is generating reports for all orders:");
        double totalRevenue = 0.0;
        int totalBooksSold = 0;

        for (Order order : orders) {
            totalRevenue += order.getTotalCost();
            totalBooksSold += order.getTotalItems();
        }

        System.out.println("Total Revenue: $" + String.format("%.2f", totalRevenue));
        System.out.println("Total Books Sold: " + totalBooksSold);
    }
    
    
    // Admin-specific functionality: Viewing the admin dashboard
    public void adminDashboard() {
        System.out.println("Welcome to the Admin Dashboard, " + getName() + "!");
        // Display dashboard options here, e.g., managing inventory, generating reports, etc.
    }

    // Admin-specific functionality: Registering new admins
    public static admin registerNewAdmin(String userId, String name, String email, String password) {
        // You could implement additional validation or security measures here
        System.out.println("Admin " + name + " has been registered.");
        return new admin(userId, name, email, password);
    }

    // Additional admin-specific methods can go here...
}
