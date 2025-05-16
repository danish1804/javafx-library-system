package com.readingroom.controller;

import com.readingroom.model.User;
import com.readingroom.service.UserManagementService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ManageUsersController {

    @FXML private TableView<User> userTableView;
    @FXML private TableColumn<User, String> userIdColumn;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn<User, String> firstNameColumn;
    @FXML private TableColumn<User, String> lastNameColumn;
    @FXML private TableColumn<User, LocalDate> dobColumn;
    @FXML private TextField searchField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private DatePicker dobPicker;

    private UserManagementService userService;
    private OrderManagementService orderService;
    private ObservableList<User> allUsers;
    private ObservableList<User> filteredUsers;

    public void setServices(UserManagementService userService, OrderManagementService orderService) {
        this.userService = userService;
        this.orderService = orderService;
        loadUsers();
    }
    
    @FXML
    private void handleViewOrders() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to view orders.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Resources/Views/AdminViews/UserOrdersView.fxml"));
            Parent root = loader.load();
            
            UserOrdersController controller = loader.getController();
            controller.setUser(selectedUser);
            controller.setOrderService(orderService);
            controller.loadOrders();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Orders for " + selectedUser.getUsername());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load user orders view.");
        }
    }
    @FXML
    private void initialize() {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));

        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayUserDetails(newSelection);
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

    private void loadUsers() {
        List<User> users = userService.getAllUsers();
        allUsers = FXCollections.observableArrayList(users);
        filteredUsers = FXCollections.observableArrayList(allUsers);
        userTableView.setItems(filteredUsers);
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        if (searchTerm.isEmpty()) {
            filteredUsers.setAll(allUsers);
        } else {
            List<User> searchResults = allUsers.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(searchTerm) ||
                                user.getFirstName().toLowerCase().contains(searchTerm) ||
                                user.getLastName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
            filteredUsers.setAll(searchResults);
        }
    }

    private void displayUserDetails(User user) {
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        dobPicker.setValue(user.getDob());
    }

    @FXML
    private void handleUpdateUser() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to update.");
            return;
        }

        selectedUser.setFirstName(firstNameField.getText());
        selectedUser.setLastName(lastNameField.getText());
        selectedUser.setDob(dobPicker.getValue());

        boolean updated = userService.updateUser(selectedUser);
        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User information updated successfully.");
            userTableView.refresh();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user information.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/Resources/Views/AdminViews/AdminDashboard.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) userTableView.getScene().getWindow();
            stage.setScene(scene);
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