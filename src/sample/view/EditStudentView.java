package sample.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.Student;

public class EditStudentView {

    // =========================================================
    // COLOR PALETTE CONSTANTS
    // =========================================================
    private static final String BG_DIALOG = "#111827";
    private static final String BG_INPUT = "#1F2937";
    private static final String ACCENT_BLUE = "#3B82F6";
    private static final String TEXT_PRIMARY = "#F9FAFB";
    private static final String TEXT_SECONDARY = "#9CA3AF";
    private static final String BORDER_COLOR = "#374151";

    private static final String FIELD_STYLE =
            "-fx-background-color: " + BG_INPUT + "; " +
            "-fx-text-fill: " + TEXT_PRIMARY + "; " +
            "-fx-prompt-text-fill: #6B7280; " +
            "-fx-border-color: " + BORDER_COLOR + "; " +
            "-fx-border-radius: 6px; " +
            "-fx-background-radius: 6px; " +
            "-fx-font-size: 13px; " +
            "-fx-padding: 8 12 8 12;";

    private static final String LOCKED_FIELD_STYLE =
            "-fx-background-color: #111827; " +
            "-fx-text-fill: #6B7280; " +
            "-fx-border-color: " + BORDER_COLOR + "; " +
            "-fx-border-radius: 6px; " +
            "-fx-background-radius: 6px; " +
            "-fx-font-size: 13px; " +
            "-fx-padding: 8 12 8 12; " +
            "-fx-opacity: 0.85;";

    private static final String LABEL_STYLE =
            "-fx-font-size: 13px; " +
            "-fx-font-weight: 500; " +
            "-fx-text-fill: " + TEXT_SECONDARY + ";";

    private final Stage stage;

    private final TextField studentIdField;
    private final TextField nameField;
    private final TextField emailField;
    private final TextField departmentField;
    private final TextField academicYearField;
    private final TextField gpaField;

    private final Button saveButton;
    private final Button cancelButton;

    public EditStudentView(Student student) {

        stage = new Stage();
        stage.setTitle("Edit Student");
        stage.initModality(Modality.APPLICATION_MODAL);

        // =========================
        // Title
        // =========================
        Label title = new Label("Edit Student Details");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subtitle = new Label("Update student record information (ID is locked)");
        subtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        VBox headerBox = new VBox(3);
        headerBox.getChildren().addAll(title, subtitle);

        // =========================
        // Labels
        // =========================
        Label studentIdLabel = new Label("Student ID");
        studentIdLabel.setStyle(LABEL_STYLE);

        Label nameLabel = new Label("Name");
        nameLabel.setStyle(LABEL_STYLE);

        Label emailLabel = new Label("Email");
        emailLabel.setStyle(LABEL_STYLE);

        Label departmentLabel = new Label("Department");
        departmentLabel.setStyle(LABEL_STYLE);

        Label academicYearLabel = new Label("Academic Year");
        academicYearLabel.setStyle(LABEL_STYLE);

        Label gpaLabel = new Label("GPA");
        gpaLabel.setStyle(LABEL_STYLE);

        // =========================
        // Text Fields
        // =========================
        studentIdField = new TextField();
        studentIdField.setText(student != null ? student.getStudentId() : "");
        studentIdField.setEditable(false);
        studentIdField.setStyle(LOCKED_FIELD_STYLE);

        nameField = new TextField();
        nameField.setText(student != null ? student.getName() : "");
        nameField.setStyle(FIELD_STYLE);

        emailField = new TextField();
        emailField.setText(student != null ? student.getEmail() : "");
        emailField.setStyle(FIELD_STYLE);

        departmentField = new TextField();
        departmentField.setText(student != null ? student.getDepartment() : "");
        departmentField.setStyle(FIELD_STYLE);

        academicYearField = new TextField();
        academicYearField.setText(student != null ? student.getAcademicYear() : "");
        academicYearField.setStyle(FIELD_STYLE);

        gpaField = new TextField();
        gpaField.setText(student != null ? String.valueOf(student.getGpa()) : "");
        gpaField.setStyle(FIELD_STYLE);

        // =========================
        // Form
        // =========================
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(12);

        form.add(studentIdLabel, 0, 0);
        form.add(studentIdField, 1, 0);

        form.add(nameLabel, 0, 1);
        form.add(nameField, 1, 1);

        form.add(emailLabel, 0, 2);
        form.add(emailField, 1, 2);

        form.add(departmentLabel, 0, 3);
        form.add(departmentField, 1, 3);

        form.add(academicYearLabel, 0, 4);
        form.add(academicYearField, 1, 4);

        form.add(gpaLabel, 0, 5);
        form.add(gpaField, 1, 5);

        // =========================
        // Buttons
        // =========================
        saveButton = new Button("Save Changes");
        saveButton.setStyle(
                "-fx-background-color: " + ACCENT_BLUE + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 8 18 8 18;"
        );

        cancelButton = new Button("Cancel");
        cancelButton.setStyle(
                "-fx-background-color: " + BG_INPUT + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 6px; " +
                "-fx-background-radius: 6px; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-font-size: 13px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 8 18 8 18;"
        );

        HBox buttons = new HBox(12);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.getChildren().addAll(saveButton, cancelButton);

        // =========================
        // Root Layout
        // =========================
        VBox root = new VBox(20);
        root.setPadding(new Insets(24, 28, 24, 28));
        root.setStyle("-fx-background-color: " + BG_DIALOG + ";");
        root.getChildren().addAll(
                headerBox,
                form,
                buttons
        );

        Scene scene = new Scene(root, 480, 400);
        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }

    public TextField getStudentIdField() {
        return studentIdField;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getDepartmentField() {
        return departmentField;
    }

    public TextField getAcademicYearField() {
        return academicYearField;
    }

    public TextField getGpaField() {
        return gpaField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
