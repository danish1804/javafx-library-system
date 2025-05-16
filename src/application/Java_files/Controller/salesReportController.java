package application.Java_files.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import application.Java_files.Model.ReadingRoom;
import application.Java_files.Model.SalesReport;

import java.time.LocalDate;
import java.util.List;

public class salesReportController {

    @FXML
    private ComboBox<String> reportTypeSelector;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField filterField;

    @FXML
    private ComboBox<String> copyTypeSelector;

    @FXML
    private TableView<SalesReport> reportTableView;

    @FXML
    private void handleGenerateReport() {
        String reportType = reportTypeSelector.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String filter = filterField.getText();
        String copyType = copyTypeSelector.getValue();  // "All", "Physical Copies", or "Ebooks"

        List<SalesReport> reportData = null;

        // Handle report generation based on the selected report type and filter
        if ("Sales by Date Range".equals(reportType)) {
            reportData = ReadingRoom.getSalesByDateRange(startDate, endDate, copyType);
        } else if ("Sales by Book".equals(reportType)) {
            reportData = ReadingRoom.getSalesByBook(filter, copyType);
        } else if ("Sales by Author".equals(reportType)) {
            reportData = ReadingRoom.getSalesByAuthor(filter, copyType);
        } else if ("Sales by Category".equals(reportType)) {
            reportData = ReadingRoom.getSalesByCategory(filter, copyType);
        } else if ("Top-Selling Books".equals(reportType)) {
            reportData = ReadingRoom.getTopSellingBooks(copyType);
        }

        if (reportData != null) {
            displayReport(reportData);
        } else {
            showAlert("Error", "No data found for the selected report.");
        }
    }

    // Method to display report in TableView
    private void displayReport(List<SalesReport> reportData) {
        reportTableView.getItems().clear();
        reportTableView.setItems(FXCollections.observableArrayList(reportData));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
