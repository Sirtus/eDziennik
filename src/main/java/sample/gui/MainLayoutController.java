package sample.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import sample.databaseCommunication.DatabaseCommunicator;
import sample.gui.views.View;

import java.util.LinkedList;

public class MainLayoutController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button backButton;

    @FXML
    private StackPane viewContainer;

    @FXML
    private Label welcomeLabel;

    private LinkedList<View> previousViews;
    private View currentView;
    private DatabaseCommunicator communicator;
    private GUIInitializer initializer; // ale to jest syf

    public void initialize() {
        previousViews = new LinkedList<>();
        backButton.setDisable(true);
    }

    @FXML
    private void backButtonAction() {
        currentView.popContext();
        previousViews.removeLast();
        View nextView = previousViews.getLast();
        viewContainer.getChildren().setAll(nextView.getRoot());
        if (previousViews.size() <= 1) backButton.setDisable(true);
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) {
        initializer.startLogin();
    }

    public void setCurrentView(View view) {
        if (currentView == null) {
            welcomeLabel.setText("Witaj, " + communicator.getUser().getFullName() + "!");
        }
        else backButton.setDisable(false);
        currentView = view;
        previousViews.add(currentView);
        viewContainer.getChildren().setAll(view.getRoot());
    }

    public void setCommunicator(DatabaseCommunicator communicator) {
        this.communicator = communicator;
    }

    public void setInitializer(GUIInitializer initializer) {
        this.initializer = initializer;
    }


}
