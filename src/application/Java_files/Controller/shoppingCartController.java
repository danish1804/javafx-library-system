package application.Java_files.Controller;

import java.io.IOException;
import java.util.UUID;

import application.Java_files.Model.CartItem;
import application.Java_files.Model.shoppingCart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class CartViewController {

    @FXML
    private TableView<CartItem> cartTable;

    @FXML
    private TableColumn<CartItem, String> bookIdColumn;

    @FXML
    private TableColumn<CartItem, String> titleColumn;

    @FXML
    private TableColumn<CartItem, String> authorColumn;

    @FXML
    private TableColumn<CartItem, Integer> physicalCopiesColumn;

    @FXML
    private TableColumn<CartItem, Integer> ebookCopiesColumn;

    @FXML
    private TableColumn<CartItem, Double> totalCostColumn;

    @FXML
    private TableColumn<CartItem, Void> actionsColumn;

    @FXML
    private Label totalCostLabel;

    private shoppingCart shoppingCart;

    @FXML
    public void initialize() {
        // Initialize the table columns
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        physicalCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("physicalCopies"));
        ebookCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("ebookCopies"));
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        // Set up the actions column
        actionsColumn.setCellFactory(param -> new TableCell<CartItem, Void>() {
            private final Button addButton = new Button("+");
            private final Button removeButton = new Button("-");
            private final HBox pane = new HBox(addButton, removeButton);

            {
                addButton.setOnAction(event -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    addToCart(item);
                });

                removeButton.setOnAction(event -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    removeFromCart(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });

        // Set up the shopping cart (you might want to inject this from another part of your application)
        shoppingCart = new ShoppingCart();

        // Load cart items
        updateCartView();
    }

    public void setShoppingCart(ShoppingCart cart) {
        this.shoppingCart = cart;
        updateCartView();
    }

    private void updateCartView() {
        ObservableList<CartItem> cartItems = FXCollections.observableArrayList(shoppingCart.getItems());
        cartTable.setItems(cartItems);
        updateTotalCost();
    }

    private void updateTotalCost() {
        double totalCost = shoppingCart.getTotalCost();
        totalCostLabel.setText(String.format("Total: $%.2f", totalCost));
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
    private void handleClose() {
        // Close the cart view
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

    private String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public void addToCart(CartItem item) {
        shoppingCart.addItem(item);
        updateCartView();
    }

    public void removeFromCart(CartItem item) {
        shoppingCart.removeItem(item);
        updateCartView();
    }
}