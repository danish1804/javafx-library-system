package com.readingroom.controller;

import com.readingroom.model.Book;
import com.readingroom.service.BookManagementService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateBookStockController {

    @FXML private TextField searchField;
    @FXML private ComboBox<Book> bookComboBox;
    @FXML private TextField physicalCopiesField;
    @FXML private CheckBox ebookAvailableCheckBox;
    @FXML private TextField physicalPriceField;
    @FXML private TextField ebookPriceField;

    private BookManagementService bookService;
    private ObservableList<Book> allBooks;
    private ObservableList<Book> filteredBooks;

    @FXML
    private void initialize() {
        bookComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateFieldsForBook(newSelection);
            }
        });

        // Add listener to searchField for real-time search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch();
        });
    }

    public void setBookService(BookManagementService bookService) {
        this.bookService = bookService;
        loadBooks();
    }

    private void loadBooks() {
        List<Book> books = bookService.getAllBooks();
        allBooks = FXCollections.observableArrayList(books);
        filteredBooks = FXCollections.observableArrayList(allBooks);
        bookComboBox.setItems(filteredBooks);
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            filteredBooks.setAll(allBooks);
        } else {
            List<Book> searchResults = allBooks.stream()
                .filter(book -> book.getBookTitle().toLowerCase().contains(keyword) ||
                                book.getAuthorName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
            filteredBooks.setAll(searchResults);
        }
        bookComboBox.setItems(filteredBooks);
    }

    private void updateFieldsForBook(Book book) {
        physicalCopiesField.setText(String.valueOf(book.getNumberOfPhysicalCopies()));
        ebookAvailableCheckBox.setSelected(book.isHasEbook());
        physicalPriceField.setText(String.format("%.2f", book.getPhysicalPrice()));
        ebookPriceField.setText(String.format("%.2f", book.getEbookPrice()));
    }

    @FXML
    private void handleUpdate() {
        Book selectedBook = bookComboBox.getValue();
        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to update.");
            return;
        }

        try {
            int physicalCopies = Integer.parseInt(physicalCopiesField.getText());
            double physicalPrice = Double.parseDouble(physicalPriceField.getText());
            double ebookPrice = Double.parseDouble(ebookPriceField.getText());

            if (physicalCopies < 0 || physicalPrice < 0 || ebookPrice < 0) {
                throw new IllegalArgumentException("Values cannot be negative.");
            }

            selectedBook.setNumberOfPhysicalCopies(physicalCopies);
            selectedBook.setHasEbook(ebookAvailableCheckBox.isSelected());
            selectedBook.setPhysicalPrice(physicalPrice);
            selectedBook.setEbookPrice(ebookPrice);

            boolean updated = bookService.updateBook(selectedBook);
            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book stock and prices updated successfully.");
                loadBooks(); // Refresh the book list
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update book stock and prices.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numbers for copies and prices.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) bookComboBox.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}