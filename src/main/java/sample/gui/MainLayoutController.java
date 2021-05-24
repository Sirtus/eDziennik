package sample.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import sample.gui.views.View;

import java.util.LinkedList;

public class MainLayoutController {

    @FXML
    private Button backButton;

    @FXML
    private StackPane viewContainer;

    private LinkedList<View> previousViews;

    public void initialize() {
        previousViews = new LinkedList<>();
        backButton.setDisable(true);
    }

    @FXML
    private void backButtonAction() {
        viewContainer.getChildren().setAll(previousViews.pop().getRoot());
        if (previousViews.size() == 0) backButton.setDisable(true);
    }

    public void setCurrentView(View view) {
        viewContainer.getChildren().setAll(view.getRoot());
    }
}
