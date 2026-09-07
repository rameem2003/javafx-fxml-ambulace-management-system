package com.ambulance.dao;

import com.ambulance.db.Database;
import com.ambulance.model.EmergencyRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmergencyRequestDAO {

    public List<EmergencyRequest> findAll() {
        List<EmergencyRequest> list = new ArrayList<>();
        String sql = "SELECT id, patient_name, location, status, priority, time, assigned_ambulance FROM emergency_requests ORDER BY id";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch emergency requests: " + e.getMessage(), e);
        }
        return list;
    }

    public EmergencyRequest findById(String id) {
        String sql = "SELECT id, patient_name, location, status, priority, time, assigned_ambulance FROM emergency_requests WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find emergency request: " + e.getMessage(), e);
        }
        return null;
    }

    public void insert(EmergencyRequest r) {
        String sql = "INSERT INTO emergency_requests (id, patient_name, location, status, priority, time, assigned_ambulance) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getId());
            ps.setString(2, r.getPatientName());
            ps.setString(3, r.getLocation());
            ps.setString(4, r.getStatus());
            ps.setString(5, r.getPriority());
            ps.setString(6, r.getTime());
            ps.setString(7, r.getAssignedAmbulance());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert emergency request: " + e.getMessage(), e);
        }
    }

    public void update(EmergencyRequest r) {
        String sql = "UPDATE emergency_requests SET patient_name = ?, location = ?, status = ?, priority = ?, time = ?, assigned_ambulance = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getPatientName());
            ps.setString(2, r.getLocation());
            ps.setString(3, r.getStatus());
            ps.setString(4, r.getPriority());
            ps.setString(5, r.getTime());
            ps.setString(6, r.getAssignedAmbulance());
            ps.setString(7, r.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update emergency request: " + e.getMessage(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM emergency_requests WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete emergency request: " + e.getMessage(), e);
        }
    }

    private EmergencyRequest map(ResultSet rs) throws SQLException {
        return new EmergencyRequest(
                rs.getString("id"),
                rs.getString("patient_name"),
                rs.getString("location"),
                rs.getString("status"),
                rs.getString("priority"),
                rs.getString("time"),
                rs.getString("assigned_ambulance"));
    }
}
