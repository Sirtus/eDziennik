package sample.database;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "seq", initialValue = 1001, allocationSize = 100)
public class Grade {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "seq")
    private int ID;
    private int mark;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    public Grade() {
    }

    public Grade(int mark) {
        this.mark = mark;
    }

    public Grade(int mark, Subject subject, Student student, Teacher teacher) {
        this.mark = mark;
        this.updateStudent(student);
        this.updateSubject(subject);
        this.updateTeacher(teacher);
    }

    //two-sided relations functions
    //use instead set functions
    public void updateStudent(Student student){
        this.setStudent(student);
        student.setGrade(this);
    }
    public void updateTeacher(Teacher teacher){
        this.setTeacher(teacher);
    }
    public void updateSubject(Subject subject){
        this.setSubject(subject);
    }

    //getters and setters
    public int getID() {
        return ID;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}

