package sample.database;

import sample.databaseCommunication.Login;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private int ID;
    private String firstname;
    private String lastname;
    private String pesel;
    private LocalDate birthdate;
    @Column(unique = true)
    private String login;
    private String password;

    public Person() {
    }

    public Person(String firstname, String lastname, String pesel, LocalDate birthdate, String login, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pesel = pesel;
        this.birthdate = birthdate;
        this.login = login;
        this.password = Login.hashPassword(password);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Login.hashPassword(password);
    }

    public int getID() {
        return ID;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
