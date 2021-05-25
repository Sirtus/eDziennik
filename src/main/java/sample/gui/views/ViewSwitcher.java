package sample.gui.views;

import javafx.fxml.FXMLLoader;
import sample.database.*;
import sample.databaseCommunication.DatabaseCommunicator;
import sample.databaseCommunication.Login;
import sample.gui.MainLayoutController;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.EnumMap;
import java.util.LinkedList;

public class ViewSwitcher {
    private final MainLayoutController mainLayoutController;
    private final EnumMap<ViewTypes, View> possibleViewsMap;
    private View currentView;
    private int userId;
    private final DatabaseCommunicator communicator;

    private final LinkedList<Student> studentContext;
    private final LinkedList<SchoolClass> classContext;
    private final LinkedList<Teacher> teacherContext;
    private final LinkedList<Subject> subjectContext;


    public ViewSwitcher(MainLayoutController mainLayoutController, DatabaseCommunicator communicator) {
        this.communicator = communicator;
        this.mainLayoutController = mainLayoutController;
        this.possibleViewsMap = new EnumMap<>(ViewTypes.class);
        this.studentContext = new LinkedList<>();
        this.classContext = new LinkedList<>();
        this.teacherContext = new LinkedList<>();
        this.subjectContext = new LinkedList<>();

        for (ViewTypes type : ViewTypes.values()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(type.toString()));
                loader.load();
                View view = loader.getController();
                view.setViewSwitcher(this);
                possibleViewsMap.put(type, view);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrentView(ViewTypes type) {
        View newView = possibleViewsMap.get(type);
        mainLayoutController.setCurrentView(newView);
        this.currentView = newView;
        newView.refresh();
    }

    public Student getCurrentStudentContext() {
        return studentContext.getLast();
    }

    public SchoolClass getCurrentSchoolClassContext() {
        return classContext.getLast();
    }

    public Teacher getCurrentTeacherContext() {
        return teacherContext.getLast();
    }

    public Subject getCurrentSubjectContext() {
        return subjectContext.getLast();
    }

    public void addToContext(Student student) {
        this.studentContext.add(student);
    }

    public void addToContext(Teacher teacher) {
        this.teacherContext.add(teacher);
    }

    public void addToContext(SchoolClass schoolClass) {
        this.classContext.add(schoolClass);
    }

    public void addToContext(Subject subject) {
        this.subjectContext.add(subject);
    }

    public void popStudent() {
        studentContext.removeLast();
    }

    public void popSubject() {
        subjectContext.removeLast();
    }

    public void popSchoolClass() {
        classContext.removeLast();
    }

    public void popTeacher() {
        teacherContext.removeLast();
    }

    public DatabaseCommunicator getCommunicator() {
        return communicator;
    }

}