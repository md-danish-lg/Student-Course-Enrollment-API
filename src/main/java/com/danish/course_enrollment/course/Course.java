package com.danish.course_enrollment.course;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    private String instructor;

    private String maxCapacity;

    public Course() {
    }

    public Course(Long id, String title, String instructor, String maxCapacity) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.maxCapacity = maxCapacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(title, course.title) && Objects.equals(instructor, course.instructor) && Objects.equals(maxCapacity, course.maxCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, instructor, maxCapacity);
    }
}
