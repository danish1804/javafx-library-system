package application.Java_files.Model;

public class OrderItem {
    private String bookId;
    private String bookTitle;
    private int physicalCopies;
    private int ebookCopies;
    private double physicalPrice;
    private double ebookPrice;

    public OrderItem(String bookId, String bookTitle, int physicalCopies, int ebookCopies, double physicalPrice, double ebookPrice) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.physicalCopies = physicalCopies;
        this.ebookCopies = ebookCopies;
        this.physicalPrice = physicalPrice;
        this.ebookPrice = ebookPrice;
    }

    // Getters
    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getPhysicalCopies() {
        return physicalCopies;
    }

    public int getEbookCopies() {
        return ebookCopies;
    }

    public double getPhysicalPrice() {
        return physicalPrice;
    }

    public double getEbookPrice() {
        return ebookPrice;
    }

    // Setters
    public void setPhysicalCopies(int physicalCopies) {
        this.physicalCopies = physicalCopies;
    }

    public void setEbookCopies(int ebookCopies) {
        this.ebookCopies = ebookCopies;
    }

    // Utility methods
    public int getTotalQuantity() {
        return physicalCopies + ebookCopies;
    }

    public double getTotalCost() {
        return (physicalCopies * physicalPrice) + (ebookCopies * ebookPrice);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "bookId='" + bookId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", physicalCopies=" + physicalCopies +
                ", ebookCopies=" + ebookCopies +
                ", physicalPrice=" + physicalPrice +
                ", ebookPrice=" + ebookPrice +
                '}';
    }
}