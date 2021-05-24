package sample.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Main;
import sample.databaseCommunication.DatabaseCommunicator;
import sample.databaseCommunication.Login;
import sample.gui.views.ViewSwitcher;
import sample.gui.views.ViewTypes;

import java.io.IOException;

public class GUIInitializer {

    public static final double WINDOW_HEIGHT = 650;
    public static final double WINDOW_WIDTH = 900;

    private Stage primaryStage;
    private DatabaseCommunicator communicator;

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

    public void startLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setInitializer(this);
        controller.setCommunicator(communicator);
        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
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
        ViewSwitcher viewSwitcher = new ViewSwitcher(mainLayoutController, communicator);

        switch (communicator.getUserType()) {
            case STUDENT:
                viewSwitcher.addToContext(communicator.getStudentByID(userID));
                viewSwitcher.setCurrentView(ViewTypes.STUDENT_GRADES);
                break;
            case TEACHER:
                break;
        }
        primaryStage.show();
    }
}
