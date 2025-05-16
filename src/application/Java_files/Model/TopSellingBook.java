package com.readingroom.model;

public class TopSellingBook {
    private int rank;
    private String bookTitle;
    private String author;
    private int copiesSold;

    public TopSellingBook(int rank, String bookTitle, String author, int copiesSold) {
        this.rank = rank;
        this.bookTitle = bookTitle;
        this.author = author;
        this.copiesSold = copiesSold;
    }

    // Getters
    public int getRank() { return rank; }
    public String getBookTitle() { return bookTitle; }
    public String getAuthor() { return author; }
    public int getCopiesSold() { return copiesSold; }

    // Setters
    public void setRank(int rank) { this.rank = rank; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public void setAuthor(String author) { this.author = author; }
    public void setCopiesSold(int copiesSold) { this.copiesSold = copiesSold; }

    @Override
    public String toString() {
        return String.format("Rank: %d, Title: %s, Author: %s, Copies Sold: %d", rank, bookTitle, author, copiesSold);
    }
}