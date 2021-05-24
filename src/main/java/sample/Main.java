package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.database.*;
import sample.gui.MainLayoutController;
import sample.gui.views.ViewSwitcher;
import sample.gui.views.ViewTypes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Main extends Application {

    public static void main(String[] args) {
        EntityManager session = getSession();
        addSampleData();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mainLayout.fxml"));
        System.out.println("main: " + getClass().getResource("/mainLayout.fxml").toString());
        Parent root = loader.load();
        MainLayoutController mainLayoutController = loader.getController();
        ViewSwitcher viewSwitcher = new ViewSwitcher(mainLayoutController);
        viewSwitcher.setCurrentView(ViewTypes.STUDENT_GRADES); // tu bedzie logowanie


        primaryStage.setTitle("eDziennik");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(
                (WindowEvent t) -> {
                    Platform.exit();
                    System.exit(0);
                }
        );
        primaryStage.show();
    }

    private static void addSampleData() {
        EntityManager session =  getSession();

        //example data

        Person t1 = new Teacher( "Arkadiusz", "Trawa", "012312453453", new Date(), "mgr", "AT123", "siemasiema");
        SchoolClass sc1 = new SchoolClass(2, 3, "c", (Teacher) t1);
        Person s1 = new Student( "Patryk", "Woda", "01234533453", new Date(), sc1,"PW123", "siemasiema21");

        Set<Teacher> teachers = new HashSet<>();
        teachers.add((Teacher) t1);

        Set<SchoolClass> schoolClasses = new HashSet<>();
        schoolClasses.add(sc1);

        Subject sub1 = new Subject("Fizyka", teachers, schoolClasses);

        EntityTransaction etx = session.getTransaction();
        try {
            etx.begin();
            session.persist(t1);
            session.persist(sc1);
            session.persist(s1);
            session.persist(sub1);
            etx.commit();
        } finally {
            //session.close();
        }
    }

    private static final EntityManagerFactory ourSessionFactory =
            Persistence.createEntityManagerFactory("myDatabaseConfig");


    public static EntityManager getSession() {
        EntityManagerFactory emf = Persistence.
                createEntityManagerFactory("myDatabaseConfig");
        return ourSessionFactory.createEntityManager();
    }
}