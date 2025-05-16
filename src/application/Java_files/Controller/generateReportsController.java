package com.readingroom.controller;

import com.readingroom.service.ReportService;
import com.readingroom.model.TopSellingBook;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GenerateReportsController {

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextArea salesReportArea;
    @FXML private TextField topBooksCountField;
    @FXML private TableView<TopSellingBook> topSellingBooksTable;
    @FXML private TableColumn<TopSellingBook, Integer> rankColumn;
    @FXML private TableColumn<TopSellingBook, String> bookTitleColumn;
    @FXML private TableColumn<TopSellingBook, String> authorColumn;
    @FXML private TableColumn<TopSellingBook, Integer> copiesSoldColumn;

    private ReportService reportService;

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    @FXML
    private void initialize() {
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        copiesSoldColumn.setCellValueFactory(new PropertyValueFactory<>("copiesSold"));
    }

    @FXML
    private void handleGenerateSalesReport() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        
        if (startDate == null || endDate == null) {
            showAlert(Alert.AlertType.WARNING, "Invalid Date Range", "Please select both start and end dates.");
            return;
        }
        
        if (endDate.isBefore(startDate)) {
            showAlert(Alert.AlertType.WARNING, "Invalid Date Range", "End date must be after start date.");
            return;
        }

        String report = reportService.generateSalesReport(startDate, endDate);
        salesReportArea.setText(report);
    }

    @FXML
    private void handleGenerateTopSellingReport() {
        int topCount;
        try {
            topCount = Integer.parseInt(topBooksCountField.getText());
            if (topCount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter a positive integer for the number of top books.");
            return;
        }

        List<TopSellingBook> topSellingBooks = reportService.generateTopSellingBooksReport(topCount);
        topSellingBooksTable.setItems(FXCollections.observableArrayList(topSellingBooks));
    }

    @FXML
    private void handleBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/Resources/Views/AdminViews/AdminDashboard.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) salesReportArea.getScene().getWindow();
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