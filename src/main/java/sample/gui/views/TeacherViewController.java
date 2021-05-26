package sample.gui.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sample.database.*;
import sample.databaseCommunication.DatabaseCommunicator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeacherViewController extends View{

    @FXML
    private VBox root;

    @FXML
    Label subjectName;

    private Set<SchoolClass> classSet;

    @FXML
    MenuButton classButton;

    @FXML
    TableView studentsTable;

    @FXML
    TableColumn firstname;
    @FXML
    TableColumn lastname;
    @FXML
    TableColumn grades;
    @FXML
    TableColumn add;

    public void initialize(){
        firstname.setCellValueFactory(new PropertyValueFactory<TableStudent, String>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<TableStudent, String>("lastname"));
        grades.setCellValueFactory(new PropertyValueFactory<TableStudent, ArrayList<Integer>>("grades"));
        add.setCellValueFactory(new PropertyValueFactory<TableStudent, Button>("button"));
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        classButton.setText("Klasa");
        studentsTable.getItems().clear();
        DatabaseCommunicator communicator = viewSwitcher.getCommunicator();
        classSet = communicator.getClassesListByTeacherAndSubject(viewSwitcher.getCurrentTeacherContext().getID(),
                viewSwitcher.getCurrentSubjectContext().getID());
        subjectName.setText(viewSwitcher.getCurrentSubjectContext().getName());
        for(SchoolClass sc: classSet){
            MenuItem item = new MenuItem(sc.getYear() + "" + sc.getDepartment());
            classButton.getItems().add(item);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    studentsTable.getItems().clear();
                    classButton.setText(item.getText());
                    viewSwitcher.addToContext(sc);
                    updateTable();
                }
            });
        }
    }

    @Override
    public void popContext() {
        studentsTable.getItems().clear();
    }

    private void updateTable() {
        System.out.println(viewSwitcher.getCurrentSchoolClassContext().getYear());
        DatabaseCommunicator com = viewSwitcher.getCommunicator();
        List<Pair<Student, List<Pair<Subject, ArrayList<Grade>>>>> students = com.getStudentsGradesBySchoolClass(viewSwitcher.getCurrentSchoolClassContext().getID());
        ArrayList<TableStudent> ts = new ArrayList<>();

        for(Pair<Student, List<Pair<Subject, ArrayList<Grade>>>> pair: students){
            Student student = pair.getKey();
            for(Pair<Subject, ArrayList<Grade>> pair2: pair.getValue()){
                if(pair2.getKey().getID() == viewSwitcher.getCurrentSubjectContext().getID()){
                    ts.add(new TableStudent(student, pair2.getValue()));
                }
            }
        }

        for(TableStudent t: ts) {
            System.out.println(t.getFirstname());
            studentsTable.getItems().add(t);
        }
    }


    public class TableStudent{
        private String firstname;
        private String lastname;
        private ArrayList<Integer> grades;
        private Student student;
        private Button button;

        public TableStudent(Student student,  ArrayList<Grade> grades){
            this.student = student;
            this.firstname = student.getFirstname();
            this.lastname = student.getLastname();
            this.button = new Button();
            this.button.setText("Dodaj");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(firstname + " " + lastname);
                }
            });
            this.grades = new ArrayList<>();
            for(Grade g: grades) {
                this.grades.add(g.getMark());
            }
        }

        public String getFirstname() {
            return firstname;
        }

        public Student getStudent() {
            return student;
        }

        public String getLastname() {
            return lastname;
        }

        public ArrayList<Integer> getGrades() {
            return grades;
        }

        public Button getButton() {
            return button;
        }
    }
}

