package com.ambulance.model;

import javafx.beans.property.*;

public class Driver {

    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty licenseNo;
    private final StringProperty status;
    private final StringProperty assignedAmbulance;

    public Driver(String id, String name, String phone, String licenseNo, String status, String assignedAmbulance) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.licenseNo = new SimpleStringProperty(licenseNo);
        this.status = new SimpleStringProperty(status);
        this.assignedAmbulance = new SimpleStringProperty(assignedAmbulance);
    }

    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty licenseNoProperty() { return licenseNo; }
    public StringProperty statusProperty() { return status; }
    public StringProperty assignedAmbulanceProperty() { return assignedAmbulance; }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getPhone() { return phone.get(); }
    public String getLicenseNo() { return licenseNo.get(); }
    public String getStatus() { return status.get(); }
    public String getAssignedAmbulance() { return assignedAmbulance.get(); }

    public void setId(String id) { this.id.set(id); }
    public void setName(String name) { this.name.set(name); }
    public void setPhone(String phone) { this.phone.set(phone); }
    public void setLicenseNo(String licenseNo) { this.licenseNo.set(licenseNo); }
    public void setStatus(String status) { this.status.set(status); }
    public void setAssignedAmbulance(String amb) { this.assignedAmbulance.set(amb); }
}
