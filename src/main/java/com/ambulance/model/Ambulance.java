package com.ambulance.model;

import javafx.beans.property.*;

public class Ambulance {

    private final StringProperty id;
    private final StringProperty plateNumber;
    private final StringProperty type;
    private final StringProperty status;
    private final StringProperty location;
    private final StringProperty driverName;

    public Ambulance(String id, String plateNumber, String type, String status, String location, String driverName) {
        this.id = new SimpleStringProperty(id);
        this.plateNumber = new SimpleStringProperty(plateNumber);
        this.type = new SimpleStringProperty(type);
        this.status = new SimpleStringProperty(status);
        this.location = new SimpleStringProperty(location);
        this.driverName = new SimpleStringProperty(driverName);
    }

    public StringProperty idProperty() { return id; }
    public StringProperty plateNumberProperty() { return plateNumber; }
    public StringProperty typeProperty() { return type; }
    public StringProperty statusProperty() { return status; }
    public StringProperty locationProperty() { return location; }
    public StringProperty driverNameProperty() { return driverName; }

    public String getId() { return id.get(); }
    public String getPlateNumber() { return plateNumber.get(); }
    public String getType() { return type.get(); }
    public String getStatus() { return status.get(); }
    public String getLocation() { return location.get(); }
    public String getDriverName() { return driverName.get(); }

    public void setId(String id) { this.id.set(id); }
    public void setPlateNumber(String plateNumber) { this.plateNumber.set(plateNumber); }
    public void setType(String type) { this.type.set(type); }
    public void setStatus(String status) { this.status.set(status); }
    public void setLocation(String location) { this.location.set(location); }
    public void setDriverName(String driverName) { this.driverName.set(driverName); }
}
