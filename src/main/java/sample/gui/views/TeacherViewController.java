package sample.gui.views;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sample.database.*;
import sample.databaseCommunication.DatabaseCommunicator;

import java.util.*;

public class TeacherViewController extends View{

    @FXML
    private VBox root;

    @FXML
    Label subjectName;

    @FXML
    MenuButton classButton;

    @FXML
    TableView<TableStudent> studentsTable;

    @FXML
    TableColumn<TableStudent, String> firstname;
    @FXML
    TableColumn<TableStudent, String> lastname;
    @FXML
    TableColumn<TableStudent, HBox> grades;
    @FXML
    TableColumn<TableStudent, Button> add;

    private Set<SchoolClass> classSet;
    private Teacher teacher;
    private Subject subject;
    private SchoolClass currentClass;

    public void initialize(){
        firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        grades.setCellValueFactory(new PropertyValueFactory<>("gradesBox"));
        add.setCellValueFactory(new PropertyValueFactory<>("button"));
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        teacher = viewSwitcher.getCurrentTeacherContext();
        subject = viewSwitcher.getCurrentSubjectContext();
        classSet = viewSwitcher.getClassesListEnrolledForSubject(subject);

        resetTable();
    }


    @Override
    public void popContext() {
        viewSwitcher.popTeacher();
        viewSwitcher.popSubject();
        currentClass = null;
        classButton.setText("Klasa");
    }

    public void updateTable() {
        studentsTable.getItems().clear();

        List<Pair<Student, List<Pair<Subject, ArrayList<Grade>>>>> students = viewSwitcher.getStudentsGradesBySchoolClass(currentClass);
        ArrayList<TableStudent> ts = new ArrayList<>();

        for(Pair<Student, List<Pair<Subject, ArrayList<Grade>>>> studentListPair: students) {
            Student student = studentListPair.getKey();
            for(Pair<Subject, ArrayList<Grade>> subjectGradeListPair: studentListPair.getValue()) {
                if(subjectGradeListPair.getKey().getID() == subject.getID()) {
                    ts.add(new TableStudent(student, subject, teacher, subjectGradeListPair.getValue(), viewSwitcher, this));
                }
            }
        }
        for(TableStudent t: ts) {
            studentsTable.getItems().add(t);
        }
    }

    private void resetTable() {
        currentClass = null;
        classButton.getItems().clear();
        studentsTable.getItems().clear();
        subjectName.setText(subject.getName());
        for(SchoolClass sc: classSet) {
            MenuItem item = new MenuItem(sc.getYear() + "" + sc.getDepartment());
            classButton.getItems().add(item);
            item.setOnAction((event) -> {
                classButton.setText(item.getText());
                viewSwitcher.addToContext(sc);
                currentClass = sc;
                updateTable();
            });
        }
    }
}

