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
        private ArrayList<Button> grades;
        private HBox gradesBox;
        private Student student;
        private Button button;
        private final String[] possibleGrades = {"1", "2", "3", "4", "5", "6"};

        public TableStudent(Student student,  ArrayList<Grade> grades){
            this.student = student;
            this.gradesBox = new HBox();
            this.firstname = student.getFirstname();
            this.lastname = student.getLastname();
            this.button = new Button();
            this.button.setText("Dodaj");
            this.button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(firstname + " " + lastname);
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
                System.out.println(firstname + " " + g.getMark() + " " + g.getID());
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

}

