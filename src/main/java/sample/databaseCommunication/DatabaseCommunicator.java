package sample.databaseCommunication;


import javafx.util.Pair;
import sample.database.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


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

    /**public List<Student> getStudentsListBySchoolClass(int schoolClassID){
        CriteriaBuilder cb = this.session.getCriteriaBuilder();
        CriteriaQuery<Student> q = cb.createQuery(Student.class);
        Root<Student> c = q.from(Student.class);
        q.select(c).where(cb.equal(c.get("myClass"), schoolClassID));

        TypedQuery<Student> query = session.createQuery(q);
        List<Student> result = query.getResultList();
        result.sort(Comparator.comparing(Person::getFullName));
        return result;
    }**/

    public Set<Subject> getTeacherSubjectsList(Teacher teacher){
        if(teacher != null) {
            return teacher.getSubjects();
        }
        return null;
    }

    public List<Pair<Subject, ArrayList<Grade>>> getStudentGrades(Student student){

        if(student == null){
            return null;
        }

        List<Pair<Subject, ArrayList<Grade>>> result = new ArrayList<>();
        Set<Subject> studentSubjects = student.getMyClass().getSubjects();
        Set<Grade> studentGrades = student.getGrades();
        for(Subject subject: studentSubjects){
            ArrayList<Grade> subjectGrades = new ArrayList<>();

            for(Grade grade: studentGrades){
                if(grade.getSubject().equals(subject)){
                    subjectGrades.add(grade);
                }
            }
            // oldest grades first
            subjectGrades.sort(Comparator.comparingInt((Grade::getID)));

            result.add(new Pair<>(subject, subjectGrades));
        }
        return result;
    }

    public List<Pair<Student, List<Pair<Subject, ArrayList<Grade>>>>> getStudentsGradesBySchoolClass( SchoolClass schoolClass) {
        if(schoolClass == null){
            return null;
        }
        List<Pair<Student, List<Pair<Subject, ArrayList<Grade>>>>> result = new ArrayList<>();
        Set<Student> students = schoolClass.getStudents();

        for(Student student: students){
            result.add(new Pair<>(student, getStudentGrades(student)));
        }

        result.sort(Comparator.comparing(pair -> pair.getKey().getFullName()));
        return result;
    }

    public Set<SchoolClass> getClassesListEnrolledForSubject(Subject subject) {
        if(subject == null) {
            return null;
        }
        return subject.getClasses();
    }

    public void insertGradeToDatabase(Grade grade){
        System.out.println(grade.getID());
        session.getTransaction().begin();
        session.persist(grade);
        session.getTransaction().commit();
    }

    public void editGrade(Grade grade, int newGrade){
        session.getTransaction().begin();
        grade.setMark(newGrade);
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
