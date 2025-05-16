// File path: src/main/java/com/readingroom/model/Book.java
package com.readingroom.model;

import java.util.Random;

public class Book {
    // Attributes of the Book class
    private String bookID;               // Unique identifier for the book
    private String bookTitle;            // Title of the book
    private String authorName;           // Name of the author
    private int numberOfPhysicalCopies;  // Number of physical copies available
    private boolean hasEbook;            // Ebook availability (true or false)
    private boolean hasPhysicalCopy;     // Indicates if physical copies are available
    private double physicalPrice;        // Price of the physical copy
    private double ebookPrice;           // Price of the ebook

    private static final Random random = new Random();  // Random number generator for bookID

    // Constructor
    public Book(String bookTitle, String authorName, int numberOfPhysicalCopies, boolean hasEbook, double physicalPrice, double ebookPrice) {
        this.bookID = generateBookID(authorName);
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.numberOfPhysicalCopies = numberOfPhysicalCopies;
        this.hasEbook = hasEbook;
        this.hasPhysicalCopy = (numberOfPhysicalCopies > 0);
        this.physicalPrice = physicalPrice;
        this.ebookPrice = ebookPrice;
    }

    // Getters and Setters
    public String getBookID() {
        return bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getNumberOfPhysicalCopies() {
        return numberOfPhysicalCopies;
    }

    public void setNumberOfPhysicalCopies(int numberOfPhysicalCopies) {
        this.numberOfPhysicalCopies = numberOfPhysicalCopies;
        this.hasPhysicalCopy = (numberOfPhysicalCopies > 0);
    }

    public boolean isHasEbook() {
        return hasEbook;
    }

    public void setHasEbook(boolean hasEbook) {
        this.hasEbook = hasEbook;
    }

    public double getPhysicalPrice() {
        return physicalPrice;
    }

    public void setPhysicalPrice(double physicalPrice) {
        this.physicalPrice = physicalPrice;
    }

    public double getEbookPrice() {
        return ebookPrice;
    }

    public void setEbookPrice(double ebookPrice) {
        this.ebookPrice = ebookPrice;
    }

    // Private methods for generating book ID and getting author initials
    private String generateBookID(String authorName) {
        String initials = getAuthorInitials(authorName);
        int randomNumber = random.nextInt(1000000); // Generates a number between 0 and 999999
        return String.format("%s-%06d", initials, randomNumber);
    }

    private String getAuthorInitials(String authorName) {
        StringBuilder initials = new StringBuilder();
        for (String part : authorName.split(" ")) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }
        return initials.toString().toUpperCase();
    }

    // Business Logic Methods
    public void decreasePhysicalCopies(int amount) {
        if (amount > numberOfPhysicalCopies) {
            throw new IllegalArgumentException("Not enough physical copies available to remove.");
        }
        numberOfPhysicalCopies -= amount;
        if (numberOfPhysicalCopies <= 0) {
            hasPhysicalCopy = false;
        }
    }

    public void increasePhysicalCopies(int amount) {
        numberOfPhysicalCopies += amount;
        hasPhysicalCopy = true;
    }

    public double getPrice(boolean isEbook) {
        return isEbook ? ebookPrice : physicalPrice;
    }

    public boolean hasPhysicalCopy() {
        return hasPhysicalCopy;
    }
}
