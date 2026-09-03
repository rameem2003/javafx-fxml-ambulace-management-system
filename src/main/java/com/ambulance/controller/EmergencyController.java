package com.ambulance.controller;

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

public class EmergencyController {

    @FXML private TableView<EmergencyRequest> emergencyTable;

    @FXML private TableColumn<EmergencyRequest, String> colId;
    @FXML private TableColumn<EmergencyRequest, String> colPatient;
    @FXML private TableColumn<EmergencyRequest, String> colLocation;
    @FXML private TableColumn<EmergencyRequest, String> colPriority;
    @FXML private TableColumn<EmergencyRequest, String> colStatus;
    @FXML private TableColumn<EmergencyRequest, String> colTime;
    @FXML private TableColumn<EmergencyRequest, String> colAmbulance;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colAmbulance.setCellValueFactory(new PropertyValueFactory<>("assignedAmbulance"));

        ObservableList<EmergencyRequest> requests = FXCollections.observableArrayList(
            new EmergencyRequest("REQ-001", "Alice Johnson", "45 Oak Street", "In Progress", "HIGH", "12:30 PM", "AMB-002"),
            new EmergencyRequest("REQ-002", "Bob Williams", "78 Pine Avenue", "Dispatched", "CRITICAL", "12:15 PM", "AMB-007"),
            new EmergencyRequest("REQ-003", "Carol Davis", "12 Maple Drive", "Completed", "MEDIUM", "11:45 AM", "AMB-001"),
            new EmergencyRequest("REQ-004", "Daniel Brown", "90 Elm Street", "Pending", "LOW", "11:30 AM", "Unassigned"),
            new EmergencyRequest("REQ-005", "Eva Martinez", "23 Cedar Lane", "In Progress", "HIGH", "11:15 AM", "AMB-005"),
            new EmergencyRequest("REQ-006", "Frank Wilson", "56 Birch Road", "Completed", "MEDIUM", "10:45 AM", "AMB-003"),
            new EmergencyRequest("REQ-007", "Grace Lee", "34 Walnut Court", "Dispatched", "CRITICAL", "10:30 AM", "AMB-009"),
            new EmergencyRequest("REQ-008", "Henry Taylor", "67 Spruce Way", "Pending", "LOW", "10:15 AM", "Unassigned"),
            new EmergencyRequest("REQ-009", "Iris Clark", "89 Ash Boulevard", "Completed", "HIGH", "10:00 AM", "AMB-010"),
            new EmergencyRequest("REQ-010", "Jack Hall", "14 Poplar Crescent", "In Progress", "CRITICAL", "9:45 AM", "AMB-012"),
            new EmergencyRequest("REQ-011", "Karen Young", "28 Willow Terrace", "Dispatched", "MEDIUM", "9:30 AM", "AMB-015"),
            new EmergencyRequest("REQ-012", "Leo Scott", "52 Hickory Lane", "Completed", "LOW", "9:15 AM", "AMB-017"),
            new EmergencyRequest("REQ-013", "Mona Adams", "63 Cypress Way", "In Progress", "HIGH", "9:00 AM", "AMB-019"),
            new EmergencyRequest("REQ-014", "Nathan Wright", "77 Redwood Drive", "Pending", "CRITICAL", "8:45 AM", "Unassigned"),
            new EmergencyRequest("REQ-015", "Olivia Martin", "91 Sequoia Court", "Completed", "MEDIUM", "8:30 AM", "AMB-021"),
            new EmergencyRequest("REQ-016", "Paul Robinson", "48 Magnolia Ave", "Dispatched", "HIGH", "8:15 AM", "AMB-022"),
            new EmergencyRequest("REQ-017", "Quinn Allen", "35 Hemlock Road", "Completed", "LOW", "8:00 AM", "AMB-014"),
            new EmergencyRequest("REQ-018", "Rachel Lewis", "62 Juniper Blvd", "In Progress", "MEDIUM", "7:45 AM", "AMB-016"),
            new EmergencyRequest("REQ-019", "Sam Green", "74 Sycamore St", "Completed", "CRITICAL", "7:30 AM", "AMB-020"),
            new EmergencyRequest("REQ-020", "Tina Baker", "86 Laurel Drive", "Pending", "MEDIUM", "7:15 AM", "Unassigned")
        );
        emergencyTable.setItems(requests);
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
