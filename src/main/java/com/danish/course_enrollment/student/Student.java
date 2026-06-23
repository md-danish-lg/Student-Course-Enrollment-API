package com.danish.course_enrollment.student;


import com.danish.course_enrollment.enrollment.Enrollment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private LocalDate addedDate;

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments;



    @PrePersist
    private void onCreate(){
        this.addedDate = LocalDate.now();
    }

    public Student() {
    }

    public Student(Long id, String name, String email, LocalDate addedDate, List<Enrollment> enrollments) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.addedDate = addedDate;
        this.enrollments = enrollments;
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

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(email, student.email) && Objects.equals(addedDate, student.addedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, addedDate);
    }
}
