package application.Java_files.Controller;

import application.Java_files.Model.shoppingCart;
import application.Java_files.Model.OrderItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.IOException;

public class CartViewController {

    @FXML private TableView<OrderItem> cartTable;
    @FXML private TableColumn<OrderItem, String> bookIdColumn;
    @FXML private TableColumn<OrderItem, String> titleColumn;
    @FXML private TableColumn<OrderItem, String> authorColumn;
    @FXML private TableColumn<OrderItem, Integer> physicalCopiesColumn;
    @FXML private TableColumn<OrderItem, Integer> ebookCopiesColumn;
    @FXML private TableColumn<OrderItem, Double> totalCostColumn;
    @FXML private TableColumn<OrderItem, Void> removeColumn;
    @FXML private Label totalCostLabel;

    private shoppingCart shoppingCart;

    public void setShoppingCart(shoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        updateCartView();
    }

    @FXML
    public void initialize() {
        bookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookId()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookTitle())); // Changed from getAuthorName to getBookTitle
        physicalCopiesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPhysicalCopies()).asObject());
        ebookCopiesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEbookCopies()).asObject());
        totalCostColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalCost()).asObject());
        
        removeColumn.setCellFactory(param -> new TableCell<OrderItem, Void>() {
            private final Button removeButton = new Button("Remove");

            {
                removeButton.setOnAction(event -> {
                    OrderItem orderItem = getTableView().getItems().get(getIndex());
                    shoppingCart.removeItem(orderItem.getBookId());
                    updateCartView();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });
    }

    private void updateCartView() {
        ObservableList<OrderItem> cartItems = FXCollections.observableArrayList(shoppingCart.getItems());
        cartTable.setItems(cartItems);
        totalCostLabel.setText(String.format("$%.2f", shoppingCart.getTotalCost()));
    }

    @FXML
    private void handleCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/CheckoutView.fxml"));
            Parent root = loader.load();
            
            checkOutScreenController checkoutController = loader.getController();
            checkoutController.setShoppingCart(shoppingCart);
            
            Stage stage = new Stage();
            stage.setTitle("Checkout");
            stage.setScene(new Scene(root));
            stage.show();
            
            // Close the cart view
            Stage cartStage = (Stage) cartTable.getScene().getWindow();
            cartStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open checkout view. Please try again.");
        }
    }

    @FXML
    private void handleContinueShopping() {
        Stage stage = (Stage) cartTable.getScene().getWindow();
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