package com.ambulance.controller;

import com.ambulance.dao.EmergencyRequestDAO;
import com.ambulance.model.EmergencyRequest;

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

public class EmergencyController {

    @FXML private TableView<EmergencyRequest> emergencyTable;
    @FXML private TableColumn<EmergencyRequest, String> colId;
    @FXML private TableColumn<EmergencyRequest, String> colPatient;
    @FXML private TableColumn<EmergencyRequest, String> colLocation;
    @FXML private TableColumn<EmergencyRequest, String> colPriority;
    @FXML private TableColumn<EmergencyRequest, String> colStatus;
    @FXML private TableColumn<EmergencyRequest, String> colTime;
    @FXML private TableColumn<EmergencyRequest, String> colAmbulance;
    @FXML private TextField searchField;

    @FXML private Label totalRequests;
    @FXML private Label criticalCount;
    @FXML private Label pendingCount;
    @FXML private Label completedCount;

    private final EmergencyRequestDAO emergencyDAO = new EmergencyRequestDAO();
    private final ObservableList<EmergencyRequest> requestList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colAmbulance.setCellValueFactory(new PropertyValueFactory<>("assignedAmbulance"));

        reloadData();
    }

    private void reloadData() {
        requestList.setAll(emergencyDAO.findAll());
        emergencyTable.setItems(requestList);
        updateStats();
    }

    private void updateStats() {
        long total = requestList.size();
        long critical = requestList.stream().filter(r -> "CRITICAL".equalsIgnoreCase(r.getPriority())).count();
        long pending = requestList.stream().filter(r -> "Pending".equalsIgnoreCase(r.getStatus())).count();
        long completed = requestList.stream().filter(r -> "Completed".equalsIgnoreCase(r.getStatus())).count();

        if (totalRequests != null) totalRequests.setText(String.valueOf(total));
        if (criticalCount != null) criticalCount.setText(String.valueOf(critical));
        if (pendingCount != null) pendingCount.setText(String.valueOf(pending));
        if (completedCount != null) completedCount.setText(String.valueOf(completed));
    }

    @FXML
    private void onAddClick() {
        Dialog<EmergencyRequest> dialog = new Dialog<>();
        dialog.setTitle("Add Emergency Request");
        dialog.setHeaderText("Enter new emergency request details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField idField = new TextField();
        idField.setPromptText("REQ-021");
        TextField patientField = new TextField();
        patientField.setPromptText("Alice Johnson");
        TextField locationField = new TextField();
        locationField.setPromptText("45 Oak Street");
        TextField priorityField = new TextField();
        priorityField.setPromptText("HIGH");
        TextField statusField = new TextField();
        statusField.setPromptText("Pending");
        TextField timeField = new TextField();
        timeField.setPromptText("12:30 PM");
        TextField ambField = new TextField();
        ambField.setPromptText("AMB-001");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Patient:"), 0, 1);
        grid.add(patientField, 1, 1);
        grid.add(new Label("Location:"), 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(new Label("Priority:"), 0, 3);
        grid.add(priorityField, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusField, 1, 4);
        grid.add(new Label("Time:"), 0, 5);
        grid.add(timeField, 1, 5);
        grid.add(new Label("Ambulance:"), 0, 6);
        grid.add(ambField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new EmergencyRequest(
                        idField.getText().trim(),
                        patientField.getText().trim(),
                        locationField.getText().trim(),
                        statusField.getText().trim(),
                        priorityField.getText().trim(),
                        timeField.getText().trim(),
                        ambField.getText().trim());
            }
            return null;
        });

        Optional<EmergencyRequest> result = dialog.showAndWait();
        result.ifPresent(request -> {
            try {
                emergencyDAO.insert(request);
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to add emergency request: " + e.getMessage());
            }
        });
    }

    @FXML
    private void onEditClick() {
        EmergencyRequest selected = emergencyTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select an emergency request to edit.");
            return;
        }

        Dialog<EmergencyRequest> dialog = new Dialog<>();
        dialog.setTitle("Edit Emergency Request");
        dialog.setHeaderText("Edit request: " + selected.getId());

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField idField = new TextField(selected.getId());
        TextField patientField = new TextField(selected.getPatientName());
        TextField locationField = new TextField(selected.getLocation());
        TextField priorityField = new TextField(selected.getPriority());
        TextField statusField = new TextField(selected.getStatus());
        TextField timeField = new TextField(selected.getTime());
        TextField ambField = new TextField(selected.getAssignedAmbulance());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Patient:"), 0, 1);
        grid.add(patientField, 1, 1);
        grid.add(new Label("Location:"), 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(new Label("Priority:"), 0, 3);
        grid.add(priorityField, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusField, 1, 4);
        grid.add(new Label("Time:"), 0, 5);
        grid.add(timeField, 1, 5);
        grid.add(new Label("Ambulance:"), 0, 6);
        grid.add(ambField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new EmergencyRequest(
                        idField.getText().trim(),
                        patientField.getText().trim(),
                        locationField.getText().trim(),
                        statusField.getText().trim(),
                        priorityField.getText().trim(),
                        timeField.getText().trim(),
                        ambField.getText().trim());
            }
            return null;
        });

        Optional<EmergencyRequest> result = dialog.showAndWait();
        result.ifPresent(updated -> {
            try {
                emergencyDAO.update(updated);
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to update emergency request: " + e.getMessage());
            }
        });
    }

    @FXML
    private void onDeleteClick() {
        EmergencyRequest selected = emergencyTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select an emergency request to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Emergency Request");
        confirm.setHeaderText("Delete request: " + selected.getId());
        confirm.setContentText("Are you sure you want to delete this emergency request? This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                emergencyDAO.delete(selected.getId());
                reloadData();
            } catch (RuntimeException e) {
                showError("Failed to delete emergency request: " + e.getMessage());
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
            emergencyTable.setItems(requestList);
            return;
        }
        ObservableList<EmergencyRequest> filtered = FXCollections.observableArrayList();
        for (EmergencyRequest r : requestList) {
            if (r.getId().toLowerCase().contains(query)
                    || r.getPatientName().toLowerCase().contains(query)
                    || r.getLocation().toLowerCase().contains(query)
                    || r.getPriority().toLowerCase().contains(query)
                    || r.getStatus().toLowerCase().contains(query)
                    || r.getTime().toLowerCase().contains(query)
                    || r.getAssignedAmbulance().toLowerCase().contains(query)) {
                filtered.add(r);
            }
        }
        emergencyTable.setItems(filtered);
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
            Stage stage = (Stage) emergencyTable.getScene().getWindow();
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
