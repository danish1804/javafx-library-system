package application.Java_files.Controller;

import application.Java_files.Model.Book;
import application.Java_files.Model.ReadingRoom;
import application.Java_files.Model.shoppingCart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class browseBooksController {

    @FXML private TextField searchField;
    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, String> bookIdColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, Integer> physicalCopiesColumn;
    @FXML private TableColumn<Book, Boolean> ebookColumn;
    @FXML private TableColumn<Book, Double> physicalPriceColumn;
    @FXML private TableColumn<Book, Double> ebookPriceColumn;
    @FXML private TableColumn<Book, Void> addToCartColumn;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();
    private shoppingCart shoppingCart = new shoppingCart();

    @FXML
    public void initialize() {
        // Initialize the table columns
        bookIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookID()));
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookTitle()));
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthorName()));
        physicalCopiesColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumberOfPhysicalCopies()).asObject());
        ebookColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().hasEbook()));
        physicalPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice(false)).asObject());
        ebookPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice(true)).asObject());

        // Set up the "Add to Cart" button column
        addToCartColumn.setCellFactory(createAddToCartButtonCellFactory());

        // Load all books into the table
        bookList.addAll(ReadingRoom.getAllBooks());
        bookTable.setItems(bookList);
    }

    private Callback<TableColumn<Book, Void>, TableCell<Book, Void>> createAddToCartButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                return new TableCell<>() {
                    private final Button addButton = new Button("Add to Cart");

                    {
                        addButton.setOnAction(event -> {
                            Book book = getTableView().getItems().get(getIndex());
                            handleAddToCart(book);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(addButton);
                        }
                    }
                };
            }
        };
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<Book> searchResults = FXCollections.observableArrayList();
        for (Book book : ReadingRoom.getAllBooks()) {
            if (book.getBookTitle().toLowerCase().contains(keyword) || 
                book.getAuthorName().toLowerCase().contains(keyword)) {
                searchResults.add(book);
            }
        }
        bookTable.setItems(searchResults);
    }
    
    public void setShoppingCart(shoppingCart cart) {
        this.shoppingCart = cart;
    }

    private void handleAddToCart(Book book) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add to Cart");
        dialog.setHeaderText("Select book type and quantity for '" + book.getBookTitle() + "'");

        ButtonType confirmButtonType = new ButtonType("Add to Cart", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ToggleGroup bookTypeGroup = new ToggleGroup();
        RadioButton physicalRB = new RadioButton("Physical");
        RadioButton ebookRB = new RadioButton("E-book");
        physicalRB.setToggleGroup(bookTypeGroup);
        ebookRB.setToggleGroup(bookTypeGroup);

        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");

        grid.add(new Label("Book Type:"), 0, 0);
        grid.add(physicalRB, 1, 0);
        grid.add(ebookRB, 2, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(quantityField, 1, 1, 2, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                RadioButton selectedRB = (RadioButton) bookTypeGroup.getSelectedToggle();
                if (selectedRB != null && !quantityField.getText().isEmpty()) {
                    return selectedRB.getText() + "," + quantityField.getText();
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(bookTypeAndQuantity -> {
            String[] parts = bookTypeAndQuantity.split(",");
            String bookType = parts[0];
            int quantity = Integer.parseInt(parts[1]);

            boolean added = false;
            if (bookType.equals("Physical")) {
                added = shoppingCart.addPhysicalBook(book, quantity);
            } else if (bookType.equals("E-book")) {
                added = shoppingCart.addEbook(book, quantity);
            }

            if (added) {
                showAlert(AlertType.INFORMATION, "Book Added", 
                          quantity + " " + bookType + " copies of '" + book.getBookTitle() + "' added to cart.");
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to add book to cart. Please try again.");
            }
        });
    }

    @FXML
    private void handleViewCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/UserViews/CartView.fxml"));
            Parent root = loader.load();
            
            CartViewController cartController = loader.getController();
            cartController.setShoppingCart(shoppingCart);
            
            Stage stage = new Stage();
            stage.setTitle("Shopping Cart");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Unable to open cart view. Please try again.");
        }
    }
        

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}