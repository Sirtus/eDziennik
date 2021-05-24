package sample.gui.views;

public enum ViewTypes {
    STUDENT_GRADES;
    @Override
    public String toString() {
        return switch(this) {
            case STUDENT_GRADES -> "/studentGrades.fxml";
        };
    }
}
