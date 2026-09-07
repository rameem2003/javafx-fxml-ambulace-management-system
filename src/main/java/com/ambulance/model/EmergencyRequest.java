package com.ambulance.model;

import javafx.beans.property.*;

public class EmergencyRequest {

    private final StringProperty id;
    private final StringProperty patientName;
    private final StringProperty location;
    private final StringProperty status;
    private final StringProperty priority;
    private final StringProperty time;
    private final StringProperty assignedAmbulance;

    public EmergencyRequest(String id, String patientName, String location, String status, String priority, String time, String assignedAmbulance) {
        this.id = new SimpleStringProperty(id);
        this.patientName = new SimpleStringProperty(patientName);
        this.location = new SimpleStringProperty(location);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
        this.time = new SimpleStringProperty(time);
        this.assignedAmbulance = new SimpleStringProperty(assignedAmbulance);
    }

    public StringProperty idProperty() { return id; }
    public StringProperty patientNameProperty() { return patientName; }
    public StringProperty locationProperty() { return location; }
    public StringProperty statusProperty() { return status; }
    public StringProperty priorityProperty() { return priority; }
    public StringProperty timeProperty() { return time; }
    public StringProperty assignedAmbulanceProperty() { return assignedAmbulance; }

    public String getId() { return id.get(); }
    public String getPatientName() { return patientName.get(); }
    public String getLocation() { return location.get(); }
    public String getStatus() { return status.get(); }
    public String getPriority() { return priority.get(); }
    public String getTime() { return time.get(); }
    public String getAssignedAmbulance() { return assignedAmbulance.get(); }

    public void setId(String id) { this.id.set(id); }
    public void setPatientName(String patientName) { this.patientName.set(patientName); }
    public void setLocation(String location) { this.location.set(location); }
    public void setStatus(String status) { this.status.set(status); }
    public void setPriority(String priority) { this.priority.set(priority); }
    public void setTime(String time) { this.time.set(time); }
    public void setAssignedAmbulance(String amb) { this.assignedAmbulance.set(amb); }
}
