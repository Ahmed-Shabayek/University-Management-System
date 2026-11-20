package sample.service;

import sample.model.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final String filePath = "Data/students.txt";

    public StudentService() {
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    public List<Student> loadStudents() throws IOException {
        File dataFile = new File(filePath);

        if (!dataFile.exists()) {
            ensureFileExists(dataFile);
            return new ArrayList<>();
        }

        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);

                if (parts.length != 6) {
                    throw new IOException("Invalid record format at line " + lineNumber +
                            ": expected 6 fields but found " + parts.length + " in line: " + line);
                }

                String studentId = parts[0].trim();
                String name = parts[1].trim();
                String email = parts[2].trim();
                String department = parts[3].trim();
                String academicYear = parts[4].trim();

                double gpa;
                try {
                    gpa = Double.parseDouble(parts[5].trim());
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid GPA number format at line " + lineNumber +
                            ": '" + parts[5] + "'", e);
                }

                if (!Double.isFinite(gpa) || gpa < 0.0 || gpa > 4.0) {
                    throw new IOException("Invalid GPA value at line " + lineNumber +
                            ": GPA must be a finite number between 0.0 and 4.0, but found " + gpa);
                }

                students.add(new Student(studentId, name, email, department, academicYear, gpa));
            }
        }

        return students;
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    public void addStudent(Student student) throws IOException {
        validateStudent(student);
        Student normalized = normalizeStudent(student);

        List<Student> students = loadStudents();

        for (Student existing : students) {
            if (existing.getStudentId().trim().equals(normalized.getStudentId())) {
                throw new IllegalArgumentException("A student with ID '" + normalized.getStudentId() + "' already exists.");
            }
        }

        File dataFile = new File(filePath);
        ensureFileExists(dataFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, StandardCharsets.UTF_8, true))) {
            writer.write(formatStudent(normalized));
            writer.newLine();
        }
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    public void updateStudent(String studentId, Student updatedStudent) throws IOException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }

        validateStudent(updatedStudent);
        String cleanId = studentId.trim();
        Student normalized = normalizeStudent(updatedStudent);

        if (!cleanId.equals(normalized.getStudentId())) {
            throw new IllegalArgumentException("Updating must preserve the original Student ID. Expected: '" +
                    cleanId + "', but received: '" + normalized.getStudentId() + "'.");
        }

        List<Student> students = loadStudents();
        boolean found = false;

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().trim().equals(cleanId)) {
                students.set(i, normalized);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Student with ID '" + cleanId + "' not found.");
        }

        saveStudents(students);
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    public void deleteStudent(String studentId) throws IOException {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty.");
        }

        String cleanId = studentId.trim();
        List<Student> students = loadStudents();
        boolean found = false;

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().trim().equals(cleanId)) {
                students.remove(i);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Student with ID '" + cleanId + "' not found.");
        }

        saveStudents(students);
    }

    // =========================================================
    // SAVE ALL STUDENTS
    // =========================================================

    private void saveStudents(List<Student> students) throws IOException {
        File dataFile = new File(filePath);
        ensureFileExists(dataFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile, StandardCharsets.UTF_8))) {
            for (Student student : students) {
                writer.write(formatStudent(normalizeStudent(student)));
                writer.newLine();
            }
        }
    }

    // =========================================================
    // HELPER METHODS
    // =========================================================

    private void ensureFileExists(File dataFile) throws IOException {
        File parent = dataFile.getParentFile();
        if (parent != null && !parent.exists()) {
            boolean created = parent.mkdirs();
            if (!created && !parent.exists()) {
                throw new IOException("Failed to create directory: " + parent.getAbsolutePath());
            }
        }
        if (!dataFile.exists()) {
            boolean created = dataFile.createNewFile();
            if (!created && !dataFile.exists()) {
                throw new IOException("Failed to create data file: " + dataFile.getAbsolutePath());
            }
        }
    }

    private Student normalizeStudent(Student s) {
        return new Student(
                s.getStudentId().trim(),
                s.getName().trim(),
                s.getEmail().trim(),
                s.getDepartment().trim(),
                s.getAcademicYear().trim(),
                s.getGpa()
        );
    }

    private String formatStudent(Student s) {
        return s.getStudentId() + "," +
                s.getName() + "," +
                s.getEmail() + "," +
                s.getDepartment() + "," +
                s.getAcademicYear() + "," +
                s.getGpa();
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }

        validateTextField("Student ID", student.getStudentId());
        validateTextField("Name", student.getName());
        validateTextField("Email", student.getEmail());
        validateTextField("Department", student.getDepartment());
        validateTextField("Academic Year", student.getAcademicYear());

        if (!Double.isFinite(student.getGpa()) || student.getGpa() < 0.0 || student.getGpa() > 4.0) {
            throw new IllegalArgumentException("GPA must be a finite number between 0.0 and 4.0, but received " + student.getGpa());
        }
    }

    private void validateTextField(String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty.");
        }
        if (value.contains(",") || value.contains("\n") || value.contains("\r")) {
            throw new IllegalArgumentException(fieldName + " cannot contain commas or line breaks: '" + value + "'");
        }
    }
}
