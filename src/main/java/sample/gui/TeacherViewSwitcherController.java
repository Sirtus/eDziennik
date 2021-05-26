package sample.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import sample.database.Subject;
import sample.databaseCommunication.DatabaseCommunicator;
import sample.gui.views.View;
import sample.gui.views.ViewTypes;

import java.util.Set;

public class TeacherViewSwitcherController extends View {

    @FXML
    private GridPane root;

    @FXML
    private MenuButton subjectsButton;

    private Set<Subject> subjects;

    public void initialize() {

    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        DatabaseCommunicator communicator = viewSwitcher.getCommunicator();
        subjects = communicator.getTeacherSubjectsList(communicator.getUser().getID());
        for(Subject s: subjects){
            MenuItem item = new MenuItem(s.getName());
            subjectsButton.getItems().add(item);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(item.getText());
                    subjectsButton.setText(item.getText());
                }
            });
        }
    }

    @Override
    public void popContext() {
        viewSwitcher.popTeacher();
    }

    @FXML
    void switchViewToClassTutorView(ActionEvent event){
        viewSwitcher.setCurrentView(ViewTypes.CLASS_TUTOR);
    }


    @FXML
    void switchViewToSubjectClassView(){
        for(Subject s: subjects){
            if(s.getName() == subjectsButton.getText()){
                viewSwitcher.addToContext(s);
                System.out.println(s.getName());
                break;
            }
        }
        viewSwitcher.setCurrentView(ViewTypes.TEACHER_VIEW);
    }
}
