package sample.gui.views;

public enum ViewTypes {
    STUDENT_GRADES, CLASS_TUTOR, TEACHER_SWITCH, TEACHER_VIEW;
    @Override
    public String toString() {
        return switch(this) {
            case STUDENT_GRADES -> "/studentGrades.fxml";
            case CLASS_TUTOR -> "/classTutor.fxml";
            case TEACHER_SWITCH -> "/teacherViewSwitcher.fxml";
            case TEACHER_VIEW -> "/teacherView.fxml";
        };
    }
}
