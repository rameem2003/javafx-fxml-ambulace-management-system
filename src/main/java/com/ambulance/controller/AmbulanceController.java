package com.ambulance.controller;

import com.ambulance.dao.AmbulanceDAO;
import com.ambulance.model.Ambulance;

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

public class AmbulanceController {

    @FXML private TableView<Ambulance> ambulanceTable;
    @FXML private TableColumn<Ambulance, String> colId;
    @FXML private TableColumn<Ambulance, String> colPlate;
    @FXML private TableColumn<Ambulance, String> colType;
    @FXML private TableColumn<Ambulance, String> colStatus;
    @FXML private TableColumn<Ambulance, String> colDriver;
    @FXML private TableColumn<Ambulance, String> colLocation;
    @FXML private TextField searchField;

    @FXML private Label totalFleet;
    @FXML private Label availableCount;
    @FXML private Label onMissionCount;
    @FXML private Label maintenanceCount;

    private final AmbulanceDAO ambulanceDAO = new AmbulanceDAO();
    private final ObservableList<Ambulance> ambulanceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPlate.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDriver.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        reloadData();
    }

    private void reloadData() {
        ambulanceList.setAll(ambulanceDAO.findAll());
        ambulanceTable.setItems(ambulanceList);
        updateStats();
    }

    private void updateStats() {
        long total = ambulanceList.size();
        long available = ambulanceList.stream().filter(a -> "Available".equalsIgnoreCase(a.getStatus())).count();
        long onMission = ambulanceList.stream().filter(a -> "On Mission".equalsIgnoreCase(a.getStatus())).count();
        long maintenance = ambulanceList.stream().filter(a -> "Maintenance".equalsIgnoreCase(a.getStatus())).count();

        if (totalFleet != null) totalFleet.setText(String.valueOf(total));
        if (availableCount != null) availableCount.setText(String.valueOf(available));
        if (onMissionCount != null) onMissionCount.setText(String.valueOf(onMission));
        if (maintenanceCount != null) maintenanceCount.setText(String.valueOf(maintenance));
    }

    @FXML
    private void onAddClick() {
        Dialog<Ambulance> dialog = new Dialog<>();
        dialog.setTitle("Add Ambulance");
        dialog.setHeaderText("Enter new ambulance details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("AMB-025");
        TextField plateField = new TextField();
        plateField.setPromptText("ABC-1234");
        TextField typeField = new TextField();
        typeField.setPromptText("Basic Life Support");
        TextField statusField = new TextField();
        statusField.setPromptText("Available");
        TextField locationField = new TextField();
        locationField.setPromptText("Central Station");
        TextField driverField = new TextField();
        driverField.setPromptText("John Smith");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Plate Number:"), 0, 1);
        grid.add(plateField, 1, 1);
        grid.add(new Label("Type:"), 0, 2);
        grid.add(typeField, 1, 2);
        grid.add(new Label("Status:"), 0, 3);
        grid.add(statusField, 1, 3);
        grid.add(new Label("Location:"), 0, 4);
        grid.add(locationField, 1, 4);
        grid.add(new Label("Driver:"), 0, 5);
        grid.add(driverField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Ambulance(
                        idField.getText().trim(),
                        plateField.getText().trim(),
                        typeField.getText().trim(),
                        statusField.getText().trim(),
                        locationField.getText().trim(),
                        driverField.getText().trim());
            }
            return null;
        });

        Optional<Ambulance> result = dialog.showAndWait();
        result.ifPresent(ambulance -> {
            try {
                ambulanceDAO.insert(ambulance);
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to add ambulance: " + e.getMessage());
            }
        });
    }

    @FXML
    private void onEditClick() {
        Ambulance selected = ambulanceTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select an ambulance to edit.");
            return;
        }

        Dialog<Ambulance> dialog = new Dialog<>();
        dialog.setTitle("Edit Ambulance");
        dialog.setHeaderText("Edit ambulance: " + selected.getId());

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField idField = new TextField(selected.getId());
        TextField plateField = new TextField(selected.getPlateNumber());
        TextField typeField = new TextField(selected.getType());
        TextField statusField = new TextField(selected.getStatus());
        TextField locationField = new TextField(selected.getLocation());
        TextField driverField = new TextField(selected.getDriverName());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Plate Number:"), 0, 1);
        grid.add(plateField, 1, 1);
        grid.add(new Label("Type:"), 0, 2);
        grid.add(typeField, 1, 2);
        grid.add(new Label("Status:"), 0, 3);
        grid.add(statusField, 1, 3);
        grid.add(new Label("Location:"), 0, 4);
        grid.add(locationField, 1, 4);
        grid.add(new Label("Driver:"), 0, 5);
        grid.add(driverField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Ambulance(
                        idField.getText().trim(),
                        plateField.getText().trim(),
                        typeField.getText().trim(),
                        statusField.getText().trim(),
                        locationField.getText().trim(),
                        driverField.getText().trim());
            }
            return null;
        });

        Optional<Ambulance> result = dialog.showAndWait();
        result.ifPresent(updated -> {
            try {
                ambulanceDAO.update(updated);
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to update ambulance: " + e.getMessage());
            }
        });
    }

    @FXML
    private void onDeleteClick() {
        Ambulance selected = ambulanceTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select an ambulance to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Ambulance");
        confirm.setHeaderText("Delete ambulance: " + selected.getId());
        confirm.setContentText("Are you sure you want to delete this ambulance? This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                ambulanceDAO.delete(selected.getId());
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to delete ambulance: " + e.getMessage());
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
            ambulanceTable.setItems(ambulanceList);
            return;
        }
        ObservableList<Ambulance> filtered = FXCollections.observableArrayList();
        for (Ambulance a : ambulanceList) {
            if (a.getId().toLowerCase().contains(query)
                    || a.getPlateNumber().toLowerCase().contains(query)
                    || a.getType().toLowerCase().contains(query)
                    || a.getStatus().toLowerCase().contains(query)
                    || a.getLocation().toLowerCase().contains(query)
                    || a.getDriverName().toLowerCase().contains(query)) {
                filtered.add(a);
            }
        }
        ambulanceTable.setItems(filtered);
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
            Stage stage = (Stage) ambulanceTable.getScene().getWindow();
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
