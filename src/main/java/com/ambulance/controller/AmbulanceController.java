package com.ambulance.controller;

import java.io.IOException;

import com.ambulance.model.Ambulance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AmbulanceController {

    @FXML
    private TableView<Ambulance> ambulanceTable;

    @FXML
    private TableColumn<Ambulance, String> colId;
    @FXML
    private TableColumn<Ambulance, String> colPlate;
    @FXML
    private TableColumn<Ambulance, String> colType;
    @FXML
    private TableColumn<Ambulance, String> colStatus;
    @FXML
    private TableColumn<Ambulance, String> colDriver;
    @FXML
    private TableColumn<Ambulance, String> colLocation;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPlate.setCellValueFactory(new PropertyValueFactory<>("plateNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDriver.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        ObservableList<Ambulance> ambulances = FXCollections.observableArrayList(
                new Ambulance("AMB-001", "ABC-1234", "Basic Life Support", "Available", "Central Station",
                        "John Smith"),
                new Ambulance("AMB-002", "ABC-5678", "Advanced Life Support", "On Mission", "Downtown", "Mike Johnson"),
                new Ambulance("AMB-003", "ABC-9012", "Basic Life Support", "Available", "North Hub", "Sarah Williams"),
                new Ambulance("AMB-004", "ABC-3456", "Patient Transport", "On Mission", "East District", "David Brown"),
                new Ambulance("AMB-005", "ABC-7890", "Advanced Life Support", "Available", "South Station",
                        "Emily Davis"),
                new Ambulance("AMB-006", "DEF-1234", "Basic Life Support", "Maintenance", "Central Station",
                        "Robert Wilson"),
                new Ambulance("AMB-007", "DEF-5678", "ICU Ambulance", "On Mission", "West Side", "Lisa Anderson"),
                new Ambulance("AMB-008", "DEF-9012", "Basic Life Support", "Maintenance", "North Hub", "James Taylor"),
                new Ambulance("AMB-009", "DEF-3456", "Advanced Life Support", "Available", "Downtown", "Maria Garcia"),
                new Ambulance("AMB-010", "DEF-7890", "Patient Transport", "On Mission", "Central Station",
                        "Chris Martinez"),
                new Ambulance("AMB-011", "GHI-1234", "ICU Ambulance", "Available", "South Station", "Anna White"),
                new Ambulance("AMB-012", "GHI-5678", "Basic Life Support", "On Mission", "North Hub", "Tom Harris"),
                new Ambulance("AMB-013", "GHI-9012", "Advanced Life Support", "Available", "East District",
                        "Nancy Clark"),
                new Ambulance("AMB-014", "GHI-3456", "Patient Transport", "On Mission", "West Side", "Kevin Lewis"),
                new Ambulance("AMB-015", "GHI-7890", "Basic Life Support", "Available", "Central Station",
                        "Laura Young"),
                new Ambulance("AMB-016", "JKL-1234", "ICU Ambulance", "On Mission", "Downtown", "Steve King"),
                new Ambulance("AMB-017", "JKL-5678", "Advanced Life Support", "Available", "South Station",
                        "Amy Scott"),
                new Ambulance("AMB-018", "JKL-9012", "Basic Life Support", "Available", "North Hub", "Brian Adams"),
                new Ambulance("AMB-019", "JKL-3456", "Patient Transport", "On Mission", "East District", "Diana Green"),
                new Ambulance("AMB-020", "JKL-7890", "ICU Ambulance", "Available", "West Side", "Eric Hall"),
                new Ambulance("AMB-021", "MNO-1234", "Basic Life Support", "On Mission", "Central Station",
                        "Fiona Allen"),
                new Ambulance("AMB-022", "MNO-5678", "Advanced Life Support", "Available", "Downtown", "George Wright"),
                new Ambulance("AMB-023", "MNO-9012", "Basic Life Support", "Maintenance", "South Station",
                        "Helen Martin"),
                new Ambulance("AMB-024", "MNO-3456", "Patient Transport", "Available", "North Hub", "Ivan Robinson"));
        ambulanceTable.setItems(ambulances);
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
}
