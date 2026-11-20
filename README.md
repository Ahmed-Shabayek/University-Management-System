
# University Student Management System

A modern desktop application built with JavaFX and Java 25, following a clean Model-Service-Controller-View (MSCV) architecture. The application provides an intuitive Dark Mode interface for managing student records, tracking academic performance, and monitoring institutional statistics with reliable flat-file data persistence.

---

## Table of Contents

- [Overview](#overview)
- [Main Features](#main-features)
- [Architecture Overview](#architecture-overview)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Build and Run Guide](#build-and-run-guide)
- [Data Storage](#data-storage)
- [Team Members](#team-members)

---

## Overview

The University Student Management System was developed as part of the Programming 3 coursework. It adapts and extends the architectural patterns of the reference management system into a practical academic management desktop application.

The interface uses a minimal, modern Dark Mode color palette (`#111827`, `#0B1220`, `#1F2937`, `#3B82F6`) designed for clarity and usability.

---

## Main Features

### 1. Dashboard Analytics

- Real-time calculation of key statistics:
  - Total Enrolled Students.
  - Average Cumulative GPA (formatted to two decimal places).
  - Number of Distinct Academic Departments.
- **Recent Students Panel:** A quick-view table showing recently enrolled student records.

### 2. Student Directory and Management (CRUD)

- **Create:** Add new students through a modal form with duplicate ID checks and input validation.
- **Read:** Display student records in a formatted table containing Student ID, Full Name, Email, Department, Academic Year, and GPA.
- **Update:** Edit existing student records through a pre-filled modal form while preserving the original Student ID.
- **Delete:** Remove student records with confirmation prompts to prevent accidental deletion.

### 3. Multi-Field Live Search

- Real-time filtering by Student ID, Name, and Department.
- Keeps the complete student dataset available while applying search filters.
- Dashboard statistics are calculated using the full dataset, not just filtered results.

### 4. Validation and Dialogs

- Rejects empty inputs and invalid numeric values.
- Ensures GPA values are finite and within the range of 0.00 to 4.00.
- Prevents commas and line breaks in fields to protect the CSV file format.
- Provides Dark Mode information, warning, error, and confirmation dialogs.

---

## Architecture Overview

The system follows the **Model-Service-Controller-View (MSCV)** design pattern.

```text
┌─────────────────────────────────────────────────────────────┐
│                           VIEW                              │
│       StudentView, AddStudentView, EditStudentView          │
│          JavaFX UI • Layout • Controls • Dialogs             │
└──────────────────────────────▲──────────────────────────────┘
                               │ User Events / Updates
┌──────────────────────────────▼──────────────────────────────┐
│                       CONTROLLER                            │
│                     StudentController                       │
│       Event Handling • Validation • UI Coordination         │
└──────────────────────────────▲──────────────────────────────┘
                               │ Business Operations
┌──────────────────────────────▼──────────────────────────────┐
│                         SERVICE                             │
│                      StudentService                         │
│             CRUD Operations • File I/O • Parsing             │
└──────────────────────────────▲──────────────────────────────┘
                               │ Serialization
┌──────────────────────────────▼──────────────────────────────┐
│                          MODEL                              │
│                          Student                            │
│                Immutable Student Data Object                │
└─────────────────────────────────────────────────────────────┘
                               │
                        Data/students.txt
```

- **Model (`sample.model.Student`):** An immutable entity representing a student record, with private final fields, an all-arguments constructor, standard JavaBean getters, and a readable `toString()` implementation.
- **Service (`sample.service.StudentService`):** Handles file persistence, record parsing, data formatting, and file operations on `Data/students.txt` using UTF-8 file I/O.
- **Controller (`sample.controller.StudentController`):** Manages UI event listeners, coordinates the `ObservableList<Student>`, applies search filters, calculates dashboard statistics, handles modal dialogs, and displays user alerts.
- **View (`sample.view`):** Builds the JavaFX interface programmatically without FXML. `StudentView` handles the main dashboard and directory, while `AddStudentView` and `EditStudentView` provide modal forms.

---

## Project Structure

```text
University-Management-System/
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
├── Data/
│   └── students.txt                  # UTF-8 CSV student database
├── src/
│   └── sample/
│       ├── Main.java                 # Application entry point
│       ├── controller/
│       │   └── StudentController.java
│       ├── model/
│       │   └── Student.java
│       ├── service/
│       │   └── StudentService.java
│       └── view/
│           ├── AddStudentView.java
│           ├── EditStudentView.java
│           └── StudentView.java
├── .gitignore
├── mvnw                              # Unix Maven wrapper script
├── mvnw.cmd                          # Windows Maven wrapper script
├── pom.xml                            # Maven project configuration
└── README.md                          # Project documentation
```

---

## Technologies Used

- **Java SE Development Kit:** JDK 25 (OpenJDK / Eclipse Temurin).
- **JavaFX:** OpenJFX 27.0.1 (`javafx-controls`).
- **Build System:** Apache Maven 3.9.9 (through the included Maven Wrapper).
- **JavaFX Maven Plugin:** Version 0.0.8.

---

## Prerequisites

- Java Development Kit (JDK) 25 installed and configured.
- Windows Command Prompt or PowerShell for the commands below.
- No standalone Maven installation is required. The included Maven Wrapper handles Maven provisioning automatically.

---

## Build and Run Guide

Run all commands from the project root directory.

**1. Resolve Dependencies**

```powershell
.\mvnw.cmd dependency:resolve
```

**2. Clean and Compile**

```powershell
.\mvnw.cmd clean compile
```

**3. Launch the Application**

```powershell
.\mvnw.cmd javafx:run
```

---

## Data Storage

Student records are stored locally in `Data/students.txt` using comma-separated values (CSV) encoded in UTF-8.

Each record follows this format:

```text
studentId,name,email,department,academicYear,gpa
```

Example records:

```text
S101,Alexander Wright,a.wright@university.edu,Computer Science,Year 1,3.85
S102,Sophia Martinez,s.martinez@university.edu,Information Systems,Year 2,3.62
S103,Liam Johnson,l.johnson@university.edu,Software Engineering,Year 3,3.40
```

- Adding a student appends a new record to the file.
- Updating or deleting a student rewrites the file with the updated dataset.
- If the `Data/` directory or data file does not exist, the service creates it automatically.

---

## Team Members

### 1. Ahmed Shabayek — Team Lead & Integration

- **Academic ID:** [To be added]
- Responsible for `Main.java` and `StudentController.java`.
- Integrates team contributions and reviews Pull Requests.
- Tests the complete application and prepares the final release.
- Manages GitHub and coordinates task distribution.

### 2. Ahmed Youssef — Model & Data

- **Academic ID:** [To be added]
- Responsible for `Student.java`, `StudentService.java`, and `Data/students.txt`.
- Handles the Student model and file-based data operations, including loading, adding, updating, deleting, and saving student records.

### 3. Hamdy Madkour — Main Dashboard UI

- **Academic ID:** [To be added]
- Responsible for `StudentView.java`.
- Designs the main application interface, sidebar, dashboard statistics, and TableView.

### 4. Bassem — Dialogs UI

- **Academic ID:** [To be added]
- Responsible for `AddStudentView.java` and `EditStudentView.java`.
- Designs the Add and Edit Student dialogs, including input fields and action buttons.

### 5. Ahmed Khalaf — Quality Assurance & Documentation

- **Academic ID:** [To be added]
- Responsible for `README.md` and project documentation.
- Prepares sample data and testing scenarios.
- Reviews functionality and verifies that the application meets project requirements.
- Handles testing responsibilities and test scenario preparation.
