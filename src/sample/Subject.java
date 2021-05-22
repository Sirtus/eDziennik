package sample;

import javax.persistence.*;
import java.util.HashSet;
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

    public Subject(String name, Set<Teacher> teachers, Set<SchoolClass> classes) {
        this.name = name;
        this.teachers = teachers;
        this.classes = classes;
    }

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

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Set<SchoolClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<SchoolClass> classes) {
        this.classes = classes;
    }
}
