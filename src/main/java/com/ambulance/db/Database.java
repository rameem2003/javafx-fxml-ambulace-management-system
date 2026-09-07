package com.ambulance.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {

    private static final Properties props = new Properties();
    private static final String DB_HOST;
    private static final String DB_PORT;
    private static final String DB_NAME;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        try (InputStream in = Database.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in != null) {
                props.load(in);
            }
            String host = props.getProperty("db.host", "localhost");
            String port = props.getProperty("db.port", "5432");
            String name = props.getProperty("db.name", "ambulance_db");
            DB_HOST = host;
            DB_PORT = port;
            DB_NAME = name;
            DB_USER = props.getProperty("db.user", "postgres");
            DB_PASSWORD = props.getProperty("db.password", "postgres");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
    }

    private static final String URL =
            "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }

    public static void initialize() {
        String createAmbulances =
                "CREATE TABLE IF NOT EXISTS ambulances (" +
                        "id VARCHAR(20) PRIMARY KEY, " +
                        "plate_number VARCHAR(20) NOT NULL, " +
                        "type VARCHAR(60) NOT NULL, " +
                        "status VARCHAR(30) NOT NULL, " +
                        "location VARCHAR(120) NOT NULL, " +
                        "driver_name VARCHAR(120) NOT NULL)";

        String createDrivers =
                "CREATE TABLE IF NOT EXISTS drivers (" +
                        "id VARCHAR(20) PRIMARY KEY, " +
                        "name VARCHAR(120) NOT NULL, " +
                        "phone VARCHAR(30) NOT NULL, " +
                        "license_no VARCHAR(30) NOT NULL, " +
                        "status VARCHAR(30) NOT NULL, " +
                        "assigned_ambulance VARCHAR(30) NOT NULL)";

        String createEmergencies =
                "CREATE TABLE IF NOT EXISTS emergency_requests (" +
                        "id VARCHAR(20) PRIMARY KEY, " +
                        "patient_name VARCHAR(120) NOT NULL, " +
                        "location VARCHAR(160) NOT NULL, " +
                        "status VARCHAR(30) NOT NULL, " +
                        "priority VARCHAR(20) NOT NULL, " +
                        "time VARCHAR(20) NOT NULL, " +
                        "assigned_ambulance VARCHAR(30) NOT NULL)";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createAmbulances);
            stmt.executeUpdate(createDrivers);
            stmt.executeUpdate(createEmergencies);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema: " + e.getMessage(), e);
        }
    }

    public static String getUrl() {
        return URL;
    }

    public static String getUser() {
        return DB_USER;
    }
}
