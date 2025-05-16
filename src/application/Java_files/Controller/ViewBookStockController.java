package com.readingroom.controller;

import com.readingroom.model.Book;
import com.readingroom.service.BookManagementService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ViewBookStockController {

    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> bookIdColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> physicalCopiesColumn;
    @FXML private TableColumn<Book, Boolean> ebookAvailableColumn;
    @FXML private TableColumn<Book, Double> physicalPriceColumn;
    @FXML private TableColumn<Book, Double> ebookPriceColumn;
    @FXML private Button addBookButton;
    @FXML private Button editBookButton;
    @FXML private Button deleteBookButton;
    @FXML private TextField searchField;

    private BookManagementService bookService;
    private ObservableList<Book> allBooks;
    private ObservableList<Book> filteredBooks;

    @FXML
    private void initialize() {
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPhysicalCopies"));
        ebookAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("hasEbook"));
        physicalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("physicalPrice"));
        ebookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ebookPrice"));

        // Set up button actions
        addBookButton.setOnAction(event -> handleAddBook());
        editBookButton.setOnAction(event -> handleEditBook());
        deleteBookButton.setOnAction(event -> handleDeleteBook());

        // Disable edit and delete buttons until a book is selected
        editBookButton.setDisable(true);
        deleteBookButton.setDisable(true);
        
        filteredBooks = FXCollections.observableArrayList();

        // Set up search field listener
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch();
        });
        // Enable edit and delete buttons when a book is selected
        bookTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            editBookButton.setDisable(newSelection == null);
            deleteBookButton.setDisable(newSelection == null);
        });
    }

    public void setBookService(BookManagementService bookService) {
        this.bookService = bookService;
        loadBooks();
    }

    private void loadBooks() {
        List<Book> books = bookService.getAllBooks();
        bookList = FXCollections.observableArrayList(books);
        filteredBooks.setAll(allBooks);
        bookTableView.setItems(bookList);
    }

    @FXML
    private void handleRefresh() {
        loadBooks();
        searchField.clear();
    }
    
    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            filteredBooks.setAll(allBooks);
        } else {
            List<Book> searchResults = allBooks.stream()
                .filter(book -> book.getBookTitle().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
            filteredBooks.setAll(searchResults);
        }

        if (filteredBooks.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Search Results", "No books found with the keyword: " + keyword);
        }
    }

    @FXML
    private void handleAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/AddBookView.fxml"));
            Parent root = loader.load();
            AddBookController controller = loader.getController();
            controller.setBookService(bookService);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Book");
            stage.showAndWait();
            
            // Refresh the book list after adding
            loadBooks();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open add book window.");
        }
    }

    @FXML
    private void handleEditBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/EditBookView.fxml"));
            Parent root = loader.load();
            EditBookController controller = loader.getController();
            controller.setBookService(bookService);
            controller.setBook(selectedBook);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit Book");
            stage.showAndWait();
            
            // Refresh the book list after editing
            loadBooks();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open edit book window.");
        }
    }

    @FXML
    private void handleDeleteBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Book");
        alert.setHeaderText("Delete " + selectedBook.getBookTitle() + "?");
        alert.setContentText("Are you sure you want to delete this book?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean deleted = bookService.deleteBook(selectedBook.getBookID());
                if (deleted) {
                    bookList.remove(selectedBook);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the book.");
                }
            }
        });
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/AdminDashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) bookTableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not return to admin dashboard.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}