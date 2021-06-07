package sample.gui.views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import sample.database.Grade;
import sample.database.Student;

import java.util.ArrayList;
import java.util.Optional;

public class TableStudent{
    private String firstname;
    private String lastname;
    private ArrayList<Button> grades;
    private HBox gradesBox;
    private Student student;
    private Button button;
    private final String[] possibleGrades = {"1", "2", "3", "4", "5", "6"};
    private final ViewSwitcher viewSwitcher;

    public TableStudent(Student student,  ArrayList<Grade> grades, ViewSwitcher viewSwitcher){
        this.viewSwitcher = viewSwitcher;
        this.student = student;
        this.gradesBox = new HBox();
        this.firstname = student.getFirstname();
        this.lastname = student.getLastname();
        this.button = new Button();
        this.button.setText("Dodaj");
        this.button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                TextInputDialog addGradeDialog = new TextInputDialog();
                addGradeDialog.setTitle("Dodawanie ocen");
                addGradeDialog.setHeaderText("Dodajesz ocenę " + firstname + " " + lastname);

                Button okButton = (Button) addGradeDialog.getDialogPane().lookupButton(ButtonType.OK);
                TextField inputField = addGradeDialog.getEditor();
                BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
                okButton.disableProperty().bind(isInvalid);

                Optional<String> result = addGradeDialog.showAndWait();
                result.ifPresent(str -> addGrade(str));
            }

        });

        this.grades = new ArrayList<>();

        for(Grade g: grades) {
            Button gradeButton = new Button();
            gradeButton.setText(Integer.toString(g.getMark()));
            gradeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    TextInputDialog editGradeDialog = new TextInputDialog();
                    ButtonType editButton = new ButtonType("Edytuj");
                    ButtonType deleteButton = new ButtonType("Usuń");
                    ButtonType cancelButton = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
                    editGradeDialog.getDialogPane().getButtonTypes().clear();
                    editGradeDialog.getDialogPane().getButtonTypes().addAll(editButton, deleteButton, cancelButton);
                    editGradeDialog.setTitle("Edytowanie ocen");
                    editGradeDialog.setHeaderText("Edytujesz ocenę " + firstname + " " + lastname);
                    editGradeDialog.setContentText("Ocena: " + gradeButton.getText());


                    editGradeDialog.setResultConverter(dialogButton ->{
                        if(dialogButton == editButton){
                            return editGradeDialog.getEditor().getText();
                        }
                        else if(dialogButton == deleteButton){
                            deleteGrade(g);
                        }
                        return null;
                    });

                    Optional<String> result = editGradeDialog.showAndWait();
                    result.ifPresent(str -> editGrade(str, g));
                }
            });
            this.grades.add(gradeButton);
            this.gradesBox.getChildren().add(gradeButton);

        }
    }

    private boolean isInvalid(String gradeText){
        for(String s: possibleGrades){
            if(gradeText.equals(s)) return false;
        }
        return true;
    }

    private void addGrade(String gradeStr) {
        Grade gradeToInsert = new Grade(Integer.parseInt(gradeStr),viewSwitcher.getCurrentSubjectContext(),
                student, viewSwitcher.getCurrentTeacherContext() );

        viewSwitcher.getCommunicator().insertGradeToDataBase(gradeToInsert);
        viewSwitcher.setCurrentView(ViewTypes.TEACHER_VIEW);
    }

    private void editGrade(String gradeStr, Grade grade){

        viewSwitcher.getCommunicator().editGrade(grade, Integer.parseInt(gradeStr));
        viewSwitcher.setCurrentView(ViewTypes.TEACHER_VIEW);
    }

    private void deleteGrade(Grade grade){
        viewSwitcher.getCommunicator().deleteGrade(grade);
        viewSwitcher.setCurrentView(ViewTypes.TEACHER_VIEW);
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

    public ArrayList<Button> getGrades() {
        return grades;
    }

    public Button getButton() {
        return button;
    }

    public HBox getGradesBox(){ return  gradesBox; }
}