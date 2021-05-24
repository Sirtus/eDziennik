package sample.databaseCommunication;


import javafx.util.Pair;
import sample.database.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
        return userId;
    }

    public Login getUserType(){
        return login;
    }

    //UWAGA
    //2 ostatnie funkcje jeszcze nie przetestowane

    public List<Student> getStudentsListBySchoolClass(int schoolClassID){
        CriteriaBuilder cb = this.session.getCriteriaBuilder();
        CriteriaQuery<Student> q = cb.createQuery(Student.class);
        Root<Student> c = q.from(Student.class);
        q.select(c).where(cb.equal(c.get("myClass"), schoolClassID));

        TypedQuery<Student> query = session.createQuery(q);
        return query.getResultList();
    }

    public Set<Subject> getTeacherSubjectsList(int teacherID){
        Teacher teacher = session.find(Teacher.class, teacherID);
        if(teacher != null){
            return teacher.getSubjects();

        }
        return null;
    }

    public Student getStudentByID(int id) {
        return session.find(Student.class, id);
    }

    public Teacher getTeacherByID(int id) {
        return session.find(Teacher.class, id);
    }

    public List<Pair<Subject, ArrayList<Grade>>> getStudentGrades(int studentID){
        Student student = session.find(Student.class, studentID);

        if(student == null){
            return null;
        }

        List<Pair<Subject, ArrayList<Grade>>> result = new ArrayList<>();
        Set<Grade> studentGrades = student.getGrades();
        boolean subjectOnList;
        for(Grade grade: studentGrades){
            subjectOnList = false;
            for(Pair<Subject, ArrayList<Grade>> res: result){
                if(res.getKey().equals(grade.getSubject())){
                    res.getValue().add(grade);
                    subjectOnList = true;
                    break;
                }
            }
            if(subjectOnList != true){
                ArrayList<Grade> grades = new ArrayList<>();
                grades.add(grade);
                result.add(new Pair<>(grade.getSubject(), grades));
            }
        }

        return result;
    }
    public List<Pair<Student, List<Pair<Subject, ArrayList<Grade>>>>> getStudentsGradesBySchoolClass(int schoolClassID){
        SchoolClass schoolClass = session.find(SchoolClass.class, schoolClassID);
        if(schoolClass == null){
            return null;
        }
        List<Pair<Student, List<Pair<Subject, ArrayList<Grade>>>>> result = new ArrayList<>();
        Set<Student> students = schoolClass.getStudents();
        for(Student student: students){
            result.add(new Pair<>(student, getStudentGrades(student.getID())));
        }
        return result;

    }


}
