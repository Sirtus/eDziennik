package sample.databaseCommunication;

import org.mindrot.jbcrypt.BCrypt;
import sample.database.Person;
import sample.database.Student;
import sample.database.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Iterator;
import java.util.List;

public enum Login {
    TEACHER, STUDENT;

    public static Person findByLogin(EntityManager session, String login){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> q = cb.createQuery(Person.class);
        Root<Person> c = q.from(Person.class);
        q.select(c).where(cb.equal(c.get("login"), login));

        TypedQuery<Person> query = session.createQuery(q);
        List<Person> peopleWithLogin = query.getResultList();

        Iterator<Person> iter = peopleWithLogin.listIterator();
        while(iter.hasNext()){
            return (Person)iter.next();
        }
        return null;
    }

    public static int login(EntityManager session, String login, String password, Login type){
        Person personWithLogin = findByLogin(session, login);
        System.out.println(login +" "+ password);
        if(personWithLogin != null){
            Teacher teacher = session.find(Teacher.class, personWithLogin.getID());
            Student student = session.find(Student.class, personWithLogin.getID());

            if(teacher != null && type == TEACHER && TEACHER.checkPassword(password, teacher.getPassword())){
                return teacher.getID();
            }

            if(student != null && type == STUDENT && STUDENT.checkPassword(password, student.getPassword())){
                return student.getID();
            }

        }

        return -1;
    }

    public static String hashPassword(String password){
        System.out.println(BCrypt.hashpw(password, BCrypt.gensalt(12)));
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    private boolean checkPassword(String password, String hashed){
        System.out.println(password);
        System.out.println(hashed);
        return BCrypt.checkpw(password, hashed);
    }

}
