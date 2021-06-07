package sample.gui.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        grades.setCellValueFactory(new PropertyValueFactory<TableStudent, HBox>("gradesBox"));
        add.setCellValueFactory(new PropertyValueFactory<TableStudent, Button>("button"));
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        classButton.getItems().clear();
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

        classButton.setText("Klasa");
        studentsTable.getItems().clear();
    }

    private void updateTable() {

        DatabaseCommunicator com = viewSwitcher.getCommunicator();
        List<Pair<Student, List<Pair<Subject, ArrayList<Grade>>>>> students = com.getStudentsGradesBySchoolClass(viewSwitcher.getCurrentSchoolClassContext().getID());
        ArrayList<TableStudent> ts = new ArrayList<>();

        for(Pair<Student, List<Pair<Subject, ArrayList<Grade>>>> pair: students){
            Student student = pair.getKey();
            for(Pair<Subject, ArrayList<Grade>> pair2: pair.getValue()){
                if(pair2.getKey().getID() == viewSwitcher.getCurrentSubjectContext().getID()){
                    ts.add(new TableStudent(student, pair2.getValue(), viewSwitcher));
                }
            }
        }

        for(TableStudent t: ts) {
            studentsTable.getItems().add(t);
        }
    }




}

