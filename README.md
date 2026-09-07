# Ambulance Management System

A JavaFX-based dashboard application for managing ambulance operations. Built with Maven, FXML layouts, and PostgreSQL for persistent data storage.

## Features

- **Dashboard** — Overview with stat cards, ambulance fleet table, emergency requests table, and activity log
- **Ambulances** — Full fleet listing with full CRUD (Create, Read, Update, Delete) operations
- **Emergencies** — Emergency request tracking with full CRUD operations
- **Drivers** — Driver management with full CRUD operations
- **Search** — Real-time filtering of data in every management view
- **Navigation** — Sidebar navigation between all views

## Tech Stack

- Java 17
- JavaFX 17
- FXML (layout)
- Maven (build)
- CSS (styling)
- PostgreSQL (persistence)

## Prerequisites

1. **Java 17** or later — [Download JDK](https://adoptium.net/)
2. **Maven 3.8+** — [Download Maven](https://maven.apache.org/download.cgi)
3. **PostgreSQL** — [Download PostgreSQL](https://www.postgresql.org/download/)

## Database Setup

1. Install PostgreSQL and start the service.
2. Create the database:
   ```sql
   CREATE DATABASE ambulance_db;
   ```
3. The application will automatically create the required tables (`ambulances`, `drivers`, `emergency_requests`) on startup.

### Database Configuration

Edit `src/main/resources/application.properties` to match your PostgreSQL setup:

```properties
db.host=localhost
db.port=5432
db.name=ambulance_db
db.user=postgres
db.password=postgres
```

## How to Run

### Using Maven (Recommended)

```bash
# 1. Clone or download the project
cd ambulance-management-system

# 2. Compile the project
mvn clean compile

# 3. Run the application
mvn javafx:run
```

### Build JAR and Run

```bash
# Build the JAR
mvn clean package

# Run (JavaFX requires module path for JAR execution)
java --module-path "C:/path-to-javafx/lib" --add-modules javafx.controls,javafx.fxml -jar target/ambulance-management-system-1.0-SNAPSHOT.jar
```

### Run from IntelliJ IDEA

1. Open the project folder in IntelliJ IDEA
2. IntelliJ should auto-detect the Maven project and import it
3. Right-click `App.java` → Run `App.main()` Or use the Maven tool window → Plugins → javafx → javafx:run

## Project Structure

```
ambulance-management-system/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── ambulance/
        │           ├── App.java                  (Main entry point)
        │           ├── db/
        │           │   └── Database.java         (Connection manager + schema init)
        │           ├── dao/
        │           │   ├── AmbulanceDAO.java     (CRUD for ambulances)
        │           │   ├── DriverDAO.java        (CRUD for drivers)
        │           │   └── EmergencyRequestDAO.java (CRUD for emergency requests)
        │           ├── controller/
        │           │   ├── DashboardController.java
        │           │   ├── AmbulanceController.java
        │           │   ├── EmergencyController.java
        │           │   └── DriverController.java
        │           └── model/
        │               ├── Ambulance.java
        │               ├── EmergencyRequest.java
        │               └── Driver.java
        └── resources/
            ├── css/
            │   └── style.css
            ├── fxml/
            │   ├── dashboard.fxml
            │   ├── ambulances.fxml
            │   ├── emergencies.fxml
            │   └── drivers.fxml
            └── application.properties            (DB connection config)
```

## CRUD Operations

Each management view (Ambulances, Drivers, Emergencies) provides:

- **Add** (+): Opens a dialog to create a new record
- **Edit** (✏️): Opens a dialog pre-filled with the selected record's data
- **Delete** (🗑): Removes the selected record after confirmation
- **Refresh** (🔄): Reloads data from the database
- **Search**: Real-time filtering of the table

## Troubleshooting

### "Failed to initialize database schema"
- Ensure PostgreSQL is running (`pg_ctl status` or check the Windows service)
- Verify credentials in `application.properties`
- Ensure the `ambulance_db` database exists

### "UnsupportedClassVersionError"
You are using a JDK older than 17. Install Java 17+.

### Windows: Module path errors when running JAR directly
Download the [JavaFX SDK](https://openjfx.io), extract it, and use:
```bash
java --module-path "C:\path\to\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml -jar target\ambulance-management-system-1.0-SNAPSHOT.jar
```
