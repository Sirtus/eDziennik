package sample.gui.views;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import sample.database.Student;

public class StudentGradesViewController extends View {

    @FXML
    private AnchorPane root;

    private Student student;

    public void initialize() {

    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        student = viewSwitcher.getCurrentStudentContext();
    }

    @Override
    public void popContext() {
        viewSwitcher.popStudent();
    }
}
