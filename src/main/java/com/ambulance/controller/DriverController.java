package com.ambulance.controller;

import com.ambulance.dao.DriverDAO;
import com.ambulance.model.Driver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class DriverController {

    @FXML private TableView<Driver> driverTable;
    @FXML private TableColumn<Driver, String> colId;
    @FXML private TableColumn<Driver, String> colName;
    @FXML private TableColumn<Driver, String> colPhone;
    @FXML private TableColumn<Driver, String> colLicense;
    @FXML private TableColumn<Driver, String> colStatus;
    @FXML private TableColumn<Driver, String> colAmbulance;
    @FXML private TextField searchField;

    private final DriverDAO driverDAO = new DriverDAO();
    private final ObservableList<Driver> driverList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colLicense.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAmbulance.setCellValueFactory(new PropertyValueFactory<>("assignedAmbulance"));

        reloadData();
    }

    private void reloadData() {
        driverList.setAll(driverDAO.findAll());
        driverTable.setItems(driverList);
    }

    @FXML
    private void onAddClick() {
        Dialog<Driver> dialog = new Dialog<>();
        dialog.setTitle("Add Driver");
        dialog.setHeaderText("Enter new driver details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("DRV-023");
        TextField nameField = new TextField();
        nameField.setPromptText("John Smith");
        TextField phoneField = new TextField();
        phoneField.setPromptText("(555) 101-2023");
        TextField licenseField = new TextField();
        licenseField.setPromptText("LIC-1023");
        TextField statusField = new TextField();
        statusField.setPromptText("On Duty");
        TextField ambField = new TextField();
        ambField.setPromptText("AMB-025");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(new Label("License No:"), 0, 3);
        grid.add(licenseField, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusField, 1, 4);
        grid.add(new Label("Assigned Ambulance:"), 0, 5);
        grid.add(ambField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Driver(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        phoneField.getText().trim(),
                        licenseField.getText().trim(),
                        statusField.getText().trim(),
                        ambField.getText().trim());
            }
            return null;
        });

        Optional<Driver> result = dialog.showAndWait();
        result.ifPresent(driver -> {
            try {
                driverDAO.insert(driver);
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to add driver: " + e.getMessage());
            }
        });
    }

    @FXML
    private void onEditClick() {
        Driver selected = driverTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a driver to edit.");
            return;
        }

        Dialog<Driver> dialog = new Dialog<>();
        dialog.setTitle("Edit Driver");
        dialog.setHeaderText("Edit driver: " + selected.getId());

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField idField = new TextField(selected.getId());
        TextField nameField = new TextField(selected.getName());
        TextField phoneField = new TextField(selected.getPhone());
        TextField licenseField = new TextField(selected.getLicenseNo());
        TextField statusField = new TextField(selected.getStatus());
        TextField ambField = new TextField(selected.getAssignedAmbulance());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(new Label("License No:"), 0, 3);
        grid.add(licenseField, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusField, 1, 4);
        grid.add(new Label("Assigned Ambulance:"), 0, 5);
        grid.add(ambField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Driver(
                        idField.getText().trim(),
                        nameField.getText().trim(),
                        phoneField.getText().trim(),
                        licenseField.getText().trim(),
                        statusField.getText().trim(),
                        ambField.getText().trim());
            }
            return null;
        });

        Optional<Driver> result = dialog.showAndWait();
        result.ifPresent(updated -> {
            try {
                driverDAO.update(updated);
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to update driver: " + e.getMessage());
            }
        });
    }

    @FXML
    private void onDeleteClick() {
        Driver selected = driverTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a driver to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Driver");
        confirm.setHeaderText("Delete driver: " + selected.getId());
        confirm.setContentText("Are you sure you want to delete this driver? This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                driverDAO.delete(selected.getId());
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to delete driver: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onRefreshClick() {
        reloadData();
    }

    @FXML
    private void onSearch() {
        String query = searchField == null ? "" : searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            driverTable.setItems(driverList);
            return;
        }
        ObservableList<Driver> filtered = FXCollections.observableArrayList();
        for (Driver d : driverList) {
            if (d.getId().toLowerCase().contains(query)
                    || d.getName().toLowerCase().contains(query)
                    || d.getPhone().toLowerCase().contains(query)
                    || d.getLicenseNo().toLowerCase().contains(query)
                    || d.getStatus().toLowerCase().contains(query)
                    || d.getAssignedAmbulance().toLowerCase().contains(query)) {
                filtered.add(d);
            }
        }
        driverTable.setItems(filtered);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) driverTable.getScene().getWindow();
            Scene scene = new Scene(root, 1200, 750);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onDashboardClick() { navigateTo("dashboard.fxml", "Dashboard - Ambulance Management System"); }
    @FXML private void onAmbulancesClick() { navigateTo("ambulances.fxml", "Ambulances - Ambulance Management System"); }
    @FXML private void onEmergenciesClick() { navigateTo("emergencies.fxml", "Emergencies - Ambulance Management System"); }
    @FXML private void onDriversClick() { navigateTo("drivers.fxml", "Drivers - Ambulance Management System"); }
}
