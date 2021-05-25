package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import sample.database.*;
import sample.databaseCommunication.DatabaseCommunicator;
import sample.databaseCommunication.Login;
import sample.gui.GUIInitializer;
import sample.gui.MainLayoutController;
import sample.gui.views.ViewSwitcher;
import sample.gui.views.ViewTypes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


public class Main extends Application {

    private static DatabaseCommunicator communicator;


    public static void main(String[] args) {
        EntityManager session = getSession();
        communicator = new DatabaseCommunicator(session);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //addSampleData();
        GUIInitializer initializer = new GUIInitializer(primaryStage, communicator);
        initializer.startLogin();
    }



    private static final EntityManagerFactory ourSessionFactory =
            Persistence.createEntityManagerFactory("myDatabaseConfig");


    public static EntityManager getSession() {
        EntityManagerFactory emf = Persistence.
                createEntityManagerFactory("myDatabaseConfig");
        return ourSessionFactory.createEntityManager();
    }
}
