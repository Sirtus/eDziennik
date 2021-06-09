package sample.database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private int ID;
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Teacher teacher;

    @ManyToMany
    private Set<SchoolClass> classes = new HashSet<>();

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, Set<Teacher> teachers, Set<SchoolClass> classes) {
        this.name = name;
        for (Teacher value : teachers) {
            this.addTeacher(value);
        }

        for (SchoolClass aClass : classes) {
            this.addClass(aClass);
        }
    }

    //two-sided relations functions
    //use instead set functions
    public void addClass(SchoolClass schoolClass){
        this.setClass(schoolClass);
        schoolClass.setSubject(this);
    }

    public void addTeacher(Teacher teacher){
        this.setTeacher(teacher);
        teacher.setSubject(this);
    }

    //getters and setters
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<SchoolClass> getClasses() {
        return classes;
    }

    public void setClass(SchoolClass schoolClass) {
        this.classes.add(schoolClass);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return ID == subject.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
