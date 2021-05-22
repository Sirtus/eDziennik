package sample;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends Person {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private int ID;

    @ManyToOne
    private SchoolClass myClass;

    @OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST)
    private Set<Grade> grades = new HashSet<>();

    public Student() {
    }

    public Student(String firstname, String lastname, String pesel, Date birthdate, SchoolClass myClass) {
        super(firstname, lastname, pesel, birthdate);
        this.myClass = myClass;
    }

    public int getID() {
        return ID;
    }

    public SchoolClass getMyClass() {
        return myClass;
    }

    public void setMyClass(SchoolClass myClass) {
        this.myClass = myClass;
    }

    public Set<Grade> getGrades() {
        return grades;
    }

    public void setGrades(Set<Grade> grades) {
        this.grades = grades;
    }
}
