# Ambulance Management System

A JavaFX-based dashboard application for managing ambulance operations. Built with Maven, FXML layouts, and static data (no database required).

## Features

- **Dashboard** — Overview with stat cards, ambulance fleet table, emergency requests table, and activity log
- **Ambulances** — Full fleet listing with ID, plate number, type, status, driver, and location
- **Emergencies** — Emergency request tracking with patient info, priority levels, status, and ambulance assignments
- **Drivers** — Driver management with contact info, license numbers, duty status, and ambulance assignments
- **Navigation** — Sidebar navigation between all views

## Tech Stack

- Java 17
- JavaFX 17
- FXML (layout)
- Maven (build)
- CSS (styling)

## Prerequisites

1. **Java 17** or later — [Download JDK](https://adoptium.net/)
2. **Maven 3.8+** — [Download Maven](https://maven.apache.org/download.cgi)

Verify installations:

```bash
java -version
mvn -version
```

## How to Run

### Option 1: Using Maven (Recommended)

```bash
# 1. Clone or download the project
cd ambulance-management-system

# 2. Compile the project
mvn clean compile

# 3. Run the application
mvn javafx:run
```

### Option 2: Using Maven Exec Plugin

```bash
mvn clean compile exec:java -Dexec.mainClass="com.ambulance.App"
```

### Option 3: Build JAR and Run

```bash
# Build the JAR
mvn clean package

# Run (JavaFX requires module path for JAR execution)
java --module-path "C:/path-to-javafx/lib" --add-modules javafx.controls,javafx.fxml -jar target/ambulance-management-system-1.0-SNAPSHOT.jar
```

> **Note:** For Option 3, you need to download JavaFX SDK from https://openjfx.io and point `--module-path` to the `lib` folder.

### Option 4: Run from IntelliJ IDEA

1. Open the project folder in IntelliJ IDEA
2. IntelliJ should auto-detect the Maven project and import it
3. Right-click `App.java` → Run `App.main()`
4. Or use the Maven tool window → Plugins → javafx → javafx:run

### Option 5: Run from VS Code

1. Install the "Extension Pack for Java" and "JavaFX Support" extensions
2. Open the project folder
3. Open `App.java` and click Run

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
            └── fxml/
                ├── dashboard.fxml
                ├── ambulances.fxml
                ├── emergencies.fxml
                └── drivers.fxml
```

## Troubleshooting

### "UnsupportedClassVersionError"
You are using a JDK older than 17. Install Java 17+.

### " javafx.fxml.LoadException: Location is not set"
Make sure you run with Maven (`mvn javafx:run`) so resources are on the classpath.

### Maven can't find javafx
JavaFX is not bundled with JDK 17+. The `pom.xml` already includes JavaFX dependencies — just run `mvn clean compile` first.

### Windows: Module path errors when running JAR directly
Download the [JavaFX SDK](https://openjfx.io), extract it, and use:
```bash
java --module-path "C:\path\to\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml -jar target\ambulance-management-system-1.0-SNAPSHOT.jar
```

## Notes

- All data is **static** — hardcoded in controller `initialize()` methods for demonstration
- No database is required
- The UI updates at runtime but changes are not persisted
- Navigation works by loading different FXML files into a new Scene
