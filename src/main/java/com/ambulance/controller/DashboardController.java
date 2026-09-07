package com.ambulance.controller;

import com.ambulance.dao.AmbulanceDAO;
import com.ambulance.dao.DriverDAO;
import com.ambulance.dao.EmergencyRequestDAO;
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

    private final AmbulanceDAO ambulanceDAO = new AmbulanceDAO();
    private final EmergencyRequestDAO emergencyDAO = new EmergencyRequestDAO();
    private final DriverDAO driverDAO = new DriverDAO();

    @FXML
    public void initialize() {
        setupAmbulanceTable();
        setupEmergencyTable();
        loadData();
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

    private void loadData() {
        try {
            ObservableList<Ambulance> ambulances = FXCollections.observableArrayList(ambulanceDAO.findAll());
            ambulanceTable.setItems(ambulances);

            ObservableList<EmergencyRequest> requests = FXCollections.observableArrayList(emergencyDAO.findAll());
            emergencyTable.setItems(requests);

            updateStats(ambulances, requests);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void updateStats(ObservableList<Ambulance> ambulances, ObservableList<EmergencyRequest> requests) {
        long total = ambulances.size();
        long available = ambulances.stream().filter(a -> "Available".equalsIgnoreCase(a.getStatus())).count();
        long onMission = ambulances.stream().filter(a -> "On Mission".equalsIgnoreCase(a.getStatus())).count();
        long maintenance = ambulances.stream().filter(a -> "Maintenance".equalsIgnoreCase(a.getStatus())).count();

        long totalReq = requests.size();
        long pending = requests.stream().filter(r -> "Pending".equalsIgnoreCase(r.getStatus())).count();

        long activeDrv = 0;
        try {
            activeDrv = driverDAO.findAll().stream()
                    .filter(d -> "On Duty".equalsIgnoreCase(d.getStatus())).count();
        } catch (RuntimeException ignored) {
        }

        if (totalAmbulances != null) totalAmbulances.setText(String.valueOf(total));
        if (availableAmbulances != null) availableAmbulances.setText(String.valueOf(available));
        if (onMissionAmbulances != null) onMissionAmbulances.setText(String.valueOf(onMission));
        if (maintenanceAmbulances != null) maintenanceAmbulances.setText(String.valueOf(maintenance));
        if (totalRequests != null) totalRequests.setText(String.valueOf(totalReq));
        if (pendingRequests != null) pendingRequests.setText(String.valueOf(pending));
        if (activeDrivers != null) activeDrivers.setText(String.valueOf(activeDrv));
        if (hospitalsConnected != null) hospitalsConnected.setText("12");
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
    }

    @FXML
    private void onSettingsClick() {
    }
}
