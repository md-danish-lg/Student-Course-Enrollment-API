package com.danish.course_enrollment.course;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(Long id){
        super("Course with ID: " + id + " not found");
    }

}
