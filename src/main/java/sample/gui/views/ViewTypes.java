package sample.gui.views;

public enum ViewTypes {
    LOGIN, STUDENT_GRADES;
    @Override
    public String toString() {
        return switch(this) {
            case STUDENT_GRADES -> "/studentGrades.fxml";
            case LOGIN -> "/login.fxml";
        };
    }
}
