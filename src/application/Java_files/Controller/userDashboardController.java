package application.Java_files.Controller;

import application.Java_files.Model.Book;
import application.Java_files.Model.ReadingRoom;
import application.Java_files.Model.shoppingCart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;

public class userDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button logoutButton;
    @FXML private Button editProfileButton;
    @FXML private Button browseBooksButton;
    @FXML private Button viewCartButton;
    @FXML private Button viewOrdersButton;
    @FXML private Button exportOrdersButton;
    @FXML private TableView<Book> topBooksTable;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> soldCopiesColumn;

    private String firstName;
    private shoppingCart shoppingCart;

    @FXML
    public void initialize() {
        // Initialize the shopping cart
        shoppingCart = new shoppingCart();
        
        // Set up table columns
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBookTitle()));
        authorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthorName()));
        soldCopiesColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getSoldCopies()).asObject());
    }

    public void setUserFirstName(String firstName) {
        this.firstName = firstName;
        welcomeLabel.setText("Welcome, " + firstName + "!");
        populateTopBooks();
    }

    private void populateTopBooks() {
        List<Book> topBooks = ReadingRoom.getTopBooks(5); // Assuming this method returns top 5 books
        topBooksTable.setItems(FXCollections.observableArrayList(topBooks));
    }

    @FXML
    private void handleLogout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/Resources/Views/LoginView.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/editProfile.fxml"));
            Parent root = loader.load();
            editProfileController editProfileController = loader.getController();
//            editProfileController.initializeUserData(firstName, "Doe", "johndoe", java.time.LocalDate.of(1990, 1, 1), 1);
            Stage stage = new Stage();
            stage.setTitle("Edit Profile");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBrowseBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/browseBooks.fxml"));
            Parent root = loader.load();
            browseBooksController controller = loader.getController();
            controller.setShoppingCart(shoppingCart);
            Stage stage = (Stage) browseBooksButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/CartView.fxml"));
            Parent root = loader.load();
            CartViewController controller = loader.getController();
            controller.setShoppingCart(shoppingCart);
            Stage stage = new Stage();
            stage.setTitle("Shopping Cart");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewOrders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/viewAllOrders.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View Orders");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExportOrders() {
        // Implement logic to export orders
        System.out.println("Exporting orders...");
        // You might want to open a file chooser here and then call a method to export the orders
    }
}