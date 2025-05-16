package application.Java_files.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class viewPastOrdersController {

    // FXML fields corresponding to the TableView and columns in the FXML file
    @FXML
    private TableView<Order> ordersTableView;

    @FXML
    private TableColumn<Order, String> orderNumberColumn;

    @FXML
    private TableColumn<Order, String> dateTimeColumn;

    @FXML
    private TableColumn<Order, String> bookNameColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    private TableColumn<Order, String> actionsColumn;

    // Initialize method called after FXML file is loaded
    @FXML
    private void initialize() {
        // Setup the columns with the corresponding properties from the Order class
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        actionsColumn.setCellValueFactory(new PropertyValueFactory<>("actions"));

        // Populate the table view with data (this would come from a real data source)
        ordersTableView.setItems(getOrders());
    }

    // Dummy method to simulate loading orders data
    private ObservableList<Order> getOrders() {
        // This should be replaced with actual data fetching logic
        ObservableList<Order> orders = FXCollections.observableArrayList();
        // Add orders to the list (replace with real data)
        orders.add(new Order("1001", "2024-10-13 14:30", "Book A", 2, 29.99, "View"));
        orders.add(new Order("1002", "2024-10-14 10:15", "Book B", 1, 15.99, "View"));
        return orders;
    }

    // Action for exporting orders
    @FXML
    private void handleExportOrders() {
        // Logic for exporting the orders, e.g., writing to a CSV file
        System.out.println("Export Orders button clicked");
    }

    // Action for going back to the dashboard
    @FXML
    private void handleBackToDashboard() {
        // Logic for navigating back to the dashboard
        System.out.println("Back to Dashboard button clicked");
    }
}
