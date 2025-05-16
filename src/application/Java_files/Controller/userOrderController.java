package application.Java_files.Controller;

import application.Java_files.Model.Order;
import application.Java_files.Model.ReadingRoom;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;

public class userOrderController {

    @FXML
    private TableView<order> ordersTableView;

    @FXML
    private TableColumn<order, String> orderIDColumn;

    @FXML
    private TableColumn<order, LocalDateTime> orderDateColumn;

    @FXML
    private TableColumn<order, Integer> totalItemsColumn;

    @FXML
    private TableColumn<order, Double> totalPriceColumn;

    @FXML
    private TableColumn<order, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<order, Integer> ebookCopiesColumn;

    // Assume userID is passed or retrieved through session management
    private int userID = 1;  // Example hard-coded value for now

    @FXML
    private void initialize() {
        // Set up the columns in the TableView
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalItemsColumn.setCellValueFactory(new PropertyValueFactory<>("totalItems"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopiesOrdered"));
        ebookCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("ebookCopiesOrdered"));

        // Load orders for the user
        loadUserOrders(userID);
    }

    private void loadUserOrders(int userID) {
        ReadingRoom readingRoom = new ReadingRoom();
        List<order> orders = readingRoom.getUserOrders(userID);

        // Populate the TableView with the user's orders
        ordersTableView.setItems(FXCollections.observableArrayList(orders));
    }

    @FXML
    private void handleBackToDashboard() {
        // Handle the back button functionality (e.g., navigate to dashboard)
    }
}
