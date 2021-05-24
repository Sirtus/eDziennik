package sample.database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Subject {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private int ID;
    private String name;

    @ManyToMany(mappedBy = "subjects")
    private Set<Teacher> teachers = new HashSet<>();

    @ManyToMany
    private Set<SchoolClass> classes = new HashSet<>();

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(String name, Set<Teacher> teachers, Set<SchoolClass> classes) {
        this.name = name;
        Iterator<Teacher> teacher = teachers.iterator();
        while(teacher.hasNext()){
            this.addTeacher(teacher.next());
        }

        Iterator<SchoolClass> schoolClass = classes.iterator();
        while(schoolClass.hasNext()){
            this.addClass(schoolClass.next());
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

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }

    public Set<SchoolClass> getClasses() {
        return classes;
    }

    public void setClass(SchoolClass schoolClass) {
        this.classes.add(schoolClass);
    }
}
