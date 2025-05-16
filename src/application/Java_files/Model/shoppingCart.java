package application.Java_files.Model;

import java.util.ArrayList;
import java.util.List;

public class shoppingCart {
    private List<OrderItem> items;

    public shoppingCart() {
        this.items = new ArrayList<>();
    }

    public boolean addPhysicalBook(Book book, int quantity) {
        return addItem(book, quantity, 0);
    }

    public boolean addEbook(Book book, int quantity) {
        return addItem(book, 0, quantity);
    }

    private boolean addItem(Book book, int physicalCopies, int ebookCopies) {
        if (physicalCopies < 0 || ebookCopies < 0) {
            return false;
        }

        OrderItem existingItem = findItem(book.getBookID());
        if (existingItem != null) {
            existingItem.setPhysicalCopies(existingItem.getPhysicalCopies() + physicalCopies);
            existingItem.setEbookCopies(existingItem.getEbookCopies() + ebookCopies);
        } else {
            items.add(new OrderItem(book.getBookID(), book.getBookTitle(), physicalCopies, ebookCopies, 
                                    book.getPrice(false), book.getPrice(true)));
        }
        return true;
    }
    public void removeItem(String bookId) {
        items.removeIf(item -> item.getBookId().equals(bookId));
    }

    public void updateItemQuantity(String bookId, int physicalCopies, int ebookCopies) {
        OrderItem item = findItem(bookId);
        if (item != null) {
            item.setPhysicalCopies(physicalCopies);
            item.setEbookCopies(ebookCopies);
        }
    }

    private OrderItem findItem(String bookId) {
        return items.stream()
                    .filter(item -> item.getBookId().equals(bookId))
                    .findFirst()
                    .orElse(null);
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotalCost() {
        return items.stream().mapToDouble(OrderItem::getTotalCost).sum();
    }

    public Order createOrder(String customerId, String shippingAddress, String paymentMethod) {
        return new Order(generateOrderId(), customerId, shippingAddress, paymentMethod, 
                         this, java.time.LocalDateTime.now());
    }

    private String generateOrderId() {
        // Implement order ID generation logic
        return "ORD-" + System.currentTimeMillis();
    }

    public void clear() {
        items.clear();
    }
}