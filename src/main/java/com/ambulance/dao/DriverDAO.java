package com.ambulance.dao;

import com.ambulance.db.Database;
import com.ambulance.model.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {

    public List<Driver> findAll() {
        List<Driver> list = new ArrayList<>();
        String sql = "SELECT id, name, phone, license_no, status, assigned_ambulance FROM drivers ORDER BY id";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch drivers: " + e.getMessage(), e);
        }
        return list;
    }

    public Driver findById(String id) {
        String sql = "SELECT id, name, phone, license_no, status, assigned_ambulance FROM drivers WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find driver: " + e.getMessage(), e);
        }
        return null;
    }

    public void insert(Driver d) {
        String sql = "INSERT INTO drivers (id, name, phone, license_no, status, assigned_ambulance) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getId());
            ps.setString(2, d.getName());
            ps.setString(3, d.getPhone());
            ps.setString(4, d.getLicenseNo());
            ps.setString(5, d.getStatus());
            ps.setString(6, d.getAssignedAmbulance());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert driver: " + e.getMessage(), e);
        }
    }

    public void update(Driver d) {
        String sql = "UPDATE drivers SET name = ?, phone = ?, license_no = ?, status = ?, assigned_ambulance = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getPhone());
            ps.setString(3, d.getLicenseNo());
            ps.setString(4, d.getStatus());
            ps.setString(5, d.getAssignedAmbulance());
            ps.setString(6, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update driver: " + e.getMessage(), e);
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM drivers WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete driver: " + e.getMessage(), e);
        }
    }

    private Driver map(ResultSet rs) throws SQLException {
        return new Driver(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("license_no"),
                rs.getString("status"),
                rs.getString("assigned_ambulance"));
    }
}
