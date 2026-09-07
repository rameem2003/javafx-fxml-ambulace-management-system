-- Optional: Run this in pgAdmin or psql against the 'ambulance_db' database
-- to populate sample data. The app auto-creates the tables on startup,
-- but you may seed sample records here if desired.

-- Ensure tables exist (app also does this on startup)
CREATE TABLE IF NOT EXISTS ambulances (
    id VARCHAR(20) PRIMARY KEY,
    plate_number VARCHAR(20) NOT NULL,
    type VARCHAR(60) NOT NULL,
    status VARCHAR(30) NOT NULL,
    location VARCHAR(120) NOT NULL,
    driver_name VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS drivers (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    license_no VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    assigned_ambulance VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS emergency_requests (
    id VARCHAR(20) PRIMARY KEY,
    patient_name VARCHAR(120) NOT NULL,
    location VARCHAR(160) NOT NULL,
    status VARCHAR(30) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    time VARCHAR(20) NOT NULL,
    assigned_ambulance VARCHAR(30) NOT NULL
);

-- Sample ambulances
INSERT INTO ambulances (id, plate_number, type, status, location, driver_name) VALUES
('AMB-001', 'ABC-1234', 'Basic Life Support', 'Available', 'Central Station', 'John Smith'),
('AMB-002', 'ABC-5678', 'Advanced Life Support', 'On Mission', 'Downtown', 'Mike Johnson'),
('AMB-003', 'ABC-9012', 'Basic Life Support', 'Available', 'North Hub', 'Sarah Williams'),
('AMB-004', 'ABC-3456', 'Patient Transport', 'On Mission', 'East District', 'David Brown')
ON CONFLICT (id) DO NOTHING;

-- Sample drivers
INSERT INTO drivers (id, name, phone, license_no, status, assigned_ambulance) VALUES
('DRV-001', 'John Smith', '(555) 101-2001', 'LIC-1001', 'On Duty', 'AMB-001'),
('DRV-002', 'Mike Johnson', '(555) 101-2002', 'LIC-1002', 'On Duty', 'AMB-002'),
('DRV-003', 'Sarah Williams', '(555) 101-2003', 'LIC-1003', 'On Duty', 'AMB-003')
ON CONFLICT (id) DO NOTHING;

-- Sample emergency requests
INSERT INTO emergency_requests (id, patient_name, location, status, priority, time, assigned_ambulance) VALUES
('REQ-001', 'Alice Johnson', '45 Oak Street', 'In Progress', 'HIGH', '12:30 PM', 'AMB-002'),
('REQ-002', 'Bob Williams', '78 Pine Avenue', 'Dispatched', 'CRITICAL', '12:15 PM', 'AMB-007'),
('REQ-003', 'Carol Davis', '12 Maple Drive', 'Completed', 'MEDIUM', '11:45 AM', 'AMB-001')
ON CONFLICT (id) DO NOTHING;
