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
- **Read:** Display student