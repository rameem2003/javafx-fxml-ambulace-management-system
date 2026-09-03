package com.ambulance.controller;

import com.ambulance.model.Driver;
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

public class DriverController {

    @FXML private TableView<Driver> driverTable;

    @FXML private TableColumn<Driver, String> colId;
    @FXML private TableColumn<Driver, String> colName;
    @FXML private TableColumn<Driver, String> colPhone;
    @FXML private TableColumn<Driver, String> colLicense;
    @FXML private TableColumn<Driver, String> colStatus;
    @FXML private TableColumn<Driver, String> colAmbulance;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colLicense.setCellValueFactory(new PropertyValueFactory<>("licenseNo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAmbulance.setCellValueFactory(new PropertyValueFactory<>("assignedAmbulance"));

        ObservableList<Driver> drivers = FXCollections.observableArrayList(
            new Driver("DRV-001", "John Smith", "(555) 101-2001", "LIC-1001", "On Duty", "AMB-001"),
            new Driver("DRV-002", "Mike Johnson", "(555) 101-2002", "LIC-1002", "On Duty", "AMB-002"),
            new Driver("DRV-003", "Sarah Williams", "(555) 101-2003", "LIC-1003", "On Duty", "AMB-003"),
            new Driver("DRV-004", "David Brown", "(555) 101-2004", "LIC-1004", "On Duty", "AMB-004"),
            new Driver("DRV-005", "Emily Davis", "(555) 101-2005", "LIC-1005", "On Duty", "AMB-005"),
            new Driver("DRV-006", "Robert Wilson", "(555) 101-2006", "LIC-1006", "Off Duty", "AMB-006"),
            new Driver("DRV-007", "Lisa Anderson", "(555) 101-2007", "LIC-1007", "On Duty", "AMB-007"),
            new Driver("DRV-008", "James Taylor", "(555) 101-2008", "LIC-1008", "Off Duty", "AMB-008"),
            new Driver("DRV-009", "Maria Garcia", "(555) 101-2009", "LIC-1009", "On Duty", "AMB-009"),
            new Driver("DRV-010", "Chris Martinez", "(555) 101-2010", "LIC-1010", "On Duty", "AMB-010"),
            new Driver("DRV-011", "Anna White", "(555) 101-2011", "LIC-1011", "On Duty", "AMB-011"),
            new Driver("DRV-012", "Tom Harris", "(555) 101-2012", "LIC-1012", "On Duty", "AMB-012"),
            new Driver("DRV-013", "Nancy Clark", "(555) 101-2013", "LIC-1013", "On Duty", "AMB-013"),
            new Driver("DRV-014", "Kevin Lewis", "(555) 101-2014", "LIC-1014", "On Duty", "AMB-014"),
            new Driver("DRV-015", "Laura Young", "(555) 101-2015", "LIC-1015", "On Duty", "AMB-015"),
            new Driver("DRV-016", "Steve King", "(555) 101-2016", "LIC-1016", "On Duty", "AMB-016"),
            new Driver("DRV-017", "Amy Scott", "(555) 101-2017", "LIC-1017", "On Duty", "AMB-017"),
            new Driver("DRV-018", "Brian Adams", "(555) 101-2018", "LIC-1018", "On Duty", "AMB-018"),
            new Driver("DRV-019", "Diana Green", "(555) 101-2019", "LIC-1019", "Off Duty", "Unassigned"),
            new Driver("DRV-020", "Eric Hall", "(555) 101-2020", "LIC-1020", "On Duty", "AMB-020"),
            new Driver("DRV-021", "Fiona Allen", "(555) 101-2021", "LIC-1021", "On Duty", "AMB-021"),
            new Driver("DRV-022", "George Wright", "(555) 101-2022", "LIC-1022", "On Leave", "Unassigned")
        );
        driverTable.setItems(drivers);
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
