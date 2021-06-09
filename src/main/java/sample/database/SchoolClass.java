package sample.database;

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
    @Column(nullable = false)
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
        this.updateClassTeacher(teacher);
    }

    //two-sided relations functions
    //use instead set functions
    public void addStudent(Student student){
        this.setStudent(student);
        student.setMyClass(this);
    }
    public void addSubject(Subject subject){
        this.setSubject(subject);
        subject.setClass(this);
    }
    public void updateClassTeacher(Teacher classTeacher){
        this.setClassTeacher(classTeacher);
        classTeacher.setMyClass(this);
    }

    //getters and setters
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

    public void setStudent(Student student) {
        this.students.add(student);
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

    public void setSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public String getName() {
        return classYear + department;
    }
}
