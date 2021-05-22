package sample;

import javax.persistence.*;

@Entity
public class Grade {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO)
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

    public Grade(int mark, Subject subject, Student student, Teacher teacher) {
        this.mark = mark;
        this.subject = subject;
        this.student = student;
        this.teacher = teacher;
    }

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

