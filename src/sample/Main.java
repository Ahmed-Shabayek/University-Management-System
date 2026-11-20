package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.StudentController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        StudentController controller = new StudentController();

        Scene scene = new Scene(
                controller.getView().getRoot(),
                1200,
                750
        );

        primaryStage.setTitle("University Management System");
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
