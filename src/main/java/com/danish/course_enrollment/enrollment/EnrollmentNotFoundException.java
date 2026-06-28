package com.danish.course_enrollment.enrollment;


public class EnrollmentNotFoundException extends RuntimeException{
    public EnrollmentNotFoundException(Long id){
        super("Enrollment with id: " + id +" Not Found");
    }
}
