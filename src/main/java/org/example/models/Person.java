package org.example.models;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.data.annotation.CreatedDate;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email",nullable = false,unique = true)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "date_of_bith")
    private LocalDate dob;


    @Column(name = "createAt",nullable = false, updatable = false)
    private LocalDateTime createAt=LocalDateTime.now().withNano(0);
    @Transient
    private int age;

    public Person(){}

    public Person(String name, String email, String password, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public int getAge() {

        LocalDate dateNow=LocalDate.now().minusYears(dob.getYear());
        return dateNow.getYear();
    }

}
