package com.ambulance.dao;

import com.ambulance.db.Database;
import com.ambulance.model.Ambulance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmbulanceDAO {

    public List<Ambulance> findAll() {
        List<Ambulance> list = new ArrayList<>();
        String sql = "SELECT id, plate_number, type, status, location, driver_name FROM ambulances ORDER BY id";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch ambulances: " + e.getMessage(), e);
        }
        return list;
    }

    public Ambulance findById(String id) {
        String sql = "SELECT id, plate_number, type, status, location, driver_name FROM ambulances WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find ambulance: " + e.getMessage(), e);
        }
        return null;
    }

    public void insert(Ambulance a) {
        String sql = "INSERT INTO ambulances (id, plate_number, type, status, location, driver_name) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getId());
            ps.setString(2, a.getPlateNumber());
            ps.setString(3, a.getType());
            ps.setString(4, a.getStatus());
            ps.setString(5, a.getLocation());
            ps.setString(6, a.getDriverName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert ambulance: " + e.getMessage(), e);
        }
    }

    public void update(Ambulance a) {
        String sql = "UPDATE ambulances SET plate_number = ?, type = ?, status = ?, location = ?, driver_name = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getPlateNumber());
            ps.setString(2, a.getType());
            ps.setString(3, a.getStatus());
            ps.setString(4, a.getLocation());
            ps.setString(5, a.getDriverName());
            ps.setString(6, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update ambulance: " + e.getMessage(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM ambulances WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete ambulance: " + e.getMessage(), e);
        }
    }

    private Ambulance map(ResultSet rs) throws SQLException {
        return new Ambulance(
                rs.getString("id"),
                rs.getString("plate_number"),
                rs.getString("type"),
                rs.getString("status"),
                rs.getString("location"),
                rs.getString("driver_name"));
    }
}
