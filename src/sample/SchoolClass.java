package sample;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SchoolClass {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private int ID;
    private int numberOfStudents;
    private int classYear;
    private String department;

    @OneToMany(mappedBy = "myClass")
    private Set<Student> students = new HashSet<>();

    @OneToOne(mappedBy = "myClass")
    private Teacher classTeacher;

    @ManyToMany(mappedBy = "classes")
    private Set<Subject> subjects = new HashSet<>();

    public SchoolClass() {
    }

    public SchoolClass(int numberOfStudents, int year, String department, Teacher teacher) {
        this.numberOfStudents = numberOfStudents;
        this.classYear = year;
        this.department = department;
        this.students = students;
        this.classTeacher = teacher;
        this.subjects = subjects;
    }

    public int getID() {
        return ID;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public int getYear() {
        return classYear;
    }

    public void setYear(int year) {
        this.classYear = year;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Teacher getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Teacher classTeacher) {
        this.classTeacher = classTeacher;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
