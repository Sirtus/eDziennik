package sample.databaseCommunication;


import javafx.util.Pair;
import sample.database.*;

import javax.persistence.EntityManager;

import java.util.*;
import java.util.stream.Collectors;


public class DatabaseCommunicator {
    private int userId;
    private Person user;
    private final EntityManager session;
    private Login login;

    public DatabaseCommunicator(EntityManager session){
        this.session = session;
    }

    public int signIn(String login, String password, Login type){
        userId = Login.login(session, login, password, type);
        if(userId != -1) this.login = type;
        System.out.println(userId);
        user = session.find(Person.class, userId);
        return userId;
    }

    public Login getUserType(){
        return login;
    }

    public Person getUser() {
        return user;
    }

    public List<Student> getStudentListBySchoolClass(SchoolClass schoolClass) {
        Set<Student> temp = schoolClass.getStudents();
        return temp.stream().sorted(Comparator.comparing(Person::getFullName)).collect(Collectors.toList());
    }

    public List<Subject> getTeacherSubjectsList(Teacher teacher) {
        if (teacher == null) return null;
        return teacher.getSubjects().stream()
                .sorted(Comparator.comparing(Subject::getName)).collect(Collectors.toList());
    }

    public List<Grade> getStudentGradesForSubject(Student student, Subject subject) {
        ArrayList<Grade> result = new ArrayList<>();

        for(Grade grade: student.getGrades()){
            if(grade.getSubject().equals(subject)){
                result.add(grade);
            }
        }
        // oldest grades first
        result.sort(Comparator.comparingInt((Grade::getID)));
        return result;
    }

    public List<Pair<Subject, List<Grade>>> getStudentGrades(Student student) {
        if (student == null) return null;

        List<Pair<Subject, List<Grade>>> result = new ArrayList<>();
        Set<Subject> studentSubjects = student.getMyClass().getSubjects();
        for(Subject subject: studentSubjects){
            result.add(new Pair<>(subject, getStudentGradesForSubject(student, subject)));
        }
        return result;
    }

    public List<Pair<Student, List<Pair<Subject, List<Grade>>>>> getStudentGradesBySchoolClass(SchoolClass schoolClass) {
        if(schoolClass == null) return null;

        List<Pair<Student, List<Pair<Subject, List<Grade>>>>> result = new ArrayList<>();
        Set<Student> students = schoolClass.getStudents();

        for(Student student: students){
            result.add(new Pair<>(student, getStudentGrades(student)));
        }

        result.sort(Comparator.comparing(pair -> pair.getKey().getFullName()));
        return result;
    }

    public Set<SchoolClass> getClassesListEnrolledForSubject(Subject subject) {
        if(subject == null) return null;
        return subject.getClasses();
    }

    public List<Pair<Student, List<Grade>>> getStudentGradesByClassForSubject(SchoolClass schoolClass, Subject subject) {
        List<Pair<Student, List<Grade>>> result = new LinkedList<>();
        for (Student student : schoolClass.getStudents()) {
            result.add(new Pair<>(student, getStudentGradesForSubject(student, subject)));
        }
        return result;
    }

    public void insertGradeToDatabase(Grade grade){
        System.out.println(grade.getID());
        session.getTransaction().begin();
        session.persist(grade);
        session.getTransaction().commit();
    }

    public void editGrade(Grade grade, int newMark){
        session.getTransaction().begin();
        grade.setMark(newMark);
        session.getTransaction().commit();
    }

    public void deleteGrade(Grade grade){
        Student s = grade.getStudent();
        session.getTransaction().begin();
        s.getGrades().remove(grade);
        session.remove(grade);
        session.getTransaction().commit();
    }
}
