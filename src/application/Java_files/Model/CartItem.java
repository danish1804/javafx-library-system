// File path: src/main/java/com/readingroom/model/CartItem.java
package com.readingroom.model;

public class CartItem {

    private Book book;
    private int physicalCopies;
    private int ebookCopies;

    // Constructor to initialize the CartItem
    public CartItem(Book book, int quantity, boolean isEbook) {
        this.book = book;
        if (isEbook) {
            this.ebookCopies = quantity;
            this.physicalCopies = 0;
        } else {
            this.physicalCopies = quantity;
            this.ebookCopies = 0;
        }
    }

    // Method to calculate the total cost of the cart item
    public double getTotalCost() {
        return (this.physicalCopies * book.getPrice(false)) + (this.ebookCopies * book.getPrice(true));
    }

    // Getter for book ID
    public String getBookID() {
        return book.getBookID();
    }

    // Getter for the Book object
    public Book getBook() {
        return this.book;
    }

    // Getter for the book title
    public String getBookTitle() {
        return book.getBookTitle();
    }

    // Add physical copies to the cart item
    public void addPhysicalCopies(int amount) {
        if (amount > 0) {
            physicalCopies += amount;
        }
    }

    // Remove physical copies from the cart item, ensuring non-negative result
    public void removePhysicalCopies(int amount) {
        if (physicalCopies >= amount) {
            physicalCopies -= amount;
        } else {
            throw new IllegalArgumentException("Not enough physical copies to remove.");
        }
    }

    // Add ebook copies to the cart item
    public void addEbookCopies(int amount) {
        if (amount > 0) {
            ebookCopies += amount;
        }
    }

    // Remove ebook copies from the cart item, ensuring non-negative result
    public void removeEbookCopies(int amount) {
        if (ebookCopies >= amount) {
            ebookCopies -= amount;
        } else {
            throw new IllegalArgumentException("Not enough ebook copies to remove.");
        }
    }

    // Getter for the number of physical copies
    public int getPhysicalCopies() {
        return this.physicalCopies;
    }

    // Getter for the number of ebook copies
    public int getEbookCopies() {
        return this.ebookCopies;
    }
}
