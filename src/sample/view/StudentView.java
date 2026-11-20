package sample.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sample.model.Student;

public class StudentView {

    // =========================================================
    // COLOR PALETTE CONSTANTS
    // =========================================================
    private static final String BG_MAIN = "#111827";
    private static final String BG_SIDEBAR = "#0B1220";
    private static final String BG_CARD = "#1F2937";
    private static final String ACCENT_BLUE = "#3B82F6";
    private static final String TEXT_PRIMARY = "#F9FAFB";
    private static final String TEXT_SECONDARY = "#9CA3AF";
    private static final String BORDER_COLOR = "#374151";

    private static final String ACTIVE_NAV_STYLE =
            "-fx-background-color: #1F2937; " +
            "-fx-text-fill: #F9FAFB; " +
            "-fx-font-size: 13px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 6px; " +
            "-fx-border-color: #3B82F6; " +
            "-fx-border-width: 0 0 0 3px; " +
            "-fx-padding: 10 16 10 16; " +
            "-fx-alignment: CENTER_LEFT; " +
            "-fx-cursor: hand;";

    private static final String INACTIVE_NAV_STYLE =
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #9CA3AF; " +
            "-fx-font-size: 13px; " +
            "-fx-font-weight: normal; " +
            "-fx-background-radius: 6px; " +
            "-fx-border-width: 0; " +
            "-fx-padding: 10 16 10 16; " +
            "-fx-alignment: CENTER_LEFT; " +
            "-fx-cursor: hand;";

    // =========================================================
    // ROOT AND PAGES
    // =========================================================
    private final BorderPane root;
    private VBox dashboardPage;
    private VBox studentsPage;

    // =========================================================
    // NAVIGATION CONTROLS
    // =========================================================
    private Button dashboardButton;
    private Button studentsButton;

    // =========================================================
    // DASHBOARD STATISTICS
    // =========================================================
    private Label totalStudentsLabel;
    private Label averageGpaLabel;
    private Label departmentsLabel;
    private TableView<Student> recentStudentsTable;

    // =========================================================
    // DIRECTORY CONTROLS
    // =========================================================
    private TextField searchField;
    private Button addButton;
    private Button editButton;
    private Button deleteButton;
    private TableView<Student> studentTable;

    public StudentView() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG_MAIN + ";");

        createLayout();
    }

    // =========================================================
    // LAYOUT INITIALIZATION
    // =========================================================
    private void createLayout() {
        root.setTop(createHeader());
        root.setLeft(createSidebar());

        dashboardPage = createDashboardPage();
        studentsPage = createStudentsPage();

        showDashboardPage();
    }

    // =========================================================
    // HEADER
    // =========================================================
    private HBox createHeader() {
        Label logo = new Label("FCI UNIVERSITY");
        logo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label subtitle = new Label("University Student Management System");
        subtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        VBox branding = new VBox(2);
        branding.getChildren().addAll(logo, subtitle);

        Label userBadge = new Label("Administrator");
        userBadge.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + ACCENT_BLUE + "; " +
                "-fx-background-color: #1E3A8A; -fx-background-radius: 12px; -fx-padding: 4 12 4 12;");

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(14, 24, 14, 24));
        header.setSpacing(20);
        header.setStyle("-fx-background-color: " + BG_SIDEBAR + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 0 1 0;");

        header.getChildren().addAll(branding, userBadge);
        HBox.setHgrow(branding, Priority.ALWAYS);

        return header;
    }

    // =========================================================
    // SIDEBAR
    // =========================================================
    private VBox createSidebar() {
        VBox sidebar = new VBox(8);
        sidebar.setPadding(new Insets(20, 16, 20, 16));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: " + BG_SIDEBAR + "; -fx-border-color: " + BORDER_COLOR + "; -fx-border-width: 0 1 0 0;");

        Label navTitle = new Label("MENU");
        navTitle.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-padding: 0 0 4 4;");

        dashboardButton = new Button("Dashboard");
        dashboardButton.setMaxWidth(Double.MAX_VALUE);
        dashboardButton.setStyle(ACTIVE_NAV_STYLE);

        studentsButton = new Button("Students");
        studentsButton.setMaxWidth(Double.MAX_VALUE);
        studentsButton.setStyle(INACTIVE_NAV_STYLE);

        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(
                navTitle,
                dashboardButton,
                studentsButton,
                spacer
        );

        return sidebar;
    }

    // =========================================================
    // DASHBOARD PAGE
    // =========================================================
    private VBox createDashboardPage() {
        VBox content = new VBox(24);
        content.setPadding(new Insets(28));
        content.setStyle("-fx-background-color: " + BG_MAIN + ";");

        // Page Header
        Label title = new Label("Dashboard Overview");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label description = new Label("Key academic metrics and system statistics");
        description.setStyle("-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        VBox titleSection = new VBox(4);
        titleSection.getChildren().addAll(title, description);

        // Statistic Cards Row
        HBox statisticsRow = createStatisticsCards();

        // Recent Students Section
        VBox recentSection = createRecentStudentsSection();
        VBox.setVgrow(recentSection, Priority.ALWAYS);

        content.getChildren().addAll(
                titleSection,
                statisticsRow,
                recentSection
        );

        return content;
    }

    private HBox createStatisticsCards() {
        HBox cards = new HBox(16);

        totalStudentsLabel = new Label("0");
        VBox totalCard = createStatCard("Total Students", totalStudentsLabel, TEXT_PRIMARY);

        averageGpaLabel = new Label("0.00");
        VBox gpaCard = createStatCard("Average GPA", averageGpaLabel, ACCENT_BLUE);

        departmentsLabel = new Label("0");
        VBox deptCard = createStatCard("Departments", departmentsLabel, TEXT_PRIMARY);

        HBox.setHgrow(totalCard, Priority.ALWAYS);
        HBox.setHgrow(gpaCard, Priority.ALWAYS);
        HBox.setHgrow(deptCard, Priority.ALWAYS);

        cards.getChildren().addAll(totalCard, gpaCard, deptCard);
        return cards;
    }

    private VBox createStatCard(String title, Label valueLabel, String valueColor) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-weight: bold;");

        valueLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + valueColor + ";");

        VBox card = new VBox(8);
        card.setPadding(new Insets(18, 22, 18, 22));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: " + BG_CARD + "; " +
                "-fx-background-radius: 8px; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 8px; " +
                "-fx-border-width: 1px;");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private VBox createRecentStudentsSection() {
        VBox section = new VBox(12);

        Label sectionTitle = new Label("Recent Students");
        sectionTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label sectionSubtitle = new Label("Recently enrolled student records");
        sectionSubtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        VBox headerBox = new VBox(2);
        headerBox.getChildren().addAll(sectionTitle, sectionSubtitle);

        recentStudentsTable = createStyledTable();

        TableColumn<Student, String> idCol = new TableColumn<>("Student ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        idCol.setPrefWidth(120);

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Student, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        deptCol.setPrefWidth(160);

        TableColumn<Student, String> yearCol = new TableColumn<>("Academic Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        yearCol.setPrefWidth(130);

        TableColumn<Student, Double> gpaCol = new TableColumn<>("GPA");
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        gpaCol.setPrefWidth(100);

        recentStudentsTable.getColumns().addAll(idCol, nameCol, deptCol, yearCol, gpaCol);
        VBox.setVgrow(recentStudentsTable, Priority.ALWAYS);

        section.getChildren().addAll(headerBox, recentStudentsTable);
        return section;
    }

    // =========================================================
    // STUDENTS DIRECTORY PAGE
    // =========================================================
    private VBox createStudentsPage() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(28));
        content.setStyle("-fx-background-color: " + BG_MAIN + ";");

        // Page Header
        Label title = new Label("Student Directory");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        Label description = new Label("Search, view, and manage student records");
        description.setStyle("-fx-font-size: 13px; -fx-text-fill: " + TEXT_SECONDARY + ";");

        VBox titleSection = new VBox(4);
        titleSection.getChildren().addAll(title, description);

        // Action Bar (Search + Buttons)
        HBox actionBar = createActionBar();

        // Main Directory Table
        studentTable = createStyledTable();

        TableColumn<Student, String> idCol = new TableColumn<>("Student ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        idCol.setPrefWidth(120);

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(220);

        TableColumn<Student, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        deptCol.setPrefWidth(150);

        TableColumn<Student, String> yearCol = new TableColumn<>("Academic Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("academicYear"));
        yearCol.setPrefWidth(130);

        TableColumn<Student, Double> gpaCol = new TableColumn<>("GPA");
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        gpaCol.setPrefWidth(100);

        studentTable.getColumns().addAll(idCol, nameCol, emailCol, deptCol, yearCol, gpaCol);
        VBox.setVgrow(studentTable, Priority.ALWAYS);

        content.getChildren().addAll(
                titleSection,
                actionBar,
                studentTable
        );

        return content;
    }

    private HBox createActionBar() {
        searchField = new TextField();
        searchField.setPromptText("Search by ID, name, or department...");
        searchField.setPrefHeight(38);
        searchField.setStyle("-fx-background-color: " + BG_CARD + "; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-prompt-text-fill: " + TEXT_SECONDARY + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 6px; " +
                "-fx-background-radius: 6px; " +
                "-fx-font-size: 13px; " +
                "-fx-padding: 8 14 8 14;");

        addButton = new Button("Add Student");
        addButton.setPrefHeight(38);
        addButton.setStyle("-fx-background-color: " + ACCENT_BLUE + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 8 18 8 18;");

        editButton = new Button("Edit Student");
        editButton.setPrefHeight(38);
        editButton.setStyle("-fx-background-color: " + BG_CARD + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 6px; " +
                "-fx-text-fill: " + TEXT_PRIMARY + "; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 8 18 8 18;");

        deleteButton = new Button("Delete Student");
        deleteButton.setPrefHeight(38);
        deleteButton.setStyle("-fx-background-color: " + BG_CARD + "; " +
                "-fx-border-color: #EF4444; " +
                "-fx-border-radius: 6px; " +
                "-fx-text-fill: #F87171; " +
                "-fx-font-size: 13px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 6px; " +
                "-fx-cursor: hand; " +
                "-fx-padding: 8 18 8 18;");

        HBox actionBar = new HBox(12);
        actionBar.setAlignment(Pos.CENTER_LEFT);
        actionBar.getChildren().addAll(searchField, addButton, editButton, deleteButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        return actionBar;
    }

    private TableView<Student> createStyledTable() {
        TableView<Student> table = new TableView<>();
        table.setStyle(
                "-fx-background-color: " + BG_CARD + "; " +
                "-fx-base: " + BG_CARD + "; " +
                "-fx-control-inner-background: " + BG_CARD + "; " +
                "-fx-control-inner-background-alt: #182230; " +
                "-fx-table-cell-border-color: " + BORDER_COLOR + "; " +
                "-fx-table-header-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-color: " + BORDER_COLOR + "; " +
                "-fx-border-radius: 6px; " +
                "-fx-background-radius: 6px; " +
                "-fx-text-fill: " + TEXT_PRIMARY + ";"
        );

        Label placeholder = new Label("No student records available");
        placeholder.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 13px;");
        table.setPlaceholder(placeholder);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        return table;
    }

    // =========================================================
    // PAGE SWITCHING
    // =========================================================
    public void showDashboardPage() {
        root.setCenter(dashboardPage);
        dashboardButton.setStyle(ACTIVE_NAV_STYLE);
        studentsButton.setStyle(INACTIVE_NAV_STYLE);
    }

    public void showStudentsPage() {
        root.setCenter(studentsPage);
        dashboardButton.setStyle(INACTIVE_NAV_STYLE);
        studentsButton.setStyle(ACTIVE_NAV_STYLE);
    }

    // =========================================================
    // PUBLIC GETTERS
    // =========================================================
    public BorderPane getRoot() {
        return root;
    }

    public TableView<Student> getStudentTable() {
        return studentTable;
    }

    public TableView<Student> getRecentStudentsTable() {
        return recentStudentsTable;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getDashboardButton() {
        return dashboardButton;
    }

    public Button getStudentsButton() {
        return studentsButton;
    }

    public Label getTotalStudentsLabel() {
        return totalStudentsLabel;
    }

    public Label getAverageGpaLabel() {
        return averageGpaLabel;
    }

    public Label getDepartmentsLabel() {
        return departmentsLabel;
    }
}
