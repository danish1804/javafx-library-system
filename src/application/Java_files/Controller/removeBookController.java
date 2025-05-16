package application.Java_files.Controller;

import application.Java_files.Model.Book;
import application.Java_files.Model.ReadingRoom;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;

public class removeBookController {

    @FXML
    private ComboBox<Book> bookSelector;

    @FXML
    private void initialize() {
        // Load all books into the ComboBox
        bookSelector.getItems().addAll(ReadingRoom.getAllBooks());
    }

    @FXML
    private void handleRemoveBook() {
        Book selectedBook = bookSelector.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            ReadingRoom.removeBook(selectedBook.getBookID());
            bookSelector.getItems().remove(selectedBook);
            showAlert("Success", "The book has been removed from the database.");
        } else {
            showAlert("Error", "Please select a book.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
