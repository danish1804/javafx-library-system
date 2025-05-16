package application.Java_files.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SQLiteJDBCexample {

    // Path to your SQLite database file
    private static final String DATABASE_URL = "jdbc:sqlite:reading_room.db";

    // Establish connection to the SQLite database
    public static Connection connect() {
        Connection conn = null;
        try {
            // Connect to the SQLite database
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Create a table in the database
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS books (\n"
                + " book_id TEXT PRIMARY KEY,\n"
                + " title TEXT NOT NULL,\n"
                + " author TEXT NOT NULL,\n"
                + " physical_copies INTEGER,\n"
                + " has_ebook BOOLEAN\n"
                + ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            // Execute the SQL statement
            stmt.execute(sql);
            System.out.println("Table 'books' created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert data into the table
    public static void insertBook(String bookId, String title, String author, int physicalCopies, boolean hasEbook) {
        String sql = "INSERT INTO books(book_id, title, author, physical_copies, has_ebook) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookId);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, physicalCopies);
            pstmt.setBoolean(5, hasEbook);
            pstmt.executeUpdate();
            System.out.println("Inserted book: " + title);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieve data from the database
    public static void selectAllBooks() {
        String sql = "SELECT * FROM books";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getString("book_id") + "\t" +
                        rs.getString("title") + "\t" +
                        rs.getString("author") + "\t" +
                        rs.getInt("physical_copies") + "\t" +
                        rs.getBoolean("has_ebook")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Create the books table
        createTable();

        // Insert a book into the table
        insertBook("B001", "Effective Java", "Joshua Bloch", 10, true);
        insertBook("B002", "Clean Code", "Robert C. Martin", 5, true);

        // Query and print all books
        selectAllBooks();
    }
}
