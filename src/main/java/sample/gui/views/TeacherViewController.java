package sample.gui.views;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sample.database.*;

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

    private List<SchoolClass> classList;
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
        classList = communicator.getClassesListEnrolledForSubject(subject);

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
        List<Pair<Student, List<Grade>>> classGrades = communicator.getStudentGradesByClassForSubject(currentClass, subject);

        for(Pair<Student, List<Grade>> studentGrades : classGrades) {
            Student student = studentGrades.getKey();
            List<Grade> grades = studentGrades.getValue();
            studentsTable.getItems().add(
                    new TableStudent(student, subject, teacher, grades, viewSwitcher, this)
            );
        }
    }

    private void resetTable() {
        currentClass = null;
        classButton.getItems().clear();
        studentsTable.getItems().clear();
        subjectName.setText(subject.getName());
        for(SchoolClass sc: classList) {
            MenuItem item = new MenuItem(sc.getName());
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

