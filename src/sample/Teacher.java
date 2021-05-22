package sample;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends Person {

    private String title;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Subject> subjects = new HashSet<>();

    @OneToOne
    private SchoolClass myClass;

    public Teacher() {
    }

    public Teacher(String firstname, String lastname, String pesel, Date birthdate, String title) {
        super(firstname, lastname, pesel, birthdate);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
