package application.Java_files.Controller;

import application.Java_files.Service.*;
import application.Java_files.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;

public class AdminDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label instructionLabel;
    @FXML private Button logoutButton;

    private BookManagementService bookService;
    private OrderManagementService orderService;
    private UserManagementService userService;
    private ReportGenerationService reportService;

    @FXML
    public void initialize() {
        bookService = new BookManagementService();
        orderService = new OrderManagementService();
        userService = new UserManagementService();
        reportService = new ReportGenerationService();
        OrderDAO orderDAO = new OrderDAO();
        BookDAO bookDAO = new BookDAO();
        this.reportService = new ReportService(orderDAO, bookDAO);
        // Set welcome message (assuming you have a way to get the admin's name)
        welcomeLabel.setText("Welcome, Admin!");
    }

    @FXML
    private void handleManageBooks() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/ManageBooksView.fxml"));
            Parent root = loader.load();
            
            ManageBooksController controller = loader.getController();
            controller.setBookService(bookService);
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Books");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load book management screen.");
        }
    }

    @FXML
    private void handleViewBookStock() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/ViewBookStockView.fxml"));
            Parent root = loader.load();
            
            ViewBookStockController controller = loader.getController();
            List<Book> books = bookService.getAllBooks();
            controller.setBooks(FXCollections.observableArrayList(books));
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Stock");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load book stock view.");
        }
    }

    @FXML
    private void handleUpdateBookStock() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/UpdateBookStockView.fxml"));
            Parent root = loader.load();
            
            UpdateBookStockController controller = loader.getController();
            controller.setBookService(bookService);
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Book Stock");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load update book stock screen.");
        }
    }

    @FXML
    private void handleAddNewBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/AddBookView.fxml"));
            Parent root = loader.load();
            
            AddBookController controller = loader.getController();
            controller.setBookService(bookService);
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Book");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load add new book screen.");
        }
    }

    @FXML
    private void handleViewAllOrders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/ViewAllOrdersView.fxml"));
            Parent root = loader.load();
            
            ViewAllOrdersController controller = loader.getController();
            List<Order> orders = orderService.getAllOrders();
            controller.setOrders(FXCollections.observableArrayList(orders));
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("All Orders");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load all orders view.");
        }
    }

    @FXML
    private void handleManageUsers() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/ManageUsersView.fxml"));
            Parent root = loader.load();
            
            ManageUsersController controller = loader.getController();
            List<User> users = userService.getAllUsers();
            controller.setUsers(FXCollections.observableArrayList(users));
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Manage Users");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load user management screen.");
        }
    }

    @FXML
    private void handleGenerateReports() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/GenerateReportsView.fxml"));
            Parent root = loader.load();
            
            GenerateReportsController controller = loader.getController();
            controller.setReportService(reportService);
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Generate Reports");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load report generation screen.");
        }
    }

    @FXML
    private void handleLogout() {
        // Clear any session data
        // For example: SessionManager.clearSession();
        
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/application/Resources/Views/LoginView.fxml"));
            Scene loginScene = new Scene(loginRoot);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load login screen.");
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