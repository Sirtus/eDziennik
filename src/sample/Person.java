package sample;

import javax.persistence.*;
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
    private Date birthdate;

    public Person() {
    }

    public Person(String firstname, String lastname, String pesel, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pesel = pesel;
        this.birthdate = birthdate;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return firstname;
    }

    public void setName(String firstname) {
        this.firstname = firstname;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
