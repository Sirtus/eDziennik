package sample.gui.views;

import javafx.scene.Node;

public abstract class View {
    protected ViewSwitcher viewSwitcher;

    public void setViewSwitcher(ViewSwitcher viewSwitcher) {
        this.viewSwitcher = viewSwitcher;
    }

    public abstract Node getRoot();

    public abstract void refresh();

    public abstract void popContext();

}
