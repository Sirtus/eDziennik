package sample.gui.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import sample.databaseCommunication.Login;

public class LoginController extends View {
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

    public void initialize() {

    }

    @Override
    public Node getRoot() {
        return root;
    }

    @Override
    public void refresh() {
        errorText.setText(viewSwitcher.getLoginError());
    }

    @Override
    public void popContext() {

    }

    @FXML
    private void signIn(ActionEvent event){
        System.out.println(login.getText());
        System.out.println(password.getText());
        if(studentRadio.isSelected()) System.out.println("uczen");
        else System.out.println("nauczyciel");

        Login type = (teacherRadio.isSelected()) ? Login.TEACHER : Login.STUDENT;
        viewSwitcher.signIn(login.getText(), password.getText(), type);

    }

}
