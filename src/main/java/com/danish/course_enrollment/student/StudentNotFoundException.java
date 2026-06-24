package com.danish.course_enrollment.student;


public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(Long id){
        super("Student with ID: " + id + " not found!");
    }
}
