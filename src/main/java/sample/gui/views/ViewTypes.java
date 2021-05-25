package sample.gui.views;

public enum ViewTypes {
    STUDENT_GRADES, CLASS_TUTOR;
    @Override
    public String toString() {
        return switch(this) {
            case STUDENT_GRADES -> "/studentGrades.fxml";
            case CLASS_TUTOR -> "/classTutor.fxml";
        };
    }
}
