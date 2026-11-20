package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import sample.model.Student;
import sample.service.StudentService;
import sample.view.AddStudentView;
import sample.view.EditStudentView;
import sample.view.StudentView;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StudentController {

    private final StudentView view;
    private final StudentService service;

    private final ObservableList<Student> allStudents;

    public StudentController() {

        view = new StudentView();

        service = new StudentService();

        allStudents = FXCollections.observableArrayList();

        loadStudents();

        view.getStudentTable()
                .setItems(allStudents);

        setupNavigation();

        setupSearch();

        setupAddButton();

        setupEditButton();

        setupDeleteButton();
    }

    // =========================================================
    // NAVIGATION
    // =========================================================

    private void setupNavigation() {

        view.getDashboardButton()
                .setOnAction(event -> {

                    view.showDashboardPage();
                });

        view.getStudentsButton()
                .setOnAction(event -> {

                    view.showStudentsPage();
                });
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    private void loadStudents() {

        try {

            List<Student> students =
                    service.loadStudents();

            allStudents.setAll(students);

            updateStatistics();

        } catch (IOException e) {

            showErrorAlert(
                    "Load Error",
                    "Failed to load student records:\n" + e.getMessage()
            );
        }
    }

    private void refreshData() {

        loadStudents();

        filterStudents(view.getSearchField().getText());

        view.getStudentTable().refresh();
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void setupSearch() {

        view.getSearchField()
                .textProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {

                            filterStudents(newValue);
                        }
                );
    }

    private void filterStudents(String searchText) {

        if (searchText == null) {
            searchText = "";
        }

        String search =
                searchText
                        .toLowerCase()
                        .trim();

        if (search.isEmpty()) {

            view.getStudentTable()
                    .setItems(allStudents);

            return;
        }

        ObservableList<Student> filtered =
                FXCollections.observableArrayList();

        for (Student student : allStudents) {

            if (student.getStudentId()
                    .toLowerCase()
                    .contains(search)
                    || student.getName()
                    .toLowerCase()
                    .contains(search)
                    || student.getDepartment()
                    .toLowerCase()
                    .contains(search)) {

                filtered.add(student);
            }
        }

        view.getStudentTable()
                .setItems(filtered);
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    private void setupAddButton() {

        view.getAddButton()
                .setOnAction(event -> {

                    addStudent();
                });
    }

    private void addStudent() {

        AddStudentView addView = new AddStudentView();

        addView.getSaveButton().setOnAction(event -> {

            saveNewStudent(addView);
        });

        addView.getCancelButton().setOnAction(event -> {

            addView.getStage().close();
        });

        addView.showAndWait();
    }

    private void saveNewStudent(AddStudentView addView) {

        String id = addView.getStudentIdField().getText().trim();
        String name = addView.getNameField().getText().trim();
        String email = addView.getEmailField().getText().trim();
        String department = addView.getDepartmentField().getText().trim();
        String academicYear = addView.getAcademicYearField().getText().trim();
        String gpaText = addView.getGpaField().getText().trim();

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()
                || department.isEmpty() || academicYear.isEmpty() || gpaText.isEmpty()) {

            showWarningAlert(
                    "Validation Error",
                    "All fields are required. Please fill in all fields."
            );
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(gpaText);
        } catch (NumberFormatException e) {
            showWarningAlert(
                    "Validation Error",
                    "GPA must be a valid number."
            );
            return;
        }

        if (!Double.isFinite(gpa) || gpa < 0.0 || gpa > 4.0) {
            showWarningAlert(
                    "Validation Error",
                    "GPA must be a finite number between 0.0 and 4.0."
            );
            return;
        }

        Student student = new Student(
                id,
                name,
                email,
                department,
                academicYear,
                gpa
        );

        try {

            service.addStudent(student);

            refreshData();

            addView.getStage().close();

            showInfoAlert(
                    "Success",
                    "Student added successfully."
            );

        } catch (IllegalArgumentException e) {

            showWarningAlert(
                    "Invalid Data",
                    e.getMessage()
            );

        } catch (IOException e) {

            showErrorAlert(
                    "File Error",
                    "Failed to save student record:\n" + e.getMessage()
            );
        }
    }

    // =========================================================
    // EDIT STUDENT
    // =========================================================

    private void setupEditButton() {

        view.getEditButton()
                .setOnAction(event -> {

                    editSelectedStudent();
                });
    }

    private void editSelectedStudent() {

        Student selected =
                view.getStudentTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showWarningAlert(
                    "No Student Selected",
                    "Please select a student from the table first."
            );
            return;
        }

        EditStudentView editView = new EditStudentView(selected);

        editView.getSaveButton().setOnAction(event -> {

            saveEditedStudent(selected, editView);
        });

        editView.getCancelButton().setOnAction(event -> {

            editView.getStage().close();
        });

        editView.showAndWait();
    }

    private void saveEditedStudent(Student selected, EditStudentView editView) {

        String name = editView.getNameField().getText().trim();
        String email = editView.getEmailField().getText().trim();
        String department = editView.getDepartmentField().getText().trim();
        String academicYear = editView.getAcademicYearField().getText().trim();
        String gpaText = editView.getGpaField().getText().trim();

        if (name.isEmpty() || email.isEmpty() || department.isEmpty()
                || academicYear.isEmpty() || gpaText.isEmpty()) {

            showWarningAlert(
                    "Validation Error",
                    "All fields are required. Please fill in all fields."
            );
            return;
        }

        double gpa;
        try {
            gpa = Double.parseDouble(gpaText);
        } catch (NumberFormatException e) {
            showWarningAlert(
                    "Validation Error",
                    "GPA must be a valid number."
            );
            return;
        }

        if (!Double.isFinite(gpa) || gpa < 0.0 || gpa > 4.0) {
            showWarningAlert(
                    "Validation Error",
                    "GPA must be a finite number between 0.0 and 4.0."
            );
            return;
        }

        Student updatedStudent = new Student(
                selected.getStudentId(),
                name,
                email,
                department,
                academicYear,
                gpa
        );

        try {

            service.updateStudent(selected.getStudentId(), updatedStudent);

            refreshData();

            editView.getStage().close();

            showInfoAlert(
                    "Success",
                    "Student updated successfully."
                    );

        } catch (IllegalArgumentException e) {

            showWarningAlert(
                    "Invalid Data",
                    e.getMessage()
            );

        } catch (IOException e) {

            showErrorAlert(
                    "File Error",
                    "Failed to update student record:\n" + e.getMessage()
            );
        }
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    private void setupDeleteButton() {

        view.getDeleteButton()
                .setOnAction(event -> {

                    deleteSelectedStudent();
                });
    }

    private void deleteSelectedStudent() {

        Student selected =
                view.getStudentTable()
                        .getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showWarningAlert(
                    "No Student Selected",
                    "Please select a student from the table first."
            );
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);
        confirm.setContentText(
                "Are you sure you want to delete student:\n" +
                        selected.getName() + " (ID: " + selected.getStudentId() + ")?"
        );

        styleAlert(confirm);

        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {

                service.deleteStudent(selected.getStudentId());

                refreshData();

                showInfoAlert(
                        "Success",
                        "Student deleted successfully."
                );

            } catch (IllegalArgumentException e) {

                showWarningAlert(
                        "Invalid Operation",
                        e.getMessage()
                );

            } catch (IOException e) {

                showErrorAlert(
                        "File Error",
                        "Failed to delete student record:\n" + e.getMessage()
                );
            }
        }
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        int total = allStudents.size();

        double gpaSum = 0.0;

        Set<String> departments = new HashSet<>();

        for (Student student : allStudents) {

            gpaSum += student.getGpa();

            departments.add(
                    student.getDepartment()
                            .trim()
                            .toLowerCase()
            );
        }

        double averageGpa =
                (total > 0) ? gpaSum / total : 0.0;

        view.getTotalStudentsLabel()
                .setText(String.valueOf(total));

        view.getAverageGpaLabel()
                .setText(String.format("%.2f", averageGpa));

        view.getDepartmentsLabel()
                .setText(String.valueOf(departments.size()));

        // Update Recent Students table on Dashboard
        if (view.getRecentStudentsTable() != null) {

            ObservableList<Student> recentStudents =
                    FXCollections.observableArrayList();

            int limit = Math.min(5, allStudents.size());

            for (int i = allStudents.size() - 1; i >= allStudents.size() - limit; i--) {

                recentStudents.add(allStudents.get(i));
            }

            view.getRecentStudentsTable()
                    .setItems(recentStudents);
        }
    }

    // =========================================================
    // ALERTS & DARK STYLING
    // =========================================================

    private void showInfoAlert(String title, String message) {

        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    private void showWarningAlert(String title, String message) {

        showAlert(Alert.AlertType.WARNING, title, message);
    }

    private void showErrorAlert(String title, String message) {

        showAlert(Alert.AlertType.ERROR, title, message);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        styleAlert(alert);

        alert.showAndWait();
    }

    private void styleAlert(Alert alert) {

        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.setStyle(
                "-fx-background-color: #111827; " +
                "-fx-base: #111827; " +
                "-fx-border-color: #374151; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 8px; " +
                "-fx-background-radius: 8px;"
        );

        // Style content and header text
        dialogPane.lookupAll(".label").forEach(node ->
                node.setStyle("-fx-text-fill: #F9FAFB; -fx-font-size: 13px;")
        );

        if (dialogPane.getContent() != null) {
            dialogPane.getContent().setStyle("-fx-text-fill: #F9FAFB; -fx-font-size: 13px;");
        }

        // Style action buttons
        for (ButtonType buttonType : dialogPane.getButtonTypes()) {
            Button button = (Button) dialogPane.lookupButton(buttonType);
            if (button != null) {
                if (buttonType == ButtonType.OK || buttonType == ButtonType.YES) {
                    button.setStyle(
                            "-fx-background-color: #3B82F6; " +
                            "-fx-text-fill: #FFFFFF; " +
                            "-fx-font-size: 12px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-background-radius: 6px; " +
                            "-fx-cursor: hand; " +
                            "-fx-padding: 6 16 6 16;"
                    );
                } else {
                    button.setStyle(
                            "-fx-background-color: #1F2937; " +
                            "-fx-border-color: #374151; " +
                            "-fx-border-radius: 6px; " +
                            "-fx-background-radius: 6px; " +
                            "-fx-text-fill: #F9FAFB; " +
                            "-fx-font-size: 12px; " +
                            "-fx-cursor: hand; " +
                            "-fx-padding: 6 16 6 16;"
                    );
                }
            }
        }
    }

    // =========================================================
    // GET VIEW
    // =========================================================

    public StudentView getView() {

        return view;
    }
}
