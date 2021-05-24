package sample.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sample.databaseCommunication.DatabaseCommunicator;
import sample.databaseCommunication.Login;
import sample.gui.views.View;
import sample.gui.views.ViewSwitcher;
import sample.gui.views.ViewTypes;

public class LoginController {
    @FXML
    private GridPane root;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private RadioButton studentRadio;
    @FXML
    private RadioButton teacherRadio;
    @FXML
    private Label errorText;

    private DatabaseCommunicator communicator;

    private GUIInitializer initializer;

    public void initialize() {

    }

    public void setCommunicator(DatabaseCommunicator communicator) {
        this.communicator = communicator;
    }

    public void setInitializer(GUIInitializer initializer) {
        this.initializer = initializer;
    }

    @FXML
    private void signIn(ActionEvent event){
        System.out.println(login.getText());
        System.out.println(password.getText());
        if(studentRadio.isSelected()) System.out.println("uczen");
        else System.out.println("nauczyciel");

        Login type = (teacherRadio.isSelected()) ? Login.TEACHER : Login.STUDENT;
        int userID = communicator.signIn(login.getText(), password.getText(), type);

        if (userID < 0) {
            errorText.setText("Błond");
        }
        else {
            initializer.startMainLayout(userID);
        }
    }

}
