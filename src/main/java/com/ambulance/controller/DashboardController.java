package com.ambulance.controller;

import com.ambulance.App;
import com.ambulance.model.Ambulance;
import com.ambulance.model.EmergencyRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML private TableView<Ambulance> ambulanceTable;
    @FXML private TableView<EmergencyRequest> emergencyTable;

    @FXML private TableColumn<Ambulance, String> colAmbId;
    @FXML private TableColumn<Ambulance, String> colAmbPlate;
    @FXML private TableColumn<Ambulance, String> colAmbType;
    @FXML private TableColumn<Ambulance, String> colAmbStatus;
    @FXML private TableColumn<Ambulance, String> colAmbDriver;
    @FXML private TableColumn<Ambulance, String> colAmbLocation;

    @FXML private TableColumn<EmergencyRequest, String> colRequestId;
    @FXML private TableColumn<EmergencyRequest, String> colRequestPatient;
    @FXML private TableColumn<EmergencyRequest, String> colRequestLocation;
    @FXML private TableColumn<EmergencyRequest, String> colRequestPriority;
    @FXML private TableColumn<EmergencyRequest, String> colRequestStatus;
    @FXML private TableColumn<EmergencyRequest, String> colRequestTime;

    @FXML private Label totalAmbulances;
    @FXML private Label availableAmbulances;
    @FXML private Label onMissionAmbulances;
    @FXML private Label maintenanceAmbulances;
    @FXML private Label totalRequests;
    @FXML private Label pendingRequests;
    @FXML private Label activeDrivers;
    @FXML private Label hospitalsConnected;
    @FXML private Label pageTitle;

    @FXML
    public void initialize() {
        setupAmbulanceTable();
        setupEmergencyTable();
        loadStaticData();
    }

    private void setupAmbulanceTable() {
        colAmbId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmbPlate.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        colAmbType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmbStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAmbDriver.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colAmbLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    private void setupEmergencyTable() {
        colRequestId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRequestPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colRequestLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colRequestPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colRequestStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRequestTime.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void loadStaticData() {
        ObservableList<Ambulance> ambulances = FXCollections.observableArrayList(
            new Ambulance("AMB-001", "ABC-1234", "Basic Life Support", "Available", "Central Station", "John Smith"),
            new Ambulance("AMB-002", "ABC-5678", "Advanced Life Support", "On Mission", "Downtown", "Mike Johnson"),
            new Ambulance("AMB-003", "ABC-9012", "Basic Life Support", "Available", "North Hub", "Sarah Williams"),
            new Ambulance("AMB-004", "ABC-3456", "Patient Transport", "On Mission", "East District", "David Brown"),
            new Ambulance("AMB-005", "ABC-7890", "Advanced Life Support", "Available", "South Station", "Emily Davis"),
            new Ambulance("AMB-006", "DEF-1234", "Basic Life Support", "Maintenance", "Central Station", "Robert Wilson"),
            new Ambulance("AMB-007", "DEF-5678", "ICU Ambulance", "On Mission", "West Side", "Lisa Anderson"),
            new Ambulance("AMB-008", "DEF-9012", "Basic Life Support", "Maintenance", "North Hub", "James Taylor"),
            new Ambulance("AMB-009", "DEF-3456", "Advanced Life Support", "Available", "Downtown", "Maria Garcia"),
            new Ambulance("AMB-010", "DEF-7890", "Patient Transport", "On Mission", "Central Station", "Chris Martinez")
        );
        ambulanceTable.setItems(ambulances);

        ObservableList<EmergencyRequest> requests = FXCollections.observableArrayList(
            new EmergencyRequest("REQ-001", "Alice Johnson", "45 Oak Street", "In Progress", "HIGH", "12:30 PM", "AMB-002"),
            new EmergencyRequest("REQ-002", "Bob Williams", "78 Pine Avenue", "Dispatched", "CRITICAL", "12:15 PM", "AMB-007"),
            new EmergencyRequest("REQ-003", "Carol Davis", "12 Maple Drive", "Completed", "MEDIUM", "11:45 AM", "AMB-001"),
            new EmergencyRequest("REQ-004", "Daniel Brown", "90 Elm Street", "Pending", "LOW", "11:30 AM", "Unassigned"),
            new EmergencyRequest("REQ-005", "Eva Martinez", "23 Cedar Lane", "In Progress", "HIGH", "11:15 AM", "AMB-005"),
            new EmergencyRequest("REQ-006", "Frank Wilson", "56 Birch Road", "Completed", "MEDIUM", "10:45 AM", "AMB-003"),
            new EmergencyRequest("REQ-007", "Grace Lee", "34 Walnut Court", "Dispatched", "CRITICAL", "10:30 AM", "AMB-009"),
            new EmergencyRequest("REQ-008", "Henry Taylor", "67 Spruce Way", "Pending", "LOW", "10:15 AM", "Unassigned")
        );
        emergencyTable.setItems(requests);
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

    @FXML
    private void onDashboardClick() {
        navigateTo("dashboard.fxml", "Dashboard - Ambulance Management System");
    }

    @FXML
    private void onAmbulancesClick() {
        navigateTo("ambulances.fxml", "Ambulances - Ambulance Management System");
    }

    @FXML
    private void onEmergenciesClick() {
        navigateTo("emergencies.fxml", "Emergencies - Ambulance Management System");
    }

    @FXML
    private void onDriversClick() {
        navigateTo("drivers.fxml", "Drivers - Ambulance Management System");
    }

    @FXML
    private void onReportsClick() {
        // Reports view placeholder
    }

    @FXML
    private void onSettingsClick() {
        // Settings view placeholder
    }
}
