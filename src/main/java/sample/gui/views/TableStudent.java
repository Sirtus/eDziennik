package sample.gui.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import sample.database.Grade;
import sample.database.Student;
import sample.database.Subject;
import sample.database.Teacher;
import sample.databaseCommunication.DatabaseCommunicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableStudent {

    private final String[] possibleGrades = {"1", "2", "3", "4", "5", "6"};

    private ArrayList<Button> grades;
    private HBox gradesBox;
    private Button button;

    private final Subject subject;
    private final Student student;
    private final Teacher teacher;

    private final TeacherViewController teacherViewController;
    private final ViewSwitcher viewSwitcher;
    private final DatabaseCommunicator communicator;


    public TableStudent(Student student, Subject subject, Teacher teacher, List<Grade> grades,
                        ViewSwitcher viewSwitcher, TeacherViewController teacherViewController) {

        this.teacherViewController = teacherViewController;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.viewSwitcher = viewSwitcher;
        this.gradesBox = new HBox();
        this.grades = new ArrayList<>();
        this.communicator = viewSwitcher.getCommunicator();

        startAddGradeButton();
        startGradeButtons(grades);
    }

    private void startAddGradeButton() {
        this.button = new Button();
        this.button.setText("Dodaj");
        this.button.setOnAction((event) -> {
            TextInputDialog addGradeDialog = new TextInputDialog();
            addGradeDialog.setTitle("Dodawanie ocen");
            addGradeDialog.setHeaderText("Dodajesz ocenę " + student.getFullName());

            Button okButton = (Button) addGradeDialog.getDialogPane().lookupButton(ButtonType.OK);
            TextField inputField = addGradeDialog.getEditor();
            BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
            okButton.disableProperty().bind(isInvalid);

            Optional<String> result = addGradeDialog.showAndWait();
            result.ifPresent(this::addGrade);
        });
    }

    private void startGradeButtons(List<Grade> grades) {
        for (Grade g : grades) {
            Button gradeButton = new Button();
            gradeButton.setText(Integer.toString(g.getMark()));
            gradeButton.setOnAction((event) -> {
                TextInputDialog editGradeDialog = new TextInputDialog();
                ButtonType editButton = new ButtonType("Edytuj");
                ButtonType deleteButton = new ButtonType("Usuń");
                ButtonType cancelButton = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
                editGradeDialog.getDialogPane().getButtonTypes().clear();
                editGradeDialog.getDialogPane().getButtonTypes().addAll(editButton, deleteButton, cancelButton);
                editGradeDialog.setTitle("Edytowanie ocen");
                editGradeDialog.setHeaderText("Edytujesz ocenę " + student.getFullName());
                editGradeDialog.setContentText("Ocena: " + gradeButton.getText());

                editGradeDialog.setResultConverter((dialogButton) -> {
                    if (dialogButton == editButton) return editGradeDialog.getEditor().getText();
                    else if (dialogButton == deleteButton) deleteGrade(g);
                    return null;
                });
                Optional<String> result = editGradeDialog.showAndWait();
                result.ifPresent(str -> editGrade(str, g));
            });
            this.grades.add(gradeButton);
            this.gradesBox.getChildren().add(gradeButton);
        }
    }

    private boolean isInvalid(String gradeText) {
        for (String s : possibleGrades) {
            if (gradeText.equals(s)) return false;
        }
        return true;
    }

    private void addGrade(String gradeStr) {
        Grade gradeToInsert = new Grade(Integer.parseInt(gradeStr), subject, student, teacher);

        communicator.insertGradeToDatabase(gradeToInsert);
        teacherViewController.updateTable();
    }

    private void editGrade(String gradeStr, Grade grade) {
        communicator.editGrade(grade, Integer.parseInt(gradeStr));
        teacherViewController.updateTable();
    }

    private void deleteGrade(Grade grade) {
        communicator.deleteGrade(grade);
        teacherViewController.updateTable();
    }

    /* DO NOT DELETE */
    public String getFirstname() {
        return student.getFirstname();
    }

    public Student getStudent() {
        return student;
    }

    public String getLastname() {
        return student.getLastname();
    }

    public ArrayList<Button> getGrades() {
        return grades;
    }

    public Button getButton() {
        return button;
    }

    public HBox getGradesBox() {
        return gradesBox;
    }
    /**/
}
