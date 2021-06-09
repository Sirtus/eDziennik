package sample.gui.views;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import sample.databaseCommunication.DatabaseCommunicator;

public abstract class View {
    protected ViewSwitcher viewSwitcher;
    protected DatabaseCommunicator communicator;

    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    public void setCommunicator(DatabaseCommunicator communicator) {
        this.communicator = communicator;
    }

    public abstract Node getRoot();

    public abstract void refresh();

    public abstract void popContext();

    protected ColumnConstraints getColumnConstraintForSize(double size) {
        ColumnConstraints column = new ColumnConstraints();

        column.setMaxWidth(size);
        column.setMinWidth(size);
        column.setPrefWidth(size);
        column.setHgrow(Priority.ALWAYS);

        column.setHalignment(HPos.CENTER);
        return column;
    }

    protected RowConstraints getGradeRowConstraintForSize(double size) {
        RowConstraints row = new RowConstraints();

        row.setMaxHeight(size);
        row.setMinHeight(size);
        row.setPrefHeight(size);
        row.setVgrow(Priority.ALWAYS);

        row.setValignment(VPos.CENTER);
        return row;
    }
}
