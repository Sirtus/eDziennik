package sample.gui.views;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.util.Pair;
import sample.database.Grade;
import sample.database.Student;
import sample.database.Subject;

import java.util.*;

public class StudentGradesViewController extends View {

    public static final double SUBJECT_COLUMN_WIDTH = 180d;
    public static final double GRADE_COLUMN_WIDTH = 40d;
    public static final double ROW_HEIGHT = 35d;

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane grid;

    private Student student;

    public void initialize() {

    }

    private void fillGrid() {
        List<Pair<Subject, ArrayList<Grade>>> contents = new ArrayList<>();
        contents.add(new Pair<>(new Subject("dupa"),
                new ArrayList<>(List.of(
                        new Grade(1), new Grade(2), new Grade(3), new Grade(4), new Grade(5), new Grade(6),
                        new Grade(1), new Grade(2), new Grade(3), new Grade(4), new Grade(5), new Grade(6),
                    new Grade(1), new Grade(2), new Grade(3), new Grade(4), new Grade(5), new Grade(6),
                    new Grade(1), new Grade(2), new Grade(3), new Grade(4), new Grade(5), new Grade(6),
                    new Grade(1), new Grade(2), new Grade(3), new Grade(4), new Grade(5), new Grade(6),
                    new Grade(1), new Grade(2), new Grade(3), new Grade(4), new Grade(5), new Grade(6)))));
//        List<Pair<Subject, ArrayList<Grade>>> contents = viewSwitcher.getCommunicator().getStudentGrades(student.getID());
        int gradeColumns = contents.stream().map(Pair::getValue).mapToInt(List::size).max().orElseThrow();
        resetGrid(gradeColumns);
        int i = 0;
        for (Pair<Subject, ArrayList<Grade>> subjectGradesPair : contents) {
            addRow(subjectGradesPair.getKey(), subjectGradesPair.getValue(), i);
            i++;
        }
    }

    private void resetGrid(int gradeColumns) {
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();
        grid.getChildren().clear();
        grid.getColumnConstraints().add(0, getColumnConstraintForSize(SUBJECT_COLUMN_WIDTH));

        for (int i = 1; i<=gradeColumns; i++) {
            grid.getColumnConstraints().add(getColumnConstraintForSize(GRADE_COLUMN_WIDTH));
        }
    }

    private void addRow(Subject subject, List<Grade> grades, int rowNumber) {
        grid.getRowConstraints().add(getGradeRowConstraintForSize(ROW_HEIGHT));
        StackPane subjectLabelContainer = new StackPane();
        subjectLabelContainer.getStyleClass().add("subject");
        subjectLabelContainer.setAlignment(Pos.CENTER);
        subjectLabelContainer.getChildren().add(new Label(subject.getName()));
        grid.add(subjectLabelContainer, 0, rowNumber);

        int i = 1;
        for (Grade grade : grades) {
            grid.add(makeGradeContainer(grade), i, rowNumber);
            i++;
        }
    }

    private StackPane makeGradeContainer(Grade grade) {
        int mark = grade.getMark();
        StackPane gradeLabelContainer = new StackPane();
        gradeLabelContainer.setAlignment(Pos.CENTER);
        gradeLabelContainer.getChildren().add(new Label(String.valueOf(mark)));
        gradeLabelContainer.getStyleClass().add("grade" + mark);

        return gradeLabelContainer;
    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        student = viewSwitcher.getCurrentStudentContext();
        fillGrid();
    }

    @Override
    public void popContext() {
        viewSwitcher.popStudent();
    }
}
