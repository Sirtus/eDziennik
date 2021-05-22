package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
    private static final EntityManagerFactory ourSessionFactory =
            Persistence.createEntityManagerFactory("myDatabaseConfig");


    public static EntityManager getSession() {
        EntityManagerFactory emf = Persistence.
                createEntityManagerFactory("myDatabaseConfig");
        return ourSessionFactory.createEntityManager();
    }


    public static void main(String[] args) {
        EntityManager session =  getSession();

        //example data

        Person t1 = new Teacher( "Arkadiusz", "Trawa", "012312453453", new Date(), "mgr");
        SchoolClass sc1 = new SchoolClass(2, 3, "c", (Teacher) t1);
        Person s1 = new Student( "Patryk", "Woda", "01234533453", new Date(), sc1);

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
            session.close();
        }


        launch(args);
    }
}
