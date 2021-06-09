package sample.gui.views;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import sample.database.*;

import java.util.*;
import java.util.stream.Collectors;

public class ClassTutorViewController extends View {

    public static final double ROW_HEIGHT = 50d;


    @FXML
    private AnchorPane root;

    @FXML
    private GridPane grid;

    private Teacher teacher;
    private SchoolClass schoolClass;

    private void fillGrid() {
        resetGrid();
        List<Student> contents = communicator.getStudentListBySchoolClass(schoolClass);
        int i = 0;
        for (Student student : contents) {
            addRow(student, i);
            i++;
        }
    }

    private void resetGrid() {
        grid.getRowConstraints().clear();
        grid.getChildren().clear();
    }

    private void addRow(Student student, int rowNumber) {
        grid.getRowConstraints().add(getGradeRowConstraintForSize(ROW_HEIGHT));
        StackPane rowContainer = new StackPane();
        rowContainer.getStyleClass().add("student");
        rowContainer.setAlignment(Pos.CENTER);
        rowContainer.getChildren().add(new Label(student.getFullName()));
        rowContainer.setCursor(Cursor.HAND);
        rowContainer.setOnMouseClicked((event) -> {
            viewSwitcher.addToContext(student);
            viewSwitcher.setCurrentView(ViewTypes.STUDENT_GRADES);
        });
        grid.add(rowContainer, 0, rowNumber);
    }


    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        teacher = viewSwitcher.getCurrentTeacherContext();
        schoolClass = viewSwitcher.getCurrentSchoolClassContext();
        fillGrid();
    }

    @Override
    public void popContext() {
        viewSwitcher.popTeacher();
        viewSwitcher.popSchoolClass();
    }
}
