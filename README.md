# University Student Management System

A modern desktop application built with JavaFX and Java 25 following a clean Model-Service-Controller-View (MSCV) architecture. The application provides an intuitive Dark Mode interface for managing student records, tracking academic performance, and monitoring institutional statistics with reliable flat-file data persistence.

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

The University Student Management System was developed as part of the Programming 3 coursework. It adapts and extends the architectural patterns of the reference management system into a robust academic management desktop tool. The interface utilizes a minimal, modern Dark Mode palette (`#111827`, `#0B1220`, `#1F2937`, `#3B82F6`) designed for clarity, usability, and responsiveness.

---

## Main Features

1. **Dashboard Analytics:**
   - Real-time calculation of key institutional metrics:
     - **Total Enrolled Students**
     - **Average Cumulative GPA** (formatted to two decimal places)
     - **Distinct Academic Departments Count**
   - **Recent Students Panel:** Quick-view table showing the latest enrolled student records.

2. **Student Directory & Management (CRUD):**
   - **Create:** Modal form to add new students with automatic ID duplication checks and comprehensive field validation.
   - **Read:** Formatted table displaying Student ID, Full Name, Email, Department, Academic Year, and GPA.
   - **Update:** Pre-filled edit modal allowing safe record updates while preserving the canonical Student ID.
   - **Delete:** Guarded deletion with confirmation prompts to prevent accidental record loss.

3. **Multi-Field Live Search:**
   - Real-time filtering against Student ID, Name, and Department simultaneously.
   - Preserves underlying selection and full dataset statistics during active search filters.

4. **Robust Validation & Dialogs:**
   - Comprehensive input sanitization rejecting empty inputs, invalid numbers, and non-finite GPA values.
   - GPA range restriction between $0.00$ and $4.00$.
   - CSV delimiter protection against commas and line breaks.
   - Dark-themed alert dialogs for informational feedback, validation warnings, error reports, and confirmation requests.

---

## Architecture Overview

The system strictly follows the layered **Model-Service-Controller-View (MSCV)** design pattern:

```
┌─────────────────────────────────────────────────────────────┐
│                          VIEW                               │
│  (StudentView, AddStudentView, EditStudentView)             │
│  Pure JavaFX Programmatic UI • No FXML • Layout & Controls   │
└──────────────────────────────▲──────────────────────────────┘
                               │ User Events / Updates
┌──────────────────────────────▼──────────────────────────────┐
│                       CONTROLLER                            │
│  (StudentController)                                        │
│  Event Handling • Validation • ObservableList Coordination  │
└──────────────────────────────▲──────────────────────────────┘
                               │ Business Operations
┌──────────────────────────────▼──────────────────────────────┐
│                        SERVICE                              │
│  (StudentService)                                           │
│  CRUD Logic • Parsing • CSV Formatting • I/O Exception Mgmt │
└──────────────────────────────▲──────────────────────────────┘
                               │ Serialization
┌──────────────────────────────▼──────────────────────────────┐
│                         MODEL                               │
│  (Student)                                                  │
│  Immutable Domain Object • JavaBean Getters                 │
└─────────────────────────────────────────────────────────────┘
                               │
                        [Data/students.txt]
```

- **Model (`sample.model.Student`):** An immutable entity representing a student record with private final fields, all-arguments constructor, standard JavaBean getters, and a readable `toString()` implementation.
- **Service (`sample.service.StudentService`):** Manages file persistence, record parsing (`split(",", -1)`), data formatting, and file integrity operations on `Data/students.txt` using UTF-8 `BufferedReader` and `BufferedWriter`.
- **Controller (`sample.controller.StudentController`):** Owns UI event listeners, coordinates the `ObservableList<Student>`, applies search filters, computes summary statistics, triggers modal dialogs, and maps service exceptions to styled user alerts.
- **View (`sample.view.*`):** Builds the programmatic JavaFX layout (`StudentView` for main dashboard and directory, `AddStudentView` and `EditStudentView` for modal forms) without FXML dependencies.

---

## Project Structure

```text
University-Management-System/
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
├── Data/
│   └── students.txt                  # Flat-file UTF-8 CSV database
├── src/
│   └── sample/
│       ├── Main.java                 # Application entry point
│       ├── controller/
│       │   └── StudentController.java # Coordinator & event handler
│       ├── model/
│       │   └── Student.java          # Immutable domain entity
│       ├── service/
│       │   └── StudentService.java   # File I/O & persistence service
│       └── view/
│           ├── AddStudentView.java   # Dark modal for student creation
│           ├── EditStudentView.java  # Dark modal for student editing
│           └── StudentView.java      # Main dashboard & directory UI
├── .gitignore
├── mvnw                              # Unix Maven wrapper script
├── mvnw.cmd                          # Windows Maven wrapper script
├── pom.xml                           # Project Object Model configuration
└── README.md                         # Documentation
```

---

## Technologies Used

- **Java SE Development Kit:** JDK 25 (OpenJDK / Eclipse Temurin)
- **JavaFX:** OpenJFX 27.0.1 (`javafx-controls`)
- **Build System:** Apache Maven 3.9.9 (via embedded Maven Wrapper)
- **Plugin:** `javafx-maven-plugin` (0.0.8)

---

## Prerequisites

- **Java Development Kit:** JDK 25 installed and configured on your system.
- **Environment:** No standalone Maven installation is required; the included Maven Wrapper (`mvnw.cmd`) handles Maven provisioning automatically.

---

## Build and Run Guide

All commands should be executed from the project root directory in a Windows Command Prompt or PowerShell:

1. **Resolve Dependencies:**
   ```cmd
   .\mvnw.cmd dependency:resolve
   ```

2. **Clean and Compile:**
   ```cmd
   .\mvnw.cmd clean compile
   ```

3. **Launch Application:**
   ```cmd
   .\mvnw.cmd javafx:run
   ```

---

## Data Storage

Student data is persisted locally in `Data/students.txt` using standard comma-separated values (CSV) encoded in UTF-8:

```text
studentId,name,email,department,academicYear,gpa
```

*Example records:*
```text
S101,Alexander Wright,a.wright@university.edu,Computer Science,Year 1,3.85
S102,Sophia Martinez,s.martinez@university.edu,Information Systems,Year 2,3.62
S103,Liam Johnson,l.johnson@university.edu,Software Engineering,Year 3,3.40
```

- When adding a record, `StudentService` appends directly to the file.
- When updating or deleting records, the service rewrites the file atomically to maintain referential integrity.
- If the `Data/` directory or file is missing, the service creates them automatically upon initialization.

---

## Team Members

| Name | Student ID | Academic Role / Contribution |
| :--- | :--- | :--- |
| *[Student Name 1]* | *[Student ID 1]* | *[Contribution / Module]* |
| *[Student Name 2]* | *[Student ID 2]* | *[Contribution / Module]* |
| *[Student Name 3]* | *[Student ID 3]* | *[Contribution / Module]* |
| *[Student Name 4]* | *[Student ID 4]* | *[Contribution / Module]* |
| *[Student Name 5]* | *[Student ID 5]* | *[Contribution / Module]* |
