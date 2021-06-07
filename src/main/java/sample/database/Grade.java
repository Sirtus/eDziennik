package sample.database;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "seq", initialValue = 1001, allocationSize = 100)
public class Grade {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "seq")
    private int ID;

    @Column(columnDefinition = "integer NOT NULL CHECK (mark BETWEEN 1 AND 6)")
    private int mark;

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne(optional = false)
    private Student student;

    public Grade() {
    }

    public Grade(int mark) {
        this.mark = mark;
    }

    public Grade(int mark, Subject subject, Student student, Teacher teacher) {
        this.mark = mark;
        this.updateStudent(student);
        this.updateSubject(subject);

    }

    //two-sided relations functions
    //use instead set functions
    public void updateStudent(Student student){
        this.setStudent(student);
        student.setGrade(this);
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

}

