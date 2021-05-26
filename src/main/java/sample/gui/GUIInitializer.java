package sample.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Main;
import sample.database.Student;
import sample.database.Teacher;
import sample.databaseCommunication.DatabaseCommunicator;
import sample.databaseCommunication.Login;
import sample.gui.views.ViewSwitcher;
import sample.gui.views.ViewTypes;

import java.io.IOException;

public class GUIInitializer {

    public static final double WINDOW_HEIGHT = 650;
    public static final double WINDOW_WIDTH = 900;

    private final Stage primaryStage;
    private final DatabaseCommunicator communicator;

    public GUIInitializer(Stage primaryStage, DatabaseCommunicator communicator) {
        this.communicator = communicator;
        this.primaryStage = primaryStage;
        primaryStage.setTitle("eDziennik");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(
                (WindowEvent t) -> {
                    Platform.exit();
                    System.exit(0);
                }
        );
    }

    public void startLogin() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setInitializer(this);
            controller.setCommunicator(communicator);
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }

    public void startMainLayout(int userID) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/mainLayout.fxml"));
        try {
            Parent root = loader.load();
            root.getStylesheets().add(Main.class.getResource("/styles.css").toExternalForm());
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        MainLayoutController mainLayoutController = loader.getController();
        mainLayoutController.setCommunicator(communicator);
        mainLayoutController.setInitializer(this);
        ViewSwitcher viewSwitcher = new ViewSwitcher(mainLayoutController, communicator);

        switch (communicator.getUserType()) {
            case STUDENT -> {
                viewSwitcher.addToContext((Student) communicator.getUser());
                viewSwitcher.setCurrentView(ViewTypes.STUDENT_GRADES);
            }
            case TEACHER -> {
                // to jest tymczasowe, potem będzie widok dla nauczyciela w którym on sobie wybiera między uczonymi przedmiotami a wychowywaną klasą.
                Teacher temp = (Teacher) communicator.getUser();
                viewSwitcher.addToContext(temp);
                viewSwitcher.addToContext(temp.getMyClass());
                viewSwitcher.setCurrentView(ViewTypes.TEACHER_SWITCH);
            }
        }
        primaryStage.show();
    }
}
