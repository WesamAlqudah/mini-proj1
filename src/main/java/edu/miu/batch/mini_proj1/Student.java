package edu.miu.batch.mini_proj1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;
    private String lastname;
    private double gpa;
    private LocalDate dob;

    @Transient
    private int age;

    public Student(String firstname, String lastname, double gpa, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gpa = gpa;
        this.age = age;
    }

    public Student(String firstname, String lastname, double gpa, LocalDate dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gpa = gpa;
        this.dob = dob;
    }

    public Student(String firstname, String lastname, double gpa, LocalDate dob, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gpa = gpa;
        this.dob = dob;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gpa=" + gpa +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}
