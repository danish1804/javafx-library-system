package com.readingroom.service;

import com.readingroom.model.TopSellingBook;
import com.readingroom.model.Order;
import com.readingroom.model.OrderItem;
import com.readingroom.dao.OrderDAO;
import com.readingroom.dao.BookDAO;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    private OrderDAO orderDAO;
    private BookDAO bookDAO;

    public ReportService(OrderDAO orderDAO, BookDAO bookDAO) {
        this.orderDAO = orderDAO;
        this.bookDAO = bookDAO;
    }

    public String generateSalesReport(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderDAO.getOrdersBetweenDates(startDate, endDate);
        
        double totalSales = orders.stream().mapToDouble(Order::getTotalCost).sum();
        int totalOrders = orders.size();
        double averageOrderValue = totalOrders > 0 ? totalSales / totalOrders : 0;
        
        int totalPhysicalBooks = orders.stream().mapToInt(Order::getTotalPhysicalCopies).sum();
        int totalEbooks = orders.stream().mapToInt(Order::getTotalEbookCopies).sum();

        StringBuilder report = new StringBuilder();
        report.append(String.format("Sales Report from %s to %s\n\n", startDate, endDate));
        report.append(String.format("Total Sales: $%.2f\n", totalSales));
        report.append(String.format("Number of Orders: %d\n", totalOrders));
        report.append(String.format("Average Order Value: $%.2f\n", averageOrderValue));
        report.append(String.format("Total Physical Books Sold: %d\n", totalPhysicalBooks));
        report.append(String.format("Total E-Books Sold: %d\n", totalEbooks));

        // Add more detailed information if needed
        report.append("\nTop 5 Selling Books:\n");
        List<TopSellingBook> topBooks = generateTopSellingBooksReport(5);
        for (TopSellingBook book : topBooks) {
            report.append(book.toString()).append("\n");
        }

        return report.toString();
    }

    public List<TopSellingBook> generateTopSellingBooksReport(int topCount) {
        Map<String, Integer> bookSales = new HashMap<>();
        
        List<Order> allOrders = orderDAO.getAllOrders();
        for (Order order : allOrders) {
            for (OrderItem item : order.getOrderItems()) {
                String bookId = item.getBookId();
                int quantity = item.getPhysicalCopies() + item.getEbookCopies();
                bookSales.merge(bookId, quantity, Integer::sum);
            }
        }

        return bookSales.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(topCount)
            .map(entry -> {
                String bookId = entry.getKey();
                int copiesSold = entry.getValue();
                String bookTitle = bookDAO.getBookById(bookId).getBookTitle();
                String author = bookDAO.getBookById(bookId).getAuthorName();
                return new TopSellingBook(0, bookTitle, author, copiesSold);
            })
            .sorted(Comparator.comparingInt(TopSellingBook::getCopiesSold).reversed())
            .peek(book -> book.setRank(bookSales.size() - bookSales.size() + 1))
            .collect(Collectors.toList());
    }
}