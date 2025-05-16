package application.Java_files.Model;

public class SalesReport {
    private String bookID;
    private String title;
    private int totalSold;
    private double totalRevenue;
    private boolean isPhysicalCopy;  // New field to track copy type

    public SalesReport(String bookID, String title, int totalSold, double totalRevenue, boolean isPhysicalCopy) {
        this.bookID = bookID;
        this.title = title;
        this.totalSold = totalSold;
        this.totalRevenue = totalRevenue;
        this.isPhysicalCopy = isPhysicalCopy;
    }

    // Getters and Setters for all fields
    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public boolean isPhysicalCopy() {
        return isPhysicalCopy;
    }
}
