package sample.database;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends Person {

    @Column(nullable = false)
    private String title;


    @OneToMany(mappedBy = "teacher", cascade = CascadeType.PERSIST)
    private Set<Subject> subjects = new HashSet<>();

    @OneToOne(optional = true)
    private SchoolClass myClass;

    public Teacher() {
    }

    public Teacher(String firstname, String lastname, String pesel, LocalDate birthdate, String title, String login, String password) {
        super(firstname, lastname, pesel, birthdate, login, password);
        this.title = title;
    }
    //two-sided relations functions
    //use instead set functions
    public void addSubject(Subject subject){
        this.setSubject(subject);
        subject.setTeacher(this);
    }

    public void updateMyClass(SchoolClass myClass){
        this.setMyClass(myClass);
        myClass.setClassTeacher(this);
    }

    //getters and setters
    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public SchoolClass getMyClass() {
        return myClass;
    }

    public void setMyClass(SchoolClass myClass) {
        this.myClass = myClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
